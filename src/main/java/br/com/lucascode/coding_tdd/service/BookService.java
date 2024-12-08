package br.com.lucascode.coding_tdd.service;

import br.com.lucascode.coding_tdd.domain.Book;
import br.com.lucascode.coding_tdd.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
}
