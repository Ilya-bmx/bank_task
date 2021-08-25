package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(
		schema = "public",
		name = "accounts",
		uniqueConstraints = @UniqueConstraint(columnNames = {"account_number"})
)
public class Account {

	// @Column(name = "id", nullable = false)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "balance")
	private BigDecimal balance;
}
