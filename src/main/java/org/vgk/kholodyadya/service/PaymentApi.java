package org.vgk.kholodyadya.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PaymentApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();
    private final ObjectMapper mapper = new ObjectMapper();
    private String sessionId;
    private String refreshToken;

    private final String HOST = "irkkt-mobile.nalog.ru:8888";
    private final String DEVICE_OS = "iOS";
    private final String CLIENT_VERSION = "2.9.0";
    private final String DEVICE_ID = "7C82010F-16CC-446B-8F66-FC4080C66521";
    private final String ACCEPT = "*/*";
    private final String USER_AGENT = "billchecker/2.9.0 (iPhone; iOS 13.6; Scale/2.00)";
    private final String ACCEPT_LANGUAGE = "ru-RU;q=1, en-US;q=0.9";
    private final String CLIENT_SECRET = "IyvrAbKt9h/8p6a7QPh8gpkXYQ4=";
    private final String OS = "Android";

//    public static void main(String[] args) throws IOException, InterruptedException {
//        new PaymentApi();
//    }

    public PaymentApi() throws IOException, InterruptedException {
        getSessionId();
    }

    private void getSessionId() throws IOException, InterruptedException {
        String phone = getPhoneNumber();
        sendPhoneVerificationRequest(phone);

        Scanner input = new Scanner(System.in);
        String code = input.next();

        String url = String.format("https://%s/v2/auth/phone/verify", HOST);

        Map<String, String> requestBodyValues = Map.of(
                "phone", phone,
                "client_secret", CLIENT_SECRET,
                "code", code,
                "os", OS
        );
        String requestBody = mapper.writeValueAsString(requestBodyValues);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", ACCEPT)
                .setHeader("Device-OS", DEVICE_OS)
                .setHeader("Device-Id", DEVICE_ID)
                .setHeader("clientVersion", CLIENT_VERSION)
                .setHeader("Accept-Language", ACCEPT_LANGUAGE)
                .setHeader("User-Agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        sessionId = jsonObject.get("sessionId").getAsString();
        refreshToken = jsonObject.get("refresh_token").getAsString();

    }

    private void sendPhoneVerificationRequest(String phone) throws IOException, InterruptedException {

        String url = String.format("https://%s/v2/auth/phone/request", HOST);

        Map<String, String> requestBodyValues = Map.of(
                "phone", phone,
                "client_secret", CLIENT_SECRET,
                "os", OS
        );
        String requestBody = mapper.writeValueAsString(requestBodyValues);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", ACCEPT)
                .setHeader("Device-OS", DEVICE_OS)
                .setHeader("Device-Id", DEVICE_ID)
                .setHeader("clientVersion", CLIENT_VERSION)
                .setHeader("Accept-Language", ACCEPT_LANGUAGE)
                .setHeader("User-Agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String getReceiptId(String qr) throws IOException, InterruptedException {
        String url = String.format("https://%s/v2/ticket", HOST);
        Map<String, String> requestBodyValues = Map.of(
                "qr", qr
        );
        String requestBody = mapper.writeValueAsString(requestBodyValues);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", ACCEPT)
                .setHeader("Device-OS", DEVICE_OS)
                .setHeader("Device-Id", DEVICE_ID)
                .setHeader("clientVersion", CLIENT_VERSION)
                .setHeader("Accept-Language", ACCEPT_LANGUAGE)
                .setHeader("User-Agent", USER_AGENT)
                .setHeader("sessionId", sessionId)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        return jsonObject.get("id").getAsString();
    }

    private String getReceipt(String qr) throws IOException, InterruptedException {
        String receiptId = getReceiptId(qr);
        String url = String.format("https://%s/v2/tickets/%s", HOST, receiptId);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", ACCEPT)
                .setHeader("Device-OS", DEVICE_OS)
                .setHeader("Device-Id", DEVICE_ID)
                .setHeader("clientVersion", CLIENT_VERSION)
                .setHeader("Accept-Language", ACCEPT_LANGUAGE)
                .setHeader("User-Agent", USER_AGENT)
                .setHeader("sessionId", sessionId)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        System.out.println(response.body());
        return "";
    }

    private String getPhoneNumber() {
        return "";
    }
}