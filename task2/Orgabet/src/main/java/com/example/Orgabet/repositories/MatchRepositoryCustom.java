package com.example.Orgabet.repositories;


import java.util.List;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.StatsDTO;
import com.example.Orgabet.dto.countDTO;
import com.example.Orgabet.dto.divisionDTO;
import com.example.Orgabet.models.Match;


public interface MatchRepositoryCustom {
	
	//Function to select divisions to show up in a date
	List<divisionDTO> selectSortedDivisions(String date, String sport);
	//Functions for average odd aggregation
	List<Match> selectSortedMatches(String sport, String date, String div);
	List<AvgDTO> computeAverageOdds(String id);
	
	//Functions for statistics aggregations
	List<countDTO> selectTeamsHome(String sport, String date, String division);

	List<countDTO> selectTeamsAway(String sport, String date, String division);
	
	StatsDTO computeTeamHome(String division, String team, Double totHome, String sport, String date);

	StatsDTO computeTeamAway(String division, String team, Double totHome, String sport, String date);
	//Return list of winning tennis player and count of won matches
	List<countDTO> selectWinningTennisPlayer(String date, String surface);
	StatsDTO computeTennisPlayer(String surface, String player, Double totWin, String date);
	

}
