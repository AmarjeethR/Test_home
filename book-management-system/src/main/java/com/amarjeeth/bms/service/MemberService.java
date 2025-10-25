package com.amarjeeth.bms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amarjeeth.bms.dto.MemberDTO;
import com.amarjeeth.bms.entity.Member;
import com.amarjeeth.bms.exception.DuplicateResourceException;
import com.amarjeeth.bms.exception.ResourceNotFoundException;
import com.amarjeeth.bms.repository.MemberRepository;

import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	/**
	 * Register a new member
	 */
	public MemberDTO.MemberResponse registerMember(MemberDTO.MemberRequest memberRequest) {
		// Check if email already exists
		if (memberRepository.existsByEmail(memberRequest.getEmail())) {
			throw new DuplicateResourceException("Member with email '" + memberRequest.getEmail() + "' already exists");
		}

		// Create new member entity
		Member member = new Member(memberRequest.getEmail(), memberRequest.getName(), memberRequest.getPhoneNumber());

		// Save member
		Member savedMember = memberRepository.save(member);

		// Convert to response DTO
		return convertToResponseDTO(savedMember);
	}

	/**
	 * Get all members with pagination
	 */
	@Transactional(readOnly = true)
	public Page<MemberDTO.MemberResponse> getAllMembers(Pageable pageable) {
		return memberRepository.findAll(pageable).map(this::convertToResponseDTO);
	}

	/**
	 * Get member by ID
	 */
	@Transactional(readOnly = true)
	public MemberDTO.MemberResponse getMemberById(Long id) {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
		return convertToResponseDTO(member);
	}

	/**
	 * Update member
	 */
	public MemberDTO.MemberResponse updateMember(Long id, MemberDTO.MemberRequest memberRequest) {
		// Find existing member
		Member existingMember = memberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

		// Check if email is being changed and if new email already exists
		if (!existingMember.getEmail().equals(memberRequest.getEmail())
				&& memberRepository.existsByEmail(memberRequest.getEmail())) {
			throw new DuplicateResourceException("Member with email '" + memberRequest.getEmail() + "' already exists");
		}

		// Update member details
		existingMember.setEmail(memberRequest.getEmail());
		existingMember.setName(memberRequest.getName());
		existingMember.setPhoneNumber(memberRequest.getPhoneNumber());

		// Save updated member
		Member updatedMember = memberRepository.save(existingMember);

		return convertToResponseDTO(updatedMember);
	}

	/**
	 * Convert Member entity to Response DTO
	 */
	private MemberDTO.MemberResponse convertToResponseDTO(Member member) {
		return new MemberDTO.MemberResponse(member.getId(), member.getEmail(), member.getName(),
				member.getPhoneNumber(), member.getMembershipDate());
	}
}