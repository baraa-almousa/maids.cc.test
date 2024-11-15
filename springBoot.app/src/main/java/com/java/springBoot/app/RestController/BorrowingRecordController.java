package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response;
import com.java.springBoot.app.Model.BorrowingRecord;
import com.java.springBoot.app.Service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @GetMapping("/borrowingRecords")
    public ResponseEntity<Response<List<BorrowingRecord>>> getAllBorrowingRecords() {
        List<BorrowingRecord> records = borrowingRecordService.getAllBorrowingRecords();
        return ResponseEntity.ok(Response.success(records));
    }

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Response<BorrowingRecord>> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            BorrowingRecord record = borrowingRecordService.borrowBook(bookId, patronId);

            Response<BorrowingRecord> response = Response.success(record);
            response.setResultCode(201);
            response.setMessage("Book borrowing created successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(404, e.getMessage()));
        }
    }


    @GetMapping("/return/book/{bookId}/patron/{patronId}")
    public ResponseEntity<Response<BorrowingRecord>> getBorrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        try {
            BorrowingRecord record = borrowingRecordService.getBorrowBook(bookId, patronId);
            return ResponseEntity.ok(Response.success(record));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(404, e.getMessage()));
        }
    }
}
