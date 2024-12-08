package br.com.lucascode.coding_tdd;

import br.com.lucascode.coding_tdd.domain.Book;
import br.com.lucascode.coding_tdd.repository.BookRepository;
import br.com.lucascode.coding_tdd.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import static br.com.lucascode.coding_tdd.BookFactory.createBook;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    @DisplayName("Success - Should save book with success")
    void shouldSaveBookWithSuccess() {
        when(bookRepository.save(ArgumentMatchers.any(Book.class)))
                .thenReturn(createBook());
        Book created = bookService.createBook(createBook());
        assertThat(created.getName()).isSameAs(createBook().getName());
        assertNotNull(created.getId());
        assertEquals(created.getId(),1);
    }
}

