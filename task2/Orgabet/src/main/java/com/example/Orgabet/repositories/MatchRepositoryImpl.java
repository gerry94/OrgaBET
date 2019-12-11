package com.example.Orgabet.repositories;

import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<AvgDTO> computeAverageOdds(String sport, String date, String div) {
		MatchOperation filterSport = Aggregation.match(new Criteria("sport").is(sport));
		MatchOperation filterDate = Aggregation.match(new Criteria("date").is(date));
		MatchOperation filterDiv = Aggregation.match(new Criteria("division").is(div));
		
		GroupOperation avgBetType = Aggregation.group("odds.type").avg("odds.quotes.odd").as("avgOdd");
		
		Aggregation agg = Aggregation.newAggregation(filterSport, filterDate, filterDiv, avgBetType);
		
		System.out.println(agg.toString());
		
		List<AvgDTO> adto = mongoTemplate.aggregate(agg, "avgs", AvgDTO.class).getMappedResults();
		System.out.println("@@@@@@ AggregationResults: " + adto.toString());
		return adto;
	}
}
