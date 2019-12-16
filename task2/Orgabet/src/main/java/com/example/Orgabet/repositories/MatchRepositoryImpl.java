package com.example.Orgabet.repositories;

import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.StatsDTO;
import com.example.Orgabet.dto.countDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;

import com.example.Orgabet.models.Match;

public class MatchRepositoryImpl implements MatchRepositoryCustom {
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public MatchRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate =mongoTemplate;
	}
	
	@Override

	public List<Match> selectSortedMatches(String sport, String date, String division) {
		MatchOperation filterSport = Aggregation.match(new Criteria("sport").is(sport));
		MatchOperation filterDate = Aggregation.match(new Criteria("date").is(date));
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(division));
		
		SortOperation srt = Aggregation.sort(Sort.Direction.ASC, "time");
		
		Aggregation aggr = Aggregation.newAggregation(filterSport, filterDate, filterDiv, srt);
		
		List<Match> res = mongoTemplate.aggregate(aggr, Match.class, Match.class).getMappedResults();
		return res;
	}
	
	@Override
	public List<AvgDTO> computeAverageOdds(String id) {
		MatchOperation filterMatch = Aggregation.match(new Criteria("id").is(id));
		UnwindOperation unw = Aggregation.unwind("odds");
		UnwindOperation unw2 = Aggregation.unwind("odds.quotes");
		
		GroupOperation grp = Aggregation.group("odds.type").avg("odds.quotes.odd").as("avg");
		
		ProjectionOperation proj = Aggregation.project("avg");
		
		Aggregation aggr = Aggregation.newAggregation(filterMatch, unw, unw2, grp);

		List<AvgDTO> res = mongoTemplate.aggregate(aggr, Match.class, AvgDTO.class).getMappedResults();
								//prima class = sorgente, seconda = destin
		return res;

	}
	
	@Override
	public List<countDTO> selectTeamsHome(String sport, String date,String division){
		MatchOperation filterSport = Aggregation.match(new Criteria("sport").is(sport));
		MatchOperation filterDate = Aggregation.match(new Criteria("date").gt(date));
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(division));
		
		GroupOperation grp =  Aggregation.group("homeTeam").count().as("count");
		
		SortOperation srt = Aggregation.sort(Sort.Direction.ASC, "_id");
		
		Aggregation aggr = Aggregation.newAggregation(filterSport, /*filterDate,*/ filterDiv, grp, srt);
		
		List<countDTO> res = mongoTemplate.aggregate(aggr, Match.class, countDTO.class).getMappedResults();
		
		return res;
	}
	
	@Override
	public List<countDTO> selectTeamsAway(String sport, String date,String division){
		MatchOperation filterSport = Aggregation.match(new Criteria("sport").is(sport));
		MatchOperation filterDate = Aggregation.match(new Criteria("date").gt(date));
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(division));
		
		GroupOperation grp =  Aggregation.group("awayTeam").count().as("count");
		
		SortOperation srt = Aggregation.sort(Sort.Direction.ASC, "_id");
		
		Aggregation aggr = Aggregation.newAggregation(filterSport, /*filterDate,*/ filterDiv, grp, srt);
		
		List<countDTO> res = mongoTemplate.aggregate(aggr, Match.class, countDTO.class).getMappedResults();
		
		return res;
	}
	
	@Override 
	public StatsDTO computeTeamHome(String division, String team, Double totHome) {
		StatsDTO stats = null;
		double homeWin = 0.00, homeDraw = 0.00, homeLost = 0.00, homeOver = 0.00, homeUnder = 0.00;
		
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(division));
		MatchOperation filterTeam = Aggregation.match(new Criteria("homeTeam").is(team));
		MatchOperation filterHomeWin = Aggregation.match(new Criteria("fullTimeResult").is("H"));
		
		GroupOperation grpHW =  Aggregation.group("homeTeam").count().as("count");
		
		Aggregation aggr = Aggregation.newAggregation(filterDiv, filterTeam, filterHomeWin, grpHW);
		
		List<countDTO> res = mongoTemplate.aggregate(aggr, Match.class, countDTO.class).getMappedResults();
		
		try {
			homeWin = ((res.get(0).getCount())/totHome) * 100;
		} catch(Exception e){
			
		}
		
		MatchOperation filterHomeDraw = Aggregation.match(new Criteria("fullTimeResult").is("D"));
		GroupOperation grpHD =  Aggregation.group("homeTeam").count().as("count");
		
		Aggregation aggr2 = Aggregation.newAggregation(filterDiv, filterTeam, filterHomeDraw, grpHD);
		List<countDTO> res2 = mongoTemplate.aggregate(aggr2, Match.class, countDTO.class).getMappedResults();
		
		try {
			homeDraw = ((res2.get(0).getCount())/totHome) * 100;
		} catch(Exception e){
			
		}
		
		MatchOperation filterHomeLost = Aggregation.match(new Criteria("fullTimeResult").is("A"));
		GroupOperation grpHL =  Aggregation.group("homeTeam").count().as("count");
		
		Aggregation aggr3 = Aggregation.newAggregation(filterDiv, filterTeam, filterHomeLost, grpHL);
		List<countDTO> res3 = mongoTemplate.aggregate(aggr3, Match.class, countDTO.class).getMappedResults();
		
		try {
			homeLost = ((res3.get(0).getCount())/totHome) * 100;
		} catch(Exception e){
			
		}
		
		ProjectionOperation prjHO = Aggregation.project("id").and("fullTimeHomeScore").plus("fullTimeAwayScore").as("totScore");
		MatchOperation filterHomeOver = Aggregation.match(new Criteria("totScore").gt(2));
		GroupOperation grpHO = Aggregation.group().count().as("count");
		
		Aggregation aggr4 = Aggregation.newAggregation(filterDiv, filterTeam, prjHO, filterHomeOver, grpHO);
		
		List<countDTO> res4 = mongoTemplate.aggregate(aggr4, Match.class, countDTO.class).getMappedResults();
		
		try {
			homeOver = ((res4.get(0).getCount())/totHome) * 100;
		} catch(Exception e){
			
		}
		
		homeUnder = 100.00 - homeOver;
		
		UnwindOperation unw = Aggregation.unwind("odds");
		UnwindOperation unw2 = Aggregation.unwind("odds.quotes");
		
		GroupOperation grp = Aggregation.group("odds.type").avg("odds.quotes.odd").as("avg");
		
		ProjectionOperation proj = Aggregation.project("avg");
		
		Aggregation aggr5 = Aggregation.newAggregation(filterDiv, filterTeam, unw, unw2, grp);

		List<AvgDTO> res5 = mongoTemplate.aggregate(aggr5, Match.class, AvgDTO.class).getMappedResults();
		//System.out.println(res5.get(0).toString());
		stats = new StatsDTO(team, homeWin, homeDraw, homeLost, homeOver, homeUnder, res5);
		
		return stats;
	}
	
	@Override 
	public StatsDTO computeTeamAway(String division, String team, Double totAway) {
		StatsDTO stats = null;
		double awayWin = 0.00, awayDraw = 0.00, awayLost = 0.00, awayOver = 0.00, awayUnder = 0.00;
		
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(division));
		MatchOperation filterTeam = Aggregation.match(new Criteria("awayTeam").is(team));
		MatchOperation filterAwayWin = Aggregation.match(new Criteria("fullTimeResult").is("A"));
		
		GroupOperation grpAW =  Aggregation.group("awayTeam").count().as("count");
		
		Aggregation aggr = Aggregation.newAggregation(filterDiv, filterTeam, filterAwayWin, grpAW);
		//System.out.println(aggr.toString());
		List<countDTO> res = mongoTemplate.aggregate(aggr, Match.class, countDTO.class).getMappedResults();
		
		try {
			awayWin = ((res.get(0).getCount())/totAway) * 100;
		} catch (Exception e) {
			
		}
		
		MatchOperation filterAwayDraw = Aggregation.match(new Criteria("fullTimeResult").is("D"));
		GroupOperation grpAD =  Aggregation.group("awayTeam").count().as("count");
		
		Aggregation aggr2 = Aggregation.newAggregation(filterDiv, filterTeam, filterAwayDraw, grpAD);
		List<countDTO> res2 = mongoTemplate.aggregate(aggr2, Match.class, countDTO.class).getMappedResults();
		
		try {
			awayDraw = ((res2.get(0).getCount())/totAway) * 100;
		} catch(Exception e){
			
		}
		
		MatchOperation filterAwayLost = Aggregation.match(new Criteria("fullTimeResult").is("H"));
		GroupOperation grpAL =  Aggregation.group("awayTeam").count().as("count");
		
		Aggregation aggr3 = Aggregation.newAggregation(filterDiv, filterTeam, filterAwayLost, grpAL);
		List<countDTO> res3 = mongoTemplate.aggregate(aggr3, Match.class, countDTO.class).getMappedResults();
		
		try {
			awayLost = ((res3.get(0).getCount())/totAway) * 100;
		} catch(Exception e){
			
		}
		
		ProjectionOperation prjAO = Aggregation.project("id").and("fullTimeHomeScore").plus("fullTimeAwayScore").as("totScore");
		MatchOperation filterAwayOver = Aggregation.match(new Criteria("totScore").gt(2));
		GroupOperation grpAO = Aggregation.group().count().as("count");
		
		Aggregation aggr4 = Aggregation.newAggregation(filterDiv, filterTeam, prjAO, filterAwayOver, grpAO);
		
		List<countDTO> res4 = mongoTemplate.aggregate(aggr4, Match.class, countDTO.class).getMappedResults();

		try {
			awayOver = ((res4.get(0).getCount())/totAway) * 100;
		} catch(Exception e){
			
		}
		
		awayUnder = 100.00 - awayOver;
		
		stats = new StatsDTO(team, awayWin, awayDraw, awayLost, awayOver, awayUnder, null);
		return stats;
	}
}
