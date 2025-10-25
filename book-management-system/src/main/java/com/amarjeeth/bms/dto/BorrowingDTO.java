package com.amarjeeth.bms.dto;

import java.time.LocalDate;

import com.amarjeeth.bms.entity.BorrowingRecord;

public class BorrowingDTO {

	// Request DTO for borrowing operations
	public static class BorrowRequest {
		private Long bookId;
		private Long memberId;

		// Getters and Setters
		public Long getBookId() {
			return bookId;
		}

		public void setBookId(Long bookId) {
			this.bookId = bookId;
		}

		public Long getMemberId() {
			return memberId;
		}

		public void setMemberId(Long memberId) {
			this.memberId = memberId;
		}
	}

	// Response DTO for borrowing records
	public static class BorrowResponse {
		private Long id;
		private Long bookId;
		private String bookTitle;
		private String bookIsbn;
		private Long memberId;
		private String memberName;
		private LocalDate borrowDate;
		private LocalDate dueDate;
		private LocalDate returnDate;
		private BorrowingRecord.BorrowStatus status;

		// Constructors
		public BorrowResponse() {
		}

		public BorrowResponse(BorrowingRecord record) {
			this.id = record.getId();
			this.bookId = record.getBook().getId();
			this.bookTitle = record.getBook().getTitle();
			this.bookIsbn = record.getBook().getIsbn();
			this.memberId = record.getMember().getId();
			this.memberName = record.getMember().getName();
			this.borrowDate = record.getBorrowDate();
			this.dueDate = record.getDueDate();
			this.returnDate = record.getReturnDate();
			this.status = record.getStatus();
		}

		// Getters and Setters
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getBookId() {
			return bookId;
		}

		public void setBookId(Long bookId) {
			this.bookId = bookId;
		}

		public String getBookTitle() {
			return bookTitle;
		}

		public void setBookTitle(String bookTitle) {
			this.bookTitle = bookTitle;
		}

		public String getBookIsbn() {
			return bookIsbn;
		}

		public void setBookIsbn(String bookIsbn) {
			this.bookIsbn = bookIsbn;
		}

		public Long getMemberId() {
			return memberId;
		}

		public void setMemberId(Long memberId) {
			this.memberId = memberId;
		}

		public String getMemberName() {
			return memberName;
		}

		public void setMemberName(String memberName) {
			this.memberName = memberName;
		}

		public LocalDate getBorrowDate() {
			return borrowDate;
		}

		public void setBorrowDate(LocalDate borrowDate) {
			this.borrowDate = borrowDate;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}

		public LocalDate getReturnDate() {
			return returnDate;
		}

		public void setReturnDate(LocalDate returnDate) {
			this.returnDate = returnDate;
		}

		public BorrowingRecord.BorrowStatus getStatus() {
			return status;
		}

		public void setStatus(BorrowingRecord.BorrowStatus status) {
			this.status = status;
		}
	}
}