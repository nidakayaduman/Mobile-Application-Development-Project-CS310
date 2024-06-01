package com.myproject.myapplication;

public class ApiUtils {
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    public static EventApiService getEventApiService() {
        return RetrofitClient.getClient(BASE_URL).create(EventApiService.class);
    }
}
