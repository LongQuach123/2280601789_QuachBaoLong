package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account; // <-- BẮT BUỘC PHẢI CÓ DÒNG NÀY

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
    @Query("SELECT a FROM Account a WHERE a.login_name = :login_name")
    Optional<Account> findByLoginName(String login_name); // <-- CHÚ Ý CHỮ Optional
}