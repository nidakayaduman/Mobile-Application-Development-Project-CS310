package com.myproject.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SearchView searchView = findViewById(R.id.searchView);
        Button addEventButton = findViewById(R.id.addEventButton);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        loadEvents();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchEvents(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    loadEvents();
                } else {
                    searchEvents(newText);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadEvents();
                return false;
            }
        });
    }

    private void loadEvents() {
        EventApiService apiService = ApiUtils.getEventApiService();
        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                eventList = response.body();
                eventAdapter = new EventAdapter(eventList);
                recyclerView.setAdapter(eventAdapter);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("MainActivity", "Error loading events", t);
            }
        });
    }

    private void searchEvents(String query) {
        EventApiService apiService = ApiUtils.getEventApiService();

        // Search by name
        Call<List<Event>> callByName = apiService.getEventsByName(query);
        callByName.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    eventList = response.body();
                    eventAdapter = new EventAdapter(eventList);
                    recyclerView.setAdapter(eventAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("MainActivity", "Error searching events by name", t);
            }
        });

        // Search by location
        Call<List<Event>> callByLocation = apiService.getEventsByLocation(query);
        callByLocation.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    eventList.addAll(response.body());
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e("MainActivity", "Error searching events by location", t);
            }
        });
    }

    public void addEvent(Event newEvent) {
        EventApiService apiService = ApiUtils.getEventApiService();
        Call<Event> call = apiService.addEvent(newEvent);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    // Yeni etkinlik başarıyla eklendiğinde yapılacak işlemler
                    loadEvents(); // Tüm etkinlikleri yeniden yükle
                } else {
                    Log.e("MainActivity", "Error adding event: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.e("MainActivity", "Error adding event", t);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Event newEvent = (Event) data.getSerializableExtra("newEvent");
            addEvent(newEvent);
        }
    }
}
