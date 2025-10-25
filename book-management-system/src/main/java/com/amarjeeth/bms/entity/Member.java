package com.amarjeeth.bms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "members", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@Column(nullable = false)
	@NotBlank(message = "Name is required")
	private String name;

	@Column(name = "phone_number")
	@Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
	private String phoneNumber;

	@Column(name = "membership_date", nullable = false)
	private LocalDate membershipDate;

	// Constructors
	public Member() {
		this.membershipDate = LocalDate.now();
	}

	public Member(String email, String name, String phoneNumber) {
		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.membershipDate = LocalDate.now();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getMembershipDate() {
		return membershipDate;
	}

	public void setMembershipDate(LocalDate membershipDate) {
		this.membershipDate = membershipDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member) o;
		return Objects.equals(id, member.id) && Objects.equals(email, member.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email);
	}

	@Override
	public String toString() {
		return "Member{" + "id=" + id + ", email='" + email + '\'' + ", name='" + name + '\'' + ", phoneNumber='"
				+ phoneNumber + '\'' + ", membershipDate=" + membershipDate + '}';
	}
}