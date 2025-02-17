package com.programacion.distribuida.repo;

import com.programacion.distribuida.db.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class AuthorRepository implements PanacheRepositoryBase<Author, Integer> {

    public Optional<Author> update(Integer id, Author author) {
        var oldAuthor = this.findByIdOptional(id);

        if (oldAuthor.isEmpty()) {
            return Optional.empty();
        }

        var newAuthor = oldAuthor.get();
        newAuthor.setFirstName(author.getFirstName());
        newAuthor.setLastName(author.getLastName());

        return Optional.of(newAuthor);
    }
}
