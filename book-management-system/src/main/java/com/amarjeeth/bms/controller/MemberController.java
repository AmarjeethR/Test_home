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

import com.amarjeeth.bms.dto.MemberDTO;
import com.amarjeeth.bms.service.MemberService;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Member Management", description = "APIs for managing library members")
public class MemberController {

	private final MemberService memberService;

	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping
	@Operation(summary = "Register a new member")
	public ResponseEntity<MemberDTO.MemberResponse> registerMember(
			@Valid @RequestBody MemberDTO.MemberRequest memberRequest) {
		MemberDTO.MemberResponse memberResponse = memberService.registerMember(memberRequest);
		return new ResponseEntity<>(memberResponse, HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all members with pagination")
	public ResponseEntity<Page<MemberDTO.MemberResponse>> getAllMembers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<MemberDTO.MemberResponse> members = memberService.getAllMembers(pageable);
		return ResponseEntity.ok(members);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get member by ID")
	public ResponseEntity<MemberDTO.MemberResponse> getMemberById(@PathVariable Long id) {
		MemberDTO.MemberResponse memberResponse = memberService.getMemberById(id);
		return ResponseEntity.ok(memberResponse);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update member details")
	public ResponseEntity<MemberDTO.MemberResponse> updateMember(@PathVariable Long id,
			@Valid @RequestBody MemberDTO.MemberRequest memberRequest) {

		MemberDTO.MemberResponse memberResponse = memberService.updateMember(id, memberRequest);
		return ResponseEntity.ok(memberResponse);
	}
}