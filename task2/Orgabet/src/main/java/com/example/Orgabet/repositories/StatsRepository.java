package com.example.Orgabet.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Orgabet.models.Match;

public interface StatsRepository extends  MongoRepository<Match,String>, StatsRepositoryCustom {

}
