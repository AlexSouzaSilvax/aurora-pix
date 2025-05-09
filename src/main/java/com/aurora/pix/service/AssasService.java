package com.aurora.pix.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aurora.pix.dto.AssasHttpResponse;
import com.aurora.pix.dto.CreatePixQrCodeStaticPayload;
import com.aurora.pix.exceptions.ApiErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@RequiredArgsConstructor
public class AssasService {

    private final ObjectMapper objectMapper;

    private final String ASSAS_BASE_URL_API = System.getenv("ASSAS_BASE_URL_API");
    private final String ASSAS_ACCESS_TOKEN = System.getenv("ASSAS_ACCESS_TOKEN");

    public AssasHttpResponse createPixQrCodeStatic(CreatePixQrCodeStaticPayload payload) throws ApiErrorException {
        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(36, TimeUnit.SECONDS)
                    .readTimeout(36, TimeUnit.SECONDS)
                    .writeTimeout(36, TimeUnit.SECONDS)
                    .build();

            String requestBodyJson = objectMapper.writeValueAsString(payload);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(requestBodyJson, mediaType);

            Request request = new Request.Builder()
                    .url(ASSAS_BASE_URL_API + "/pix/qrCodes/static")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", ASSAS_ACCESS_TOKEN)
                    .build();

            Response httpResponse = client.newCall(request).execute();

            return new AssasHttpResponse(httpResponse);

        } catch (IOException error) {
            throw new ApiErrorException(error.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public AssasHttpResponse getKeyPixCodeAssas() {
        try {

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(36, TimeUnit.SECONDS)
                    .readTimeout(36, TimeUnit.SECONDS)
                    .writeTimeout(36, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(ASSAS_BASE_URL_API + "/pix/addressKeys")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("access_token", ASSAS_ACCESS_TOKEN)
                    .build();

            Response httpResponse = client.newCall(request).execute();

            return new AssasHttpResponse(httpResponse);

        } catch (IOException error) {
            throw new ApiErrorException(error.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }
}
