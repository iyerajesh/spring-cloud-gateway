package me.aboullaite.bookstore.service;

import me.aboullaite.bookstore.model.Author;
import me.aboullaite.bookstore.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookService {

    private List<Book> books;

    public BookService() {

        this.books = new ArrayList<>();

        books.add(Book.builder().
                title("Becoming").
                isbn("9781524763138").
                authors(Arrays.asList(Author.builder().
                        name("Michelle Obama").
                        build()))
                .build());

        books.add(Book.builder().
                title("Liar Liar").
                isbn("9780316418249").
                authors(Arrays.asList(Author.builder().name("James Paterson").build()))
                .build());

        books.add(Book.builder().
                title("The Chef").
                isbn("9780316453301").
                authors(Arrays.asList(Author.builder().name("James Dalio").build(),
                        Author.builder().name("James Paterson").build()))
                .build());

        books.add(Book.builder().
                title("Stranger Things - Suspicious Minds").
                isbn("9781984800886").
                authors(Arrays.asList(Author.builder().name("Gwenda Bond").build()))
                .build());
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return books.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> longExecutionMethod() {
        try {
            // Simulate random poor performing method!
            if (ThreadLocalRandom.current().nextInt() > 0.5)
                Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.books;

    }

}
