package com.example.Orgabet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.mongodb.internal.connection.Time;

@Getter
@Setter
public class Match {
	@Id 
	private String Id;
	@Indexed
	private String sport;
	@Indexed
	private String division;
	@Indexed
	private Date date;
	private Time time;
	private String homeTeam;
	private String awayTeam;
	private Integer fullTimeHomeG;
	private Integer fullTimeAwayG;
	private String FullTimeResult;
	private List<Odd> odds;
	
	public Match(
			final String sport,
			final String division,
			/*final Date date,
			final Time time,*/
			final String homeTeam,
			final String awayTeam,
			/*final Integer fullTimeHomeG,
			final Integer fullTimeAwayG,
			final String FullTimeResult,*/
			final List<Odd> odds) {
		this.sport = sport;
		this.division = division;
		this.date = date;
		this.time = time;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.fullTimeHomeG = fullTimeHomeG;
		this.fullTimeAwayG = fullTimeAwayG;
		this.FullTimeResult = FullTimeResult;
		this.odds = odds;
	}
}
