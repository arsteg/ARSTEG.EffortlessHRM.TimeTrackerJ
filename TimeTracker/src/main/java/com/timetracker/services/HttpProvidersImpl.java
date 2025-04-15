package com.timetracker.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetracker.services.HttpProviders;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpProvidersImpl implements HttpProviders {
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public HttpProvidersImpl() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T, U> CompletableFuture<T> postAsync(String uri, U body, Class<T> responseType) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), responseType);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public <T, U> CompletableFuture<T> postWithTokenAsync(String uri, U body, Class<T> responseType, String token) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), responseType);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public <T> CompletableFuture<T> getWithTokenAsync(String uri, Class<T> responseType, String token) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), responseType);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public <T, U> CompletableFuture<T> putWithTokenAsync(String uri, U body, Class<T> responseType, String token) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), responseType);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public <T> CompletableFuture<T> deleteWithTokenAsync(String uri, Class<T> responseType, String token) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Authorization", "Bearer " + token)
                    .DELETE()
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        try {
                            return objectMapper.readValue(response.body(), responseType);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse response", e);
                        }
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public <U> CompletableFuture<Void> postAsyncWithVoid(String uri, U body, String token) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {})
                    .exceptionally(throwable -> {
                        throw new RuntimeException("HTTP request failed", throwable);
                    });
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}