package br.com.lucascode.coding_tdd.service;

import br.com.lucascode.coding_tdd.domain.Book;
import br.com.lucascode.coding_tdd.exception.BookNotFoundException;
import br.com.lucascode.coding_tdd.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> listAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) throws BookNotFoundException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new BookNotFoundException(id);
        }
        return bookRepository.findById(id);
    }

    public ResponseEntity<Book> updateBookById(Long id, Book book) throws BookNotFoundException {
        return bookRepository.findById(id).map(
                bookToUpdate -> {
                    bookToUpdate.setName(book.getName());
                    bookToUpdate.setStatus(book.getStatus());
                    bookToUpdate.setCategory(book.getCategory());
                    Book updated = bookRepository.save(bookToUpdate);
                    return ResponseEntity.ok().body(updated);

                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteBookById(Long id) {
        return bookRepository.findById(id).map(bookToDelete -> {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    public List<Book> listBooksThatStartsWith(String partialName) {
        return bookRepository.findByNameStartingWith(partialName);
    }
}
