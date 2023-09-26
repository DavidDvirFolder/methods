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
import static junit.framework.TestCase.assertEquals;

public class RefactoredBackendTest {
        private static final String BASE_URL = "https://api.github.com/";
        static HttpClient httpClient = HttpClient.newBuilder().build();
        static HttpResponse<Void> response;
        private static final String USER_AGENT = "Java 11 Http bot";

        @BeforeAll
        static void sendGetToBaseEndpoint() throws IOException, InterruptedException {
            HttpRequest get = HttpRequest.newBuilder(URI.create(BASE_URL))
                    .setHeader("user-Agent", USER_AGENT)
                    .build();
            //send the request
            response = httpClient.send(get, HttpResponse.BodyHandlers.discarding());
            int actualCode = response.statusCode();
            assertEquals(200, actualCode);
        }

        // Response headers
        @ParameterizedTest
        @CsvSource({
                "X-Ratelimit-Limit, 60",
                "content-type, application/json; charset=utf-8",
                "Server, GitHub.com",
                "X-Frame-Options,deny"
        })
        void contentTypeIsJson(String header, String expectedValue) {
            String headerValue = response.headers().firstValue(header).get();
            assertEquals(expectedValue, headerValue);
        }
    }
