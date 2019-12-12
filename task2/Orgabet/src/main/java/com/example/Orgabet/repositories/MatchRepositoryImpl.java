package com.example.Orgabet.repositories;



import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
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
}
