package com.example.restandgraphql.config;

import com.example.restandgraphql.account.Account;
import com.example.restandgraphql.account.AccountRepository;
import com.example.restandgraphql.book.Author;
import com.example.restandgraphql.book.AuthorRepository;
import com.example.restandgraphql.book.Book;
import com.example.restandgraphql.book.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;

@Configuration
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner demo(AuthorRepository authorRepository,
                                  BookRepository bookRepository,
                                  AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(new Account(null, "test", new BigDecimal(100)));
            var author = new Author("Herbert", "Schildt");
            authorRepository.save(author);
            bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
        };
    }
}
