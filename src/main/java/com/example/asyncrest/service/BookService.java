package com.example.asyncrest.service;

import com.example.asyncrest.model.Book;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookService {
    private final Map<String, Book> store = new ConcurrentHashMap<>();

    public BookService() {
        store.put("1", new Book("1", "Reactive Spring", "Josh Long"));
        store.put("2", new Book("2", "Spring in Action", "Craig Walls"));
        store.put("3", new Book("3", "Effective Java", "Joshua Bloch"));
        store.put("4", new Book("4", "Clean Code", "Robert C. Martin"));
        store.put("5", new Book("5", "Java Concurrency in Practice", "Brian Goetz"));
        store.put("6", new Book("6", "Spring Boot in Practice", "Somnath Musib"));
        store.put("7", new Book("7", "Microservices Patterns", "Chris Richardson"));
        store.put("8", new Book("8", "Head First Design Patterns", "Eric Freeman"));
        store.put("9", new Book("9", "Domain-Driven Design", "Eric Evans"));
        store.put("10", new Book("10", "Clean Architecture", "Robert C. Martin"));
        store.put("11", new Book("11", "Kubernetes in Action", "Marko Lukša"));
        store.put("12", new Book("12", "Cloud Native Java", "Josh Long"));
        store.put("13", new Book("13", "Spring Security in Action", "Laurentiu Spilca"));
        store.put("14", new Book("14", "Building Microservices", "Sam Newman"));
        store.put("15", new Book("15", "Refactoring", "Martin Fowler"));
        store.put("16", new Book("16", "Designing Data-Intensive Applications", "Martin Kleppmann"));
        store.put("17", new Book("17", "The Pragmatic Programmer", "Andrew Hunt"));
        store.put("18", new Book("18", "Pro Spring 6", "Iuliana Cosmina"));
        store.put("19", new Book("19", "Hands-On Microservices with Spring Boot and Spring Cloud", "Magnus Larsson"));
        store.put("20", new Book("20", "System Design Interview", "Alex Xu"));
    }


    public Flux<Book> findAll() {
        // Simulate reactive source
        return Flux.fromIterable(store.values())
                .delayElements(Duration.ofSeconds(1)); // 2 seconds delay per book
    }

    public Mono<Book> findById(String id) {
        return Mono.justOrEmpty(store.get(id));
    }

    public Mono<Book> save(Mono<Book> bookMono) {
        return bookMono.map(book -> {
            store.put(book.getId(), book);
            return book;
        });
    }

    public Mono<Void> deleteById(String id) {
        store.remove(id);
        return Mono.empty();
    }
}
