package com.amarjeeth.bms.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amarjeeth.bms.dto.BorrowingDTO;
import com.amarjeeth.bms.entity.Book;
import com.amarjeeth.bms.entity.BorrowingRecord;
import com.amarjeeth.bms.entity.Member;
import com.amarjeeth.bms.exception.InvalidOperationException;
import com.amarjeeth.bms.exception.ResourceNotFoundException;
import com.amarjeeth.bms.repository.BookRepository;
import com.amarjeeth.bms.repository.BorrowingRecordRepository;
import com.amarjeeth.bms.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowingService {
    
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    
    @Autowired
    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository,
                          BookRepository bookRepository,
                          MemberRepository memberRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }
    
    /**
     * Borrow a book
     */
    public BorrowingDTO.BorrowResponse borrowBook(Long bookId, Long memberId) {
        // Find book
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        
        // Find member
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));
        
        // Check if book is available
        if (book.getAvailableQuantity() <= 0) {
            throw new InvalidOperationException("Book '" + book.getTitle() + "' is not available for borrowing");
        }
        
        // Check if member has overdue books
        if (borrowingRecordRepository.hasOverdueBooks(memberId, LocalDate.now())) {
            throw new InvalidOperationException("Member has overdue books and cannot borrow new books");
        }
        
        // Check if member already has this book borrowed and not returned
        if (borrowingRecordRepository.findByBookIdAndMemberIdAndReturnDateIsNull(bookId, memberId).isPresent()) {
            throw new InvalidOperationException("Member already has this book borrowed");
        }
        
        // Create borrowing record
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, member);
        
        // Decrement available quantity
        book.decrementAvailableQuantity();
        
        // Save entities
        bookRepository.save(book);
        BorrowingRecord savedRecord = borrowingRecordRepository.save(borrowingRecord);
        
        return new BorrowingDTO.BorrowResponse(savedRecord);
    }
    
    /**
     * Return a borrowed book
     */
    public BorrowingDTO.BorrowResponse returnBook(Long bookId, Long memberId) {
        // Find active borrowing record
        BorrowingRecord borrowingRecord = borrowingRecordRepository
                .findByBookIdAndMemberIdAndReturnDateIsNull(bookId, memberId)
                .orElseThrow(() -> new InvalidOperationException(
                    "No active borrowing record found for book id: " + bookId + " and member id: " + memberId));
        
        // Get the book
        Book book = borrowingRecord.getBook();
        
        // Return the book
        borrowingRecord.returnBook();
        
        // Increment available quantity
        book.incrementAvailableQuantity();
        
        // Save entities
        bookRepository.save(book);
        BorrowingRecord updatedRecord = borrowingRecordRepository.save(borrowingRecord);
        
        return new BorrowingDTO.BorrowResponse(updatedRecord);
    }
    
    /**
     * Get borrowing history for a member
     */
    @Transactional(readOnly = true)
    public List<BorrowingDTO.BorrowResponse> getBorrowingHistory(Long memberId) {
        // Check if member exists
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        }
        
        List<BorrowingRecord> records = borrowingRecordRepository.findByMemberId(memberId);
        
        return records.stream()
                .map(BorrowingDTO.BorrowResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Update overdue status for all borrowing records
     */
    public void updateOverdueStatus() {
        List<BorrowingRecord> overdueRecords = borrowingRecordRepository.findOverdueRecords(LocalDate.now());
        
        for (BorrowingRecord record : overdueRecords) {
            record.updateStatus();
            borrowingRecordRepository.save(record);
        }
    }
}