package com.java.springBoot.app.Service;

import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book getBook(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return book;
    }

    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NoDataFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }


    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }


}
