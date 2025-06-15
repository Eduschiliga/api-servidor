package com.eduardo.apiservidor.repository.email;

import com.eduardo.apiservidor.entity.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query("FROM Email WHERE emailRemetente = ?1 AND status = 0 ORDER BY emailId DESC")
    List<Email> buscarRascunhos(String email);
}
