package com.amarjeeth.bms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "books", uniqueConstraints = { @UniqueConstraint(columnNames = "isbn") })
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	@NotBlank(message = "ISBN is required")
	private String isbn;

	@Column(nullable = false)
	@NotBlank(message = "Title is required")
	private String title;

	@Column(nullable = false)
	@NotBlank(message = "Author is required")
	private String author;

	@Column(name = "publication_year")
	private Integer publicationYear;

	@Column(nullable = false)
	@NotNull(message = "Quantity is required")
	@Min(value = 0, message = "Quantity must be non-negative")
	private Integer quantity;

	@Column(name = "available_quantity", nullable = false)
	@NotNull(message = "Available quantity is required")
	@Min(value = 0, message = "Available quantity must be non-negative")
	private Integer availableQuantity;

	// Constructors
	public Book() {
		// Default constructor for JPA
	}

	public Book(String isbn, String title, String author, Integer publicationYear, Integer quantity) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.quantity = quantity;
		this.availableQuantity = quantity;
	}

	// Business logic methods
	public void decrementAvailableQuantity() {
		if (this.availableQuantity > 0) {
			this.availableQuantity--;
		} else {
			throw new IllegalStateException("No available copies to decrement");
		}
	}

	public void incrementAvailableQuantity() {
		if (this.availableQuantity < this.quantity) {
			this.availableQuantity++;
		} else {
			throw new IllegalStateException("Available quantity cannot exceed total quantity");
		}
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
		// Ensure available quantity doesn't exceed new total quantity
		if (this.availableQuantity > quantity) {
			this.availableQuantity = quantity;
		}
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		if (availableQuantity <= this.quantity) {
			this.availableQuantity = availableQuantity;
		} else {
			throw new IllegalArgumentException("Available quantity cannot exceed total quantity");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Book book = (Book) o;
		return Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isbn);
	}

	@Override
	public String toString() {
		return "Book{" + "id=" + id + ", isbn='" + isbn + '\'' + ", title='" + title + '\'' + ", author='" + author
				+ '\'' + ", publicationYear=" + publicationYear + ", quantity=" + quantity + ", availableQuantity="
				+ availableQuantity + '}';
	}
}