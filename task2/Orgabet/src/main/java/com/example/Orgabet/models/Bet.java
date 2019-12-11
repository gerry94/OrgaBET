package com.example.Orgabet.models;
import java.security.Timestamp;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Bet {
	private String homeTeam;
	private String awayTeam;
	private String result;
	private double avgOdd;
	private List<Quote> quotes;
}
