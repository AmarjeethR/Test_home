package com.amarjeeth.bms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amarjeeth.bms.dto.BorrowingDTO;
import com.amarjeeth.bms.service.BorrowingService;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "Borrowing Management", description = "APIs for managing book borrowing and returning")
public class BorrowingController {

	private final BorrowingService borrowingService;

	@Autowired
	public BorrowingController(BorrowingService borrowingService) {
		this.borrowingService = borrowingService;
	}

	@PostMapping("/{bookId}/member/{memberId}")
	@Operation(summary = "Borrow a book")
	public ResponseEntity<BorrowingDTO.BorrowResponse> borrowBook(@PathVariable Long bookId,
			@PathVariable Long memberId) {

		BorrowingDTO.BorrowResponse borrowResponse = borrowingService.borrowBook(bookId, memberId);
		return ResponseEntity.ok(borrowResponse);
	}

	@PutMapping("/{bookId}/member/{memberId}/return")
	@Operation(summary = "Return a borrowed book")
	public ResponseEntity<BorrowingDTO.BorrowResponse> returnBook(@PathVariable Long bookId,
			@PathVariable Long memberId) {

		BorrowingDTO.BorrowResponse borrowResponse = borrowingService.returnBook(bookId, memberId);
		return ResponseEntity.ok(borrowResponse);
	}

	@GetMapping("/member/{memberId}")
	@Operation(summary = "Get borrowing history for a member")
	public ResponseEntity<List<BorrowingDTO.BorrowResponse>> getBorrowingHistory(@PathVariable Long memberId) {
		List<BorrowingDTO.BorrowResponse> history = borrowingService.getBorrowingHistory(memberId);
		return ResponseEntity.ok(history);
	}
}