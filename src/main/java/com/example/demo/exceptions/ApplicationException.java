package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class ApplicationException extends RuntimeException{
	public ApplicationException(String message) {
        super(message);
    }
}
