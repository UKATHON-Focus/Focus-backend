package com.focus.repository;

import com.focus.domain.Application;
import com.focus.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserOrderByApplicationIdDesc(User user);
}
