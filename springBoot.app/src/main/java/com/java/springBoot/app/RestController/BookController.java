package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Repository.BookRepository;
import com.java.springBoot.app.Service.BookService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
            return Response.error(404, "book not found");
        }
        else {
            Response<Book> response = new Response<Book>();
            response.setResultCode(200);
            response.setResultDescription("Success");
            response.setMessage("User details retrieved successfully");
            response.setData(book);
            return response;
        }
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);  // استدعاء الخدمة لإضافة الكتاب
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
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);  // استدعاء الخدمة لحذف الكتاب
    }
}
