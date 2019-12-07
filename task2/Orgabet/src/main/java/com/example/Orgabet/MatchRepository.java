package com.example.Orgabet;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "matches", path = "matches")
public interface MatchRepository extends MongoRepository<Match, String> {
	List<Match> findByHomeTeam(@Param("homeTeam") String homeTeam);
	
	@Query("{'odd.type': ?0}")
	  List<Match> findByOddsType(final String type);
}
