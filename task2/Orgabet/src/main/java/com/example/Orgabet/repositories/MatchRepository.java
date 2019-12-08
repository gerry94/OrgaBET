package com.example.Orgabet.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Orgabet.models.Match;

public interface MatchRepository extends MongoRepository<Match, String> {
	List<Match> findByHomeTeam(@Param("homeTeam") String homeTeam);
	
	@Query("{'odd.type': ?0}")
	  List<Match> findByOddsType(final String type);
}
