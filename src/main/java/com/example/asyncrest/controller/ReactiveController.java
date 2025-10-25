package com.example.asyncrest.controller;

import com.example.asyncrest.model.Book;
import com.example.asyncrest.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/books")
public class ReactiveController {

    private final BookService svc;
    public ReactiveController(BookService svc) { this.svc = svc; }



    @GetMapping(path = "/all")
    public Flux<Book> all() {
        return svc.findAll();
    }


    @GetMapping("/consumer")
    public Flux<Book> consumer() {

        System.out.println("Request Thread -"+Thread.currentThread().getName());

        return svc.findAll()
                .doOnNext(book -> System.out.println("Received: " + book + " Thread -"+Thread.currentThread().getName()))
                .doOnError(err -> System.err.println("Error: " + err))
                .doOnComplete(() -> System.out.println("Completed!" + " Thread -"+Thread.currentThread().getName()));
    }

    @GetMapping(path = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> streamAll() {
        return svc.findAll(); // emits many items over time
    }

    @GetMapping(path = "/{id}")
    public Mono<ResponseEntity<Book>> getOne(@PathVariable String id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Book>> create(@RequestBody Mono<Book> bookMono) {
        return svc.save(bookMono)
                .map(saved -> ResponseEntity.status(201).body(saved));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return svc.findById(id)
                .flatMap(b -> svc.deleteById(id).thenReturn(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
