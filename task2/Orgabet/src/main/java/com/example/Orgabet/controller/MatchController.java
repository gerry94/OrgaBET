package com.example.Orgabet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.Orgabet.dto.AvgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.Orgabet.models.Match;
import com.example.Orgabet.repositories.MatchRepository;

@Controller
public class MatchController {
	@Autowired
	MatchRepository matchRepository;
	
	@RequestMapping("/match")
	   public String viewMatches(Model model) {
	 	
		//ArrayList<Match> test = matchRepository.findByHomeTeam("Fiorentina");
		//System.out.println("TEST: " + test.get(0).toString());
		
		List<AvgDTO> list = matchRepository.computeAverageOdds("Football", "30/08/2019", "I1");
		try {
			System.out.println(list.get(0));
		} catch(IndexOutOfBoundsException iob) { System.out.println("IndexOutOfBoundsException!"); }
		
		model.addAttribute("matches", list);
	      return "match";
	   }
}
