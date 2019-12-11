package com.example.Orgabet.repositories;

import java.util.Date;
import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.models.Match;

public interface MatchRepositoryCustom {
	List<AvgDTO> computeAverageOdds(String sport, String date, String div);
}
