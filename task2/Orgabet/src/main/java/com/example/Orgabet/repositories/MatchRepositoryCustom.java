package com.example.Orgabet.repositories;


import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.models.Match;


public interface MatchRepositoryCustom {

	List<Match> selectSortedMatches(String sport, String date, String div);
	List<AvgDTO> computeAverageOdds(String id);

}
