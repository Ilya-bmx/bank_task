package com.example.demo.controller;

import com.example.demo.services.AccountService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
	public BigDecimal withdraw(@RequestParam("account-number") String accountNumber, @RequestParam("amount") BigDecimal moneyAmount) {
		return accountService.withdraw(accountNumber, moneyAmount);
	}

	@GetMapping("/balance")
	public BigDecimal getBalance(@RequestParam("account-number") String accountNumber) {
		return accountService.getBalance(accountNumber);
	}
}
