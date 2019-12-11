package com.example.Orgabet.models;
import java.security.Timestamp;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Odd {
	private String type;
	private List<Quote> quotes;
	
	public Odd(
			final String type,
			final List<Quote> quotes) {
		this.type = type;
		this.quotes = quotes;
	}
}
