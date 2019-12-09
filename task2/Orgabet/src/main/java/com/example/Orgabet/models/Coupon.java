package com.example.Orgabet.models;
import java.security.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coupon {
	private Timestamp timestamp;
	private List<Tot >bookmakerTot;
	private List<Bet> bets;
}
