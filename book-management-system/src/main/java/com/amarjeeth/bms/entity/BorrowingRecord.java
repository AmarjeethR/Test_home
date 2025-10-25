package com.amarjeeth.bms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "borrow_date", nullable = false)
	private LocalDate borrowDate;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BorrowStatus status;

	// Enum for borrowing status
	public enum BorrowStatus {
		BORROWED, RETURNED, OVERDUE
	}

	// Constructors
	public BorrowingRecord() {
		this.borrowDate = LocalDate.now();
		this.dueDate = this.borrowDate.plusDays(14);
		this.status = BorrowStatus.BORROWED;
	}

	public BorrowingRecord(Book book, Member member) {
		this();
		this.book = book;
		this.member = member;
		updateStatus();
	}

	// Business logic methods
	public void returnBook() {
		this.returnDate = LocalDate.now();
		updateStatus();
	}

	public void updateStatus() {
		if (this.returnDate != null) {
			this.status = BorrowStatus.RETURNED;
		} else if (LocalDate.now().isAfter(this.dueDate)) {
			this.status = BorrowStatus.OVERDUE;
		} else {
			this.status = BorrowStatus.BORROWED;
		}
	}

	public boolean isOverdue() {
		return this.returnDate == null && LocalDate.now().isAfter(this.dueDate);
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
		this.dueDate = borrowDate.plusDays(14);
		updateStatus();
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
		updateStatus();
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
		updateStatus();
	}

	public BorrowStatus getStatus() {
		updateStatus(); // Always return current status
		return status;
	}

	public void setStatus(BorrowStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BorrowingRecord{" + "id=" + id + ", book=" + (book != null ? book.getTitle() : "null") + ", member="
				+ (member != null ? member.getName() : "null") + ", borrowDate=" + borrowDate + ", dueDate=" + dueDate
				+ ", returnDate=" + returnDate + ", status=" + status + '}';
	}
}