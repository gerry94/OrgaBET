package com.example.Orgabet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.StatsDTO;
import com.example.Orgabet.dto.TableDTO;
import com.example.Orgabet.dto.TableStatsDTO;
import com.example.Orgabet.dto.countDTO;
import com.example.Orgabet.models.Match;
import com.example.Orgabet.models.User;
import com.example.Orgabet.repositories.MatchRepository;
import com.example.Orgabet.services.MongoUserDetailsService;


@Controller
public class StatsController {
	
	@Autowired
	private MongoUserDetailsService userService;
	
	@Autowired
	MatchRepository statsRepository;
	
	@RequestMapping("/stats")
	public List<String> viewStats(Model model) {
		//due to inconsistency in the dataset we need to re-format the date string according
		//to the specific format of each sport
		String day="24", month="08", year="2019";
		String sport = "Football", date = "", division = "I1";
		
		if(sport.equals("Basket"))
			date = day+"/"+month+"/"+year;
		else if(sport.equals("Tennis"))
			date = year+"/"+month+"/"+day;
		else {
			if(year.equals("2018") || year.equals("2019"))
				date = day+"/"+month+"/"+year;
			else date = day+"/"+month+"/"+(year.substring(year.length() - 2));
		}
		List<countDTO> list = statsRepository.selectTeamsHome(sport, date, division);
		List<countDTO> listA = statsRepository.selectTeamsAway(sport, date, division);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
		
		List<TableStatsDTO> tbl = new ArrayList<TableStatsDTO>();
		List<TableStatsDTO> tblA = new ArrayList<TableStatsDTO>();
		
		for(Iterator<countDTO> l = list.iterator(); l.hasNext();) {
			countDTO cnt = l.next();
			StatsDTO stats = statsRepository.computeTeamHome("I1",cnt.getId(), cnt.getCount());
		
			tbl.add(new TableStatsDTO(stats.getId(), stats.getHomeWin(), stats.getHomeDraw(), stats.getHomeLost(), stats.getHomeOver(), stats.getHomeUnder(), stats.getAvgOdds()));
		}
		
		model.addAttribute("statsH", tbl);
		
		for(Iterator<countDTO> l2 = listA.iterator(); l2.hasNext();) {
			countDTO cnt2 = l2.next();
			StatsDTO stats2 = statsRepository.computeTeamAway("I1",cnt2.getId(), cnt2.getCount());
		
			tblA.add(new TableStatsDTO(stats2.getId(), stats2.getHomeWin(), stats2.getHomeDraw(), stats2.getHomeLost(), stats2.getHomeOver(), stats2.getHomeUnder(), stats2.getAvgOdds()));
		}
		
		model.addAttribute("statsA", tblA);
		
		List<String> stats = new ArrayList<String>();
		stats.add("statsH");
		stats.add("statsA");
		
		return stats;
	}
}