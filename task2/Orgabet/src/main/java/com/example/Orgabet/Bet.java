package com.example.Orgabet;
import java.security.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bet {
	private String homeTeam;
	private String awayTeam;
	private String result;
	private double avgOdd;
	private List<Quote> quotes;
}
