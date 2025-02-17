package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class BookRepository implements PanacheRepositoryBase<Book, Integer> {

    public Optional<Book> update(Integer id, Book book) {
        var oldBook = this.findByIdOptional(id);

        if (oldBook.isEmpty()) {
            return Optional.empty();
        }

        var newBook = oldBook.get();
        newBook.setTitle(book.getTitle());
        newBook.setAuthorId(book.getAuthorId());
        newBook.setIsbn(book.getIsbn());
        newBook.setPrice(book.getPrice());

        return Optional.of(newBook);
    }
}
