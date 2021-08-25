package com.example.demo.repo;

import com.example.demo.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	public Optional<Account> findByAccountNumber(String accountNumber);
}
