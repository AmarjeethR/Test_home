package com.amarjeeth.bms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amarjeeth.bms.entity.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	// Find book by ISBN
	Optional<Book> findByIsbn(String isbn);

	// Check if ISBN exists
	boolean existsByIsbn(String isbn);

	// Search books by title (case-insensitive)
	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
	List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

	// Search books by title with pagination
	@Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
	Page<Book> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

	// Find available books
	List<Book> findByAvailableQuantityGreaterThan(Integer availableQuantity);
}