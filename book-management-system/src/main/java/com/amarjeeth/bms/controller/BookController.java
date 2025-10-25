package com.amarjeeth.bms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.amarjeeth.bms.dto.BookDTO;
import com.amarjeeth.bms.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "APIs for managing books")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	@Operation(summary = "Create a new book")
	public ResponseEntity<BookDTO.BookResponse> createBook(@Valid @RequestBody BookDTO.BookRequest bookRequest) {
		BookDTO.BookResponse bookResponse = bookService.createBook(bookRequest);
		return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all books with pagination")
	public ResponseEntity<Page<BookDTO.BookResponse>> getAllBooks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "title") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<BookDTO.BookResponse> books = bookService.getAllBooks(pageable);
		return ResponseEntity.ok(books);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get book by ID")
	public ResponseEntity<BookDTO.BookResponse> getBookById(@PathVariable Long id) {
		BookDTO.BookResponse bookResponse = bookService.getBookById(id);
		return ResponseEntity.ok(bookResponse);
	}

	@GetMapping("/search")
	@Operation(summary = "Search books by title")
	public ResponseEntity<List<BookDTO.BookResponse>> searchBooksByTitle(@RequestParam String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		if (page >= 0 && size > 0) {
			Pageable pageable = PageRequest.of(page, size);
			Page<BookDTO.BookResponse> books = bookService.searchBooksByTitle(title, pageable);
			return ResponseEntity.ok(books.getContent());
		} else {
			List<BookDTO.BookResponse> books = bookService.searchBooksByTitle(title);
			return ResponseEntity.ok(books);
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update book details")
	public ResponseEntity<BookDTO.BookResponse> updateBook(@PathVariable Long id,
			@Valid @RequestBody BookDTO.BookRequest bookRequest) {

		BookDTO.BookResponse bookResponse = bookService.updateBook(id, bookRequest);
		return ResponseEntity.ok(bookResponse);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a book")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}