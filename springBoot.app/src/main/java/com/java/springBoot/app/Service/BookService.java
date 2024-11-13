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
        Book book = null;
        book = bookRepository.findById(id).get();
        return book;
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    public Book updateBook(Book book) {
        return bookRepository.save(book);  // حفظ الكتاب المعدل في قاعدة البيانات
    }


}
