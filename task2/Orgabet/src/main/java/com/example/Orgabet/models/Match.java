package com.example.Orgabet.models;
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
	@Id private String Id;
	private String sport;
	private String division;
	private Date date;
	private Time time;
	private String homeTeam;
	private String awayTeam;
	private Integer fullTimeHomeG;
	private Integer fullTimeAwayG;
	private String FullTimeResult;
	private List<Odd> odds;
	
	//football-only attribute
	private Integer halfTimeHomeG;
	private Integer halfTimeHomeA;
	private String halfTimeResult;
	private Integer attendance;
	private String referee;
	private Integer homeShots;
	private Integer awayShots;
	private Integer homeOnTarget;
	private Integer awayOnTarget;
	private Integer homeWoodworks;
	private Integer awayWoodworks;
	private Integer homeCorners;
	private Integer awayCorners;
	private Integer homeFouls;
	private Integer awayFouls;
	private Integer homeFreeKicksConceded;
	private Integer awayFreeKicksConceded;
	private Integer homeOffsides;
	private Integer awayOffsides;
	private Integer homeYellow;
	private Integer awayYellow;
	private Integer homeRed;
	private Integer awayRed;
	private Integer homeBookingPoints;
	private Integer awayBookingPoints;
	
	//tennis-only attribute
	private Integer ATP;
	private String location;
	private String series;
	private String court;
	private String surface;
	private String round;
	private Integer bestOf;
	private Integer homeRank;
	private Integer awayRank;
	private Integer homeS1;
	private Integer awayS1;
	private Integer homeS2;
	private Integer awayS2;
	private Integer homeS3;
	private Integer awayS3;
	private Integer homeS4;
	private Integer awayS4;
	private Integer homeS5;
	private Integer awayS5;
	private String comment;
	
	//basket-only attribute
	private String season;
	private String typeOfGame;
	private String over;
	
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
