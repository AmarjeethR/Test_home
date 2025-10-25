package com.amarjeeth.bms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amarjeeth.bms.entity.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// Find member by email
	Optional<Member> findByEmail(String email);

	// Check if email exists
	boolean existsByEmail(String email);

	// Find all members with pagination
	Page<Member> findAll(Pageable pageable);
}