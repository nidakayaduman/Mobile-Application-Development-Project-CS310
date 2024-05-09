package com.example.demo;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
	
	 List<Event> findByNameContainingIgnoreCase(String name);
	 List<Event> findByLocationContainingIgnoreCase(String location);

}
