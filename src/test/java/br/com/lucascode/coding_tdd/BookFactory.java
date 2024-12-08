package br.com.lucascode.coding_tdd;

import br.com.lucascode.coding_tdd.domain.Book;
import br.com.lucascode.coding_tdd.domain.Category;
import br.com.lucascode.coding_tdd.domain.Status;

public class BookFactory {
    public static Book createBook() {
        Book book = new Book();
        book.setId(1L);
        book.setName("cracking the code interview");
        book.setCategory(Category.PROGRAMMING);
        book.setStatus(Status.NOT_STARTED);
        return book;
    }
}
