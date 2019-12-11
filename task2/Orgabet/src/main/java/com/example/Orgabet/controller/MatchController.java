package com.example.Orgabet.controller;

import java.util.List;
import java.util.Map;

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
	 
	      List<Match> list = matchRepository.computeAverageOdds("football", "23/10/2019", "I1");
	
	      model.addAttribute("matches", list);
	 
	      return "match";
	   }
	
}
