package com.pupptmstr.jbtest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

public class Matcher {
    public final int DEFAULT_WAIT_TIME = 15000;

    //java вариант - поставить таймер и спустя время насильно гасить выполнение и возвращать false\выкидывать ttl
    public boolean matches(String text, String regex) {
        return matches(text, regex, DEFAULT_WAIT_TIME);
    }

    public boolean matches(String text, String regex, int maxMillisToWork) {
        try {
            CompletableFuture<Boolean> matcherTask =
                    CompletableFuture.supplyAsync(() -> Pattern.compile(regex).matcher(text).matches());
            return matcherTask.get(maxMillisToWork, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*В теории в котлине можно было бы сделать это бэкграунд функцией (на корутинах),
    которая бы выполнялась до конца, но не блокировала бы остальные действия*/

}
