package me.aboullaite.bookstore.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.contrib.spring.web.client.HttpHeadersCarrier;
import io.opentracing.propagation.Format;
import io.opentracing.util.GlobalTracer;
import me.aboullaite.bookstore.model.Book;
import me.aboullaite.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks() {

        Tracer tracer = GlobalTracer.get();
        Span span = tracer.buildSpan("getAllBooks").start();

        HttpHeaders responseHeaders = new HttpHeaders();
        tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new HttpHeadersCarrier(responseHeaders));
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> optBook = bookService.getBookByIsbn(isbn);
        if (optBook.isPresent())
            return ResponseEntity.ok(optBook.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Book.builder().build());
    }

    @GetMapping("/danger")
    public ResponseEntity<List<Book>> unstableBooksEndpoint() {
        return ResponseEntity.ok(bookService.longExecutionMethod());
    }
}
