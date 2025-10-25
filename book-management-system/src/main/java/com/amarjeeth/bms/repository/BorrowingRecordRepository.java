package com.amarjeeth.bms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amarjeeth.bms.entity.BorrowingRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

	// Find borrowing record by book and member with BORROWED status
	Optional<BorrowingRecord> findByBookIdAndMemberIdAndReturnDateIsNull(Long bookId, Long memberId);

	// Find all borrowing records for a member
	List<BorrowingRecord> findByMemberId(Long memberId);

	// Find active borrowing records for a member (not returned)
	List<BorrowingRecord> findByMemberIdAndReturnDateIsNull(Long memberId);

	// Find overdue records
	@Query("SELECT br FROM BorrowingRecord br WHERE br.dueDate < :currentDate AND br.returnDate IS NULL")
	List<BorrowingRecord> findOverdueRecords(@Param("currentDate") LocalDate currentDate);

	// Check if member has overdue books
	@Query("SELECT COUNT(br) > 0 FROM BorrowingRecord br WHERE br.member.id = :memberId AND br.dueDate < :currentDate AND br.returnDate IS NULL")
	boolean hasOverdueBooks(@Param("memberId") Long memberId, @Param("currentDate") LocalDate currentDate);

	// Check if book is currently borrowed by any member
	boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}