package com.example.Orgabet;
import java.security.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
