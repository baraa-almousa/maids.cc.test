package com.java.springBoot.app.Service;

import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Model.BorrowingRecord;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Repository.BookRepository;
import com.java.springBoot.app.Repository.BorrowingRecordRepository;
import com.java.springBoot.app.Repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingRecordService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;


    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll();
    }
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoDataFoundException("Book not found"));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new NoDataFoundException("Patron not found"));

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowDate(LocalDate.now());

        return borrowingRecordRepository.save(record);
    }

    public BorrowingRecord getBorrowBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository
                .findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new NoDataFoundException("Borrowing record not found"));

        record.setReturnDate(LocalDate.now());
        return borrowingRecordRepository.save(record);
    }
}
