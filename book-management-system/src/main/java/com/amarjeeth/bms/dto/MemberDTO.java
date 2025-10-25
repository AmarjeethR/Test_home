package com.amarjeeth.bms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class MemberDTO {

	// Request DTO for creating/updating members
	public static class MemberRequest {
		@NotBlank(message = "Email is required")
		@Email(message = "Email should be valid")
		private String email;

		@NotBlank(message = "Name is required")
		private String name;

		@Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
		private String phoneNumber;

		// Getters and Setters
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
	}

	// Response DTO for member data
	public static class MemberResponse {
		private Long id;
		private String email;
		private String name;
		private String phoneNumber;
		private LocalDate membershipDate;

		// Constructors
		public MemberResponse() {
		}

		public MemberResponse(Long id, String email, String name, String phoneNumber, LocalDate membershipDate) {
			this.id = id;
			this.email = email;
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.membershipDate = membershipDate;
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
	}
}