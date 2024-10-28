package com.hackathon.bankingapp.repositories;

import com.hackathon.bankingapp.entities.Account;
import com.hackathon.bankingapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUser(User user);

}
