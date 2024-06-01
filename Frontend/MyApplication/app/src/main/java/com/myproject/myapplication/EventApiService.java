package com.myproject.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventApiService {
    @GET("/events")
    Call<List<Event>> getAllEvents();

    @POST("/events")
    Call<Event> addEvent(@Body Event newEvent);

    @GET("/events/search/name/{name}")
    Call<List<Event>> getEventsByName(@Path("name") String name);

    @GET("/events/search/location/{location}")
    Call<List<Event>> getEventsByLocation(@Path("location") String location);
}
