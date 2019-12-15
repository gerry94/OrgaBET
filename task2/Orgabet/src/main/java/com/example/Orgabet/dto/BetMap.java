package com.example.Orgabet.dto;

import com.example.Orgabet.models.Match;
import lombok.*;

//this class maps a Match with the type of odd played (ex 1, X, OVER...)
@Getter
@Setter
public class BetMap
{
	private Match match;
	private String type;
	
	public BetMap(Match m, String t) {
		this.match = m;
		this.type = t;
	}
}
