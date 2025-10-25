package com.amarjeeth.bms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookDTO {

	// Request DTO for creating/updating books
	public static class BookRequest {
		@NotBlank(message = "ISBN is required")
		private String isbn;

		@NotBlank(message = "Title is required")
		private String title;

		@NotBlank(message = "Author is required")
		private String author;

		private Integer publicationYear;

		@NotNull(message = "Quantity is required")
		@Min(value = 1, message = "Quantity must be at least 1")
		private Integer quantity;

		// Getters and Setters
		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Integer getPublicationYear() {
			return publicationYear;
		}

		public void setPublicationYear(Integer publicationYear) {
			this.publicationYear = publicationYear;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
	}

	// Response DTO for book data
	public static class BookResponse {
		private Long id;
		private String isbn;
		private String title;
		private String author;
		private Integer publicationYear;
		private Integer quantity;
		private Integer availableQuantity;

		// Constructors
		public BookResponse() {
		}

		public BookResponse(Long id, String isbn, String title, String author, Integer publicationYear,
				Integer quantity, Integer availableQuantity) {
			this.id = id;
			this.isbn = isbn;
			this.title = title;
			this.author = author;
			this.publicationYear = publicationYear;
			this.quantity = quantity;
			this.availableQuantity = availableQuantity;
		}

		// Getters and Setters
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getIsbn() {
			return isbn;
		}

		public void setIsbn(String isbn) {
			this.isbn = isbn;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public Integer getPublicationYear() {
			return publicationYear;
		}

		public void setPublicationYear(Integer publicationYear) {
			this.publicationYear = publicationYear;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Integer getAvailableQuantity() {
			return availableQuantity;
		}

		public void setAvailableQuantity(Integer availableQuantity) {
			this.availableQuantity = availableQuantity;
		}
	}
}