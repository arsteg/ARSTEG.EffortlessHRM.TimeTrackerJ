package com.timetracker.services;
import java.util.concurrent.CompletableFuture;

public interface HttpProviders  {
    <T, U> CompletableFuture<T> postAsync(String uri, U body, Class<T> responseType);
    <T, U> CompletableFuture<T> postWithTokenAsync(String uri, U body, Class<T> responseType, String token);
    <T> CompletableFuture<T> getWithTokenAsync(String uri, Class<T> responseType, String token);
    <T, U> CompletableFuture<T> putWithTokenAsync(String uri, U body, Class<T> responseType, String token);
    <T> CompletableFuture<T> deleteWithTokenAsync(String uri, Class<T> responseType, String token);
    <U> CompletableFuture<Void> postAsyncWithVoid(String uri, U body, String token);
}
