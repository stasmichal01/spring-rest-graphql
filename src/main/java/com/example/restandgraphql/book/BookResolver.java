package com.example.restandgraphql.book;

import graphql.kickstart.tools.GraphQLResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public Author getAuthor(Book book) {
        var id = book.getAuthor().getId();
        return authorRepository.findById(id).orElseThrow(() -> new BookNotFoundException("The book to be updated was found", id));
    }
}
