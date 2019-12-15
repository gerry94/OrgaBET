package com.example.Orgabet.repositories;

import java.util.Date;
import java.util.List;

import com.example.Orgabet.dto.StatsDTO;
import com.example.Orgabet.dto.countDTO;
import com.example.Orgabet.models.Match;

public interface StatsRepositoryCustom {
	List<countDTO> selectTeamsHome(String sport, String date, String division);

	List<countDTO> selectTeamsAway(String sport, String date, String division);
	
	StatsDTO computeTeamHome(String division, String team, Double totHome);

	StatsDTO computeTeamAway(String division, String team, Double totHome);

}
