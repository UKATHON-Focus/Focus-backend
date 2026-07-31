package com.focus.repository;

import com.focus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<User, Long> {}
