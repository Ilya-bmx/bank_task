package com.example.demo.services;

import com.example.demo.exceptions.ApplicationException;
import com.example.demo.model.Account;
import com.example.demo.repo.AccountRepository;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AccountService {

	private final AccountRepository accountRepository;

	@Transactional
	public void remittance(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
		withdraw(sourceAccountNumber, amount);
		replenishBalance(targetAccountNumber, amount);
	}

	@Transactional
	public void replenishBalance(String accountNumber, BigDecimal amount) {
		Account account = getAccount(accountNumber);
		replenishAccountBalance(account, amount);
	}

	private void replenishAccountBalance(Account account, BigDecimal amount) {
		BigDecimal replenishedBalance = account.getBalance().add(amount);
		account.setBalance(replenishedBalance);
	}

	@Transactional
	public String createAccount(BigDecimal amount) {
		String accountNumber = generateAccountNumber();
		BigDecimal initialBalance = amount == null ? BigDecimal.ZERO : amount;
		Account account = Account.builder()
				.accountNumber(accountNumber)
				.balance(initialBalance)
				.build();
		accountRepository.save(account);
		return accountNumber;
	}

	private String generateAccountNumber() {
		return String.valueOf(new Date().getTime());
	}

	@Transactional
	public BigDecimal withdraw(String accountNumber, BigDecimal amount) {
		Account account = getAccount(accountNumber);
		return withdraw(account, amount);
	}

	private BigDecimal withdraw(Account account, BigDecimal amount) {
		BigDecimal currentBalance = account.getBalance();
		BigDecimal newBalance = currentBalance.subtract(amount);

		if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new ApplicationException("Вы не можете списать больше, чем есть на вашем счёте");
		}

		account.setBalance(newBalance);
		return amount;
	}

	@Transactional
	public BigDecimal getBalance(String accountNumber) {
		Account account = getAccount(accountNumber);
		return account.getBalance();
	}

	@NotNull
	private Account getAccount(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new ApplicationException("Счёта с номером: " + accountNumber + " не существует"));
	}
}
