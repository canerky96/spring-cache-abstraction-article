package com.kaya.springcachedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(BookController.ENDPOINT)
@Slf4j
public class BookController {

  public static final String ENDPOINT = "book";

  public Map<String, Book> books = new HashMap<>();

  @PostConstruct
  public void setBooks() {
    books.put("1", Book.builder().id(1L).name("Altıncı Koğuş").writer("Anton Çehov").build());
    books.put("2", Book.builder().id(2L).name("Devlet Ana").writer("Kemal Tahir").build());
  }

  @Cacheable(cacheNames = BookController.ENDPOINT, key = "#root.methodName")
  @GetMapping
  public List<Book> getBooks() {
    log.info("Method invoked for getBooks()");
    return new ArrayList<>(books.values());
  }

  @Cacheable(cacheNames = BookController.ENDPOINT, key = "#root.methodName + #id ")
  @GetMapping("{id}")
  public Book getBook(@PathVariable("id") String id) {
    log.info("Method invoked for getBook({})", id);
    return books.getOrDefault(id, new Book());
  }
}
