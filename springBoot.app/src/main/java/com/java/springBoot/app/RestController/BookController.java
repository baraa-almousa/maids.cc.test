package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response;
import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Repository.BookRepository;
import com.java.springBoot.app.Service.BookService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;  // استدعاء الخدمة بدلاً من repository

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();  // استدعاء الخدمة للحصول على الكتب
    }

    @GetMapping("/{id}")
    public Response getBookById(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        if (book == null) {
            return Response.error(404, "Book not found");
        } else {
            Response<Book> response = new Response<>();
            response.setResultCode(200);
            response.setResultDescription("Success");
            response.setMessage("Book details retrieved successfully");
            response.setData(book);
            return response;
        }
    }

    @PostMapping
    public ResponseEntity<Response<Book>> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);

        Response<Book> response = Response.success(savedBook);
        response.setResultCode(201);
        response.setMessage("Book created successfully");


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PutMapping("/{id}")
    public Response updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        // العثور على الكتاب بناءً على المعرف
        Book existingBook = bookService.getBook(id);

        // إذا لم يتم العثور على الكتاب
        if (existingBook == null) {
            return Response.error(404, "Book not found");
        }

        // تحديث بيانات الكتاب
        existingBook.setTitle(bookDetails.getTitle());
        existingBook.setAuthor(bookDetails.getAuthor());
        existingBook.setPublicationYear(bookDetails.getPublicationYear());
        existingBook.setIsbn(bookDetails.getIsbn());

        // حفظ الكتاب المعدل في قاعدة البيانات
        Book updatedBook = bookService.updateBook(existingBook);

        // إرجاع الاستجابة مع الكتاب المعدل
        Response<Book> response = new Response<>();
        response.setResultCode(200);
        response.setResultDescription("Success");
        response.setMessage("Book updated successfully");
        response.setData(updatedBook);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteBook(@PathVariable Long id) {
        try {
            Book book = bookService.findById(id);

            if (book == null) {
                // Return 404 if the book is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error(404, "Error deleting Book not found"));
            }

            bookService.deleteBook(id);  // Proceed with deletion

            // Return 200 if the deletion was successful with no data
            return ResponseEntity.ok(Response.success());  // Use the method with no data

        } catch (NoDataFoundException e) {
            // In case book was not found, catch the exception and return 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(404, e.getMessage()));
        } catch (Exception e) {
            // Return 500 in case of any other error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(500, "Error deleting book"));
        }
    }


}
