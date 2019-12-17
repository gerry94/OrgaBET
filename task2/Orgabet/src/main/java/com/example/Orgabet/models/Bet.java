package com.example.Orgabet.models;
import java.lang.reflect.Array;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
@Document
public class Bet {
	private String matchId;
	private Date date; //may cause problems if date is not correctly formatted in mongoDB
	private String time;
	private String homeTeam;
	private String awayTeam;
	private String result; //quote type (ex: H, D, OVER...)
	private Double avgOdd;
	private ArrayList<Quotes> quotes;
	
	public Bet(/*String h, String a, String res, Double avg, ArrayList<Quotes> q*/) {
		/*this.homeTeam = h;
		this.awayTeam = a;
		this.result = res;
		this.avgOdd = avg;*/
		this.quotes = new ArrayList<Quotes>();
		//this.quotes.addAll(q);
	}
	
	public void addQuotes(ArrayList<Quotes> q) {
		this.quotes.addAll(q);
	}
}
