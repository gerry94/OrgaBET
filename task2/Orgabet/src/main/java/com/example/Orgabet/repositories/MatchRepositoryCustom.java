package com.example.Orgabet.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.models.Match;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface MatchRepositoryCustom {
	List<Match> selectSortedMatches(String sport, String date, String div);
	List<AvgDTO> computeAverageOdds(String id);
}
