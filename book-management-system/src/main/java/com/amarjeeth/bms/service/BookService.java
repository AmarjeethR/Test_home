package com.amarjeeth.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amarjeeth.bms.dto.BookDTO;
import com.amarjeeth.bms.entity.Book;
import com.amarjeeth.bms.exception.DuplicateResourceException;
import com.amarjeeth.bms.exception.ResourceNotFoundException;
import com.amarjeeth.bms.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * Create a new book
	 */
	public BookDTO.BookResponse createBook(BookDTO.BookRequest bookRequest) {
		// Check if ISBN already exists
		if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
			throw new DuplicateResourceException("Book with ISBN '" + bookRequest.getIsbn() + "' already exists");
		}

		// Create new book entity
		Book book = new Book(bookRequest.getIsbn(), bookRequest.getTitle(), bookRequest.getAuthor(),
				bookRequest.getPublicationYear(), bookRequest.getQuantity());

		// Save book
		Book savedBook = bookRepository.save(book);

		// Convert to response DTO
		return convertToResponseDTO(savedBook);
	}

	/**
	 * Get all books with pagination
	 */
	@Transactional(readOnly = true)
	public Page<BookDTO.BookResponse> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable).map(this::convertToResponseDTO);
	}

	/**
	 * Get book by ID
	 */
	@Transactional(readOnly = true)
	public BookDTO.BookResponse getBookById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		return convertToResponseDTO(book);
	}

	/**
	 * Search books by title
	 */
	@Transactional(readOnly = true)
	public List<BookDTO.BookResponse> searchBooksByTitle(String title) {
		List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
		return books.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
	}

	/**
	 * Search books by title with pagination
	 */
	@Transactional(readOnly = true)
	public Page<BookDTO.BookResponse> searchBooksByTitle(String title, Pageable pageable) {
		return bookRepository.findByTitleContainingIgnoreCase(title, pageable).map(this::convertToResponseDTO);
	}

	/**
	 * Update book
	 */
	public BookDTO.BookResponse updateBook(Long id, BookDTO.BookRequest bookRequest) {
		// Find existing book
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

		// Check if ISBN is being changed and if new ISBN already exists
		if (!existingBook.getIsbn().equals(bookRequest.getIsbn())
				&& bookRepository.existsByIsbn(bookRequest.getIsbn())) {
			throw new DuplicateResourceException("Book with ISBN '" + bookRequest.getIsbn() + "' already exists");
		}

		// Update book details
		existingBook.setIsbn(bookRequest.getIsbn());
		existingBook.setTitle(bookRequest.getTitle());
		existingBook.setAuthor(bookRequest.getAuthor());
		existingBook.setPublicationYear(bookRequest.getPublicationYear());
		existingBook.setQuantity(bookRequest.getQuantity());

		// Save updated book
		Book updatedBook = bookRepository.save(existingBook);

		return convertToResponseDTO(updatedBook);
	}

	/**
	 * Delete book
	 */
	public void deleteBook(Long id) {
		// Check if book exists
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

		// Check if book has active borrow records
		// This check would be implemented in BorrowingRecordRepository
		// For now, we'll assume it's handled by database constraints

		bookRepository.delete(book);
	}

	/**
	 * Convert Book entity to Response DTO
	 */
	private BookDTO.BookResponse convertToResponseDTO(Book book) {
		return new BookDTO.BookResponse(book.getId(), book.getIsbn(), book.getTitle(), book.getAuthor(),
				book.getPublicationYear(), book.getQuantity(), book.getAvailableQuantity());
	}
}