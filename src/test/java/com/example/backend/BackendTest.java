package com.example.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Testing Frameworks & Tools: Refactor by JunitJupiterParams

public class BackendTest {
    private static final String BASE_URL = "https://api.github.com/";
    static HttpClient httpClient = HttpClient.newBuilder().build();

    @BeforeAll
    static void sendGetToBaseEndpoint() throws IOException, InterruptedException {
        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL))
                .setHeader("user-Agent", "Java 11 Http bot")
                .build();
        //send request
        HttpResponse<Void> response = httpClient.send(get, HttpResponse.BodyHandlers.discarding());
        int actualCode = response.statusCode();
        Assertions.assertEquals(200, actualCode);
    }

    // Response headers
    @ParameterizedTest
    @CsvSource({
            "X-Ratelimit-Limit, 60",
            "content-type, application/json; charset=utf-8",
            "Server, GitHub.com",
            "X-Frame-Options,deny"
    })
    void contentTypeIsJson(String header, String expectedValue) throws IOException, InterruptedException {
        //Arrange
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL))
                .setHeader("user-Agent", "Java 11 Http bot")
                .build();
        //Act
        HttpResponse<Void> response = httpClient.send(get, HttpResponse.BodyHandlers.discarding());
        String contentType = response.headers().firstValue(header).get();
        Assertions.assertEquals(expectedValue, contentType);
    }
}
