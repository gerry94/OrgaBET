package com.example.Orgabet.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Orgabet.models.Match;

public interface MatchRepository extends MongoRepository<Match, String>, MatchRepositoryCustom {

	List<Match> computeAverageOdds(String sport, String date, String div);
	
}
