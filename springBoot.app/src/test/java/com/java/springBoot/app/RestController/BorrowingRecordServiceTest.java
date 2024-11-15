package com.java.springBoot.app.Service;

import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Book;
import com.java.springBoot.app.Model.BorrowingRecord;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Repository.BookRepository;
import com.java.springBoot.app.Repository.BorrowingRecordRepository;
import com.java.springBoot.app.Repository.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingRecordServiceTest {

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBorrowingRecords() {
        BorrowingRecord record1 = new BorrowingRecord();
        BorrowingRecord record2 = new BorrowingRecord();

        when(borrowingRecordRepository.findAll()).thenReturn(List.of(record1, record2));

        List<BorrowingRecord> records = borrowingRecordService.getAllBorrowingRecords();

        assertEquals(2, records.size());
        verify(borrowingRecordRepository, times(1)).findAll();
    }

    @Test
    void testBorrowBook_Success() {
        Book book = new Book();
        book.setId(1L);
        Patron patron = new Patron();
        patron.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenAnswer(i -> i.getArgument(0));

        BorrowingRecord record = borrowingRecordService.borrowBook(1L, 1L);

        assertNotNull(record);
        assertEquals(book, record.getBook());
        assertEquals(patron, record.getPatron());
        assertEquals(LocalDate.now(), record.getBorrowDate());

        verify(bookRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findById(1L);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void testBorrowBook_BookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoDataFoundException.class, () -> borrowingRecordService.borrowBook(1L, 1L));

        assertEquals("Book not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
        verifyNoInteractions(patronRepository);
    }

    @Test
    void testGetBorrowBook_Success() {
        BorrowingRecord record = new BorrowingRecord();
        record.setReturnDate(null);

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L))
                .thenReturn(Optional.of(record));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenAnswer(i -> i.getArgument(0));

        BorrowingRecord updatedRecord = borrowingRecordService.getBorrowBook(1L, 1L);

        assertNotNull(updatedRecord);
        assertEquals(LocalDate.now(), updatedRecord.getReturnDate());
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L);
        verify(borrowingRecordRepository, times(1)).save(record);
    }

    @Test
    void testGetBorrowBook_RecordNotFound() {
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoDataFoundException.class, () -> borrowingRecordService.getBorrowBook(1L, 1L));

        assertEquals("Borrowing record not found", exception.getMessage());
        verify(borrowingRecordRepository, times(1)).findByBookIdAndPatronIdAndReturnDateIsNull(1L, 1L);
    }



}

