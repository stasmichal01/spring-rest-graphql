package com.example.restandgraphql.book;

import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Author newAuthor(String firstName, String lastName) {
        return authorRepository.save(new Author(firstName, lastName));
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        var pages = pageCount == null || pageCount < 0 ? 0 : pageCount;
        return bookRepository.save(new Book(title, isbn, pages, new Author(authorId)));
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        return bookRepository.findById(id).map(book -> {
            book.setPageCount(pageCount);
            bookRepository.save(book);
            return book;
        }).orElseThrow(() -> new BookNotFoundException("The book to be updated was found", id));
    }
}
