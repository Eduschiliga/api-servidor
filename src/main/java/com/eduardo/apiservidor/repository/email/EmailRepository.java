package com.eduardo.apiservidor.repository.email;

import com.eduardo.apiservidor.entity.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}
