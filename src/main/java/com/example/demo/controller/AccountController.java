package com.example.demo.controller;

import com.example.demo.exceptions.ApplicationException;
import com.example.demo.services.AccountService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/create")
	public String createAccount(@RequestParam(value = "initial-balance", required = false) BigDecimal initBalance) {
		return accountService.createAccount(initBalance);
	}

	@PutMapping("/put")
	public void putMoney(@RequestParam("account-number") String accountNumber, @RequestParam("amount") BigDecimal moneyAmount) {
		accountService.replenishBalance(accountNumber, moneyAmount);
	}

	@GetMapping("/withdraw")
	public BigDecimal withdraw(@RequestParam("account-number") String accountNumber, @RequestParam(value = "amount", defaultValue = "0") BigDecimal moneyAmount) {
		return accountService.withdraw(accountNumber, moneyAmount);
	}

	@GetMapping("/balance")
	public BigDecimal getBalance(@RequestParam("account-number") String accountNumber) {
		return accountService.getBalance(accountNumber);
	}

	@PostMapping("/remittance")
	public void remittance(@RequestParam("source-account-number") String sourceAccountNumber, @RequestParam("target-account-number") String targetAccountNumber, @RequestParam("amount") BigDecimal amount) {
		accountService.remittance(sourceAccountNumber, targetAccountNumber, amount);
	}

	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleNoSuchElementFoundException(
			ApplicationException exception
	) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(exception.getMessage());
	}
}
