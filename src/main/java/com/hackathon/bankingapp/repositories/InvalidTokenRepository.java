package com.hackathon.bankingapp.repositories;

import com.hackathon.bankingapp.entities.InvalidToken;
import com.hackathon.bankingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {

    boolean existsByToken(String token);

}
