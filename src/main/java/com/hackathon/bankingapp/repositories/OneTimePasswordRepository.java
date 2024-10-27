package com.hackathon.bankingapp.repositories;

import com.hackathon.bankingapp.entities.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {

    OneTimePassword findByEmail(String email);

}
