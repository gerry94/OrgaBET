package com.example.Orgabet.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.example.Orgabet.dto.AggregationDTO;
import com.example.Orgabet.models.Match;

public class MatchRepositoryImpl implements MatchRepositoryCustom {
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public MatchRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate =mongoTemplate;
	}

	@Override
	public List<Match> computeAverageOdds(String sport, String date, String div) {
		  MatchOperation filterSport = Aggregation.match(new Criteria("Match.sport").is(sport));
		  MatchOperation filterDate = Aggregation.match(new Criteria("Match.Date").is(date));
		  MatchOperation filterDiv = Aggregation.match(new Criteria("Match.Div").is(div));

		  GroupOperation avgBetType = Aggregation.group("Match.odds.type").avg("quotes.odd").as("avgOdd");

		  Aggregation agg = Aggregation.newAggregation(filterSport, filterDate, filterDiv, avgBetType);
		  return mongoTemplate.aggregate(agg, "avgs", Match.class).getMappedResults();
	}
}
