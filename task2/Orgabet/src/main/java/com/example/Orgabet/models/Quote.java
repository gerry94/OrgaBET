package com.example.Orgabet.models;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.mongodb.internal.connection.Time;

@Getter
@Setter
public class Quote {
	private String bookmaker;
	private Double odd;
	
	public Quote(
			final String bookmaker,
			final Double odd) {
		this.bookmaker = bookmaker;
		this.odd = odd;
	}
}
