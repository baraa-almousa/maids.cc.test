package com.java.springBoot.app.Service;

import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();  // استخدام BookRepository للحصول على جميع الكتب
    }
    public Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return book;
    }


    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        // Ensure the book exists before attempting to delete
        if (!bookRepository.existsById(id)) {
            throw new NoDataFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }


    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);  // حفظ الكتاب المعدل في قاعدة البيانات
    }


}
