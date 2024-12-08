package br.com.lucascode.coding_tdd;

import br.com.lucascode.coding_tdd.domain.Book;
import br.com.lucascode.coding_tdd.domain.Category;
import br.com.lucascode.coding_tdd.domain.Status;
import br.com.lucascode.coding_tdd.exception.BookNotFoundException;
import br.com.lucascode.coding_tdd.repository.BookRepository;
import br.com.lucascode.coding_tdd.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static br.com.lucascode.coding_tdd.BookFactory.createBook;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Success - Should Save Book With Success")
    void shouldSaveBookWithSuccess() {

        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(createBook());
        Book created = bookService.createBook(createBook());
        assertThat(created.getName()).isSameAs(createBook().getName());
        assertNotNull(created.getId());
        assertEquals(created.getId(), 1);
    }

    @Test
    @DisplayName("Success - should return the list of books with success")
    void shouldReturnTheListOfBooksWithSuccess() {
        when(bookRepository.findAll()).thenReturn(List.of(createBook()));
        List<Book> books = this.bookService.listAllBooks();
        assertEquals(1, books.size());
    }


    @Test
    @DisplayName("Success - should find a book by id with success")
    void ShouldFindBookByIdWithSuccess() throws BookNotFoundException {
        Book book = new Book();
        book.setId(1L);
        book.setName("cracking the code interview");
        book.setStatus(Status.NOT_STARTED);
        book.setCategory(Category.PROGRAMMING);


        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(createBook()));
        Optional<Book> expected = bookService.getBookById(book.getId());
        assertThat(expected.get().getId()).isEqualTo(createBook().getId());
        assertThat(expected.get().getName()).isEqualTo(createBook().getName());
        assertThat(expected.get().getStatus()).isEqualTo(createBook().getStatus());
        assertThat(expected.get().getCategory()).isEqualTo(createBook().getCategory());
        assertDoesNotThrow(() -> {
            bookService.getBookById(book.getId());
        });
    }

    @Test
    @DisplayName("Error - should throw exception when try to find a book by id ")
    void ShouldThrowExceptionWhenTryToFindABook() throws BookNotFoundException {
        Book book = new Book();
        book.setId(1L);
        book.setName("cracking the code interview");
        book.setStatus(Status.NOT_STARTED);
        book.setCategory(Category.PROGRAMMING);


        when(bookRepository.findById(200L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById(200L));
        assertEquals("Book with id " + 200L + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Success - should delete book with success ")
    void shouldDeleteBookWithSuccess() {
        when(bookRepository.findById(createBook().getId())).thenReturn(Optional.empty());

        ResponseEntity<Object> expected = bookService.deleteBookById(createBook().getId());
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }
    @Test
    @DisplayName("Success - should find a book by partial name")
    void ShouldFindBookByPartialNameWithSuccess(){
        Book book = new Book();
        book.setId(1L);
        book.setName("essencialismo");
        book.setStatus(Status.IN_PROGRESS);
        book.setCategory(Category.SOFT_SKILLS);


        when(bookRepository.findByNameStartingWith("ess")).thenReturn((List.of(book)));
        List<Book> expected = bookService.listBooksThatStartsWith("ess");
        assertThat(expected.get(0).getId()).isEqualTo(book.getId());
        assertThat(expected.get(0).getName()).isEqualTo(book.getName());
        assertThat(expected.get(0).getCategory()).isEqualTo(book.getCategory());
        assertThat(expected.get(0).getStatus()).isEqualTo(book.getStatus());
        assertThat(expected.get(0).getName()).isNotEqualTo(createBook().getName());



    }
}

