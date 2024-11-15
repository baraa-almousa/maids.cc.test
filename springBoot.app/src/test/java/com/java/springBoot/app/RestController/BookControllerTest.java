package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Service.BookService;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    void setUp() {
        // Setting up a test book instance
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2024);
        book.setIsbn("123-456-789");
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        // Act
        List<Book> result = bookController.getAllBooks();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }

    @Test
    void testGetBookById_BookFound() {
        // Arrange
        when(bookService.getBook(1L)).thenReturn(book);

        // Act
        Response response = bookController.getBookById(1L);

        // Assert
        assertEquals(200, response.getResultCode());
        assertEquals("Success", response.getResultDescription());
        assertEquals("Book details retrieved successfully", response.getMessage());
        assertNotNull(response.getData());
        assertEquals("Test Book", ((Book) response.getData()).getTitle());
    }

    @Test
    void testGetBookById_BookNotFound() {
        // Arrange
        when(bookService.getBook(1L)).thenReturn(null);

        // Act
        Response response = bookController.getBookById(1L);

        // Assert
        assertEquals(404, response.getResultCode());
        assertEquals("Book not found", response.getMessage());
    }

    @Test
    void testAddBook() {
        // Arrange
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        // Act
        ResponseEntity<Response<Book>> response = bookController.addBook(book);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Book created successfully", response.getBody().getMessage());
        assertEquals(201, response.getBody().getResultCode());
    }

    @Test
    void testUpdateBook_BookFound() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPublicationYear(2025);
        updatedBook.setIsbn("987-654-321");

        when(bookService.getBook(1L)).thenReturn(book);
        when(bookService.updateBook(any(Book.class))).thenReturn(updatedBook);

        // Act
        Response response = bookController.updateBook(1L, updatedBook);

        // Assert
        assertEquals(200, response.getResultCode());
        assertEquals("Book updated successfully", response.getMessage());
        assertEquals("Updated Title", ((Book) response.getData()).getTitle());
    }

    @Test
    void testUpdateBook_BookNotFound() {
        // Arrange
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");

        when(bookService.getBook(1L)).thenReturn(null);

        // Act
        Response response = bookController.updateBook(1L, updatedBook);

        // Assert
        assertEquals(404, response.getResultCode());
        assertEquals("Book not found", response.getMessage());
    }

    @Test
    void testDeleteBook_BookFound() {
        // Arrange
        when(bookService.findById(1L)).thenReturn(book);
        doNothing().when(bookService).deleteBook(1L);

        // Act
        ResponseEntity<Response<Void>> response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getBody().getResultCode());
    }

    @Test
    void testDeleteBook_BookNotFound() {
        // Arrange
        when(bookService.findById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Response<Void>> response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getResultCode());
    }

    @Test
    void testDeleteBook_Exception() {
        // Arrange
        when(bookService.findById(1L)).thenThrow(new RuntimeException("Error"));

        // Act
        ResponseEntity<Response<Void>> response = bookController.deleteBook(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getResultCode());
    }
}
