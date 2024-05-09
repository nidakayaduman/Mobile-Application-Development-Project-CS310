package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Arrays;



@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    @Autowired
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        initDatabase(); // Uygulama başlatıldığında başlangıç verilerini ekle
    }

    // Tüm event'ları listeleme için bir GET endpoint'i
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Yeni bir event ekleme için bir POST endpoint'i
    @PostMapping
    public Event addEvent(@RequestBody Event newEvent) {
        return eventRepository.save(newEvent);
    }
    
    @GetMapping("/search/name/{name}")
    public List<Event> getEventsByName(@PathVariable String name) {
        return eventRepository.findByNameContainingIgnoreCase(name);
    }


    @GetMapping("/search/location/{location}")
    public List<Event> getEventsByLocation(@PathVariable String location) {
        return eventRepository.findByLocationContainingIgnoreCase(location);
    }
   
    public void initDatabase() {
        if (eventRepository.count() == 0) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Event[] eventsArray = objectMapper.readValue(new File("events.json"), Event[].class);
                List<Event> events = Arrays.asList(eventsArray);
                eventRepository.saveAll(events);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
