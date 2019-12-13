package com.example.Orgabet.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.CouponDTO;
import com.example.Orgabet.dto.TableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.Orgabet.models.Match;
import com.example.Orgabet.repositories.MatchRepository;

@Controller
public class MatchController {
	@Autowired
	MatchRepository matchRepository;
	
	public List<TableDTO> tbl;
	public CouponDTO coupon = new CouponDTO();
	
	public Match findById(String id) {
		
		for(Iterator<TableDTO> t = tbl.iterator(); t.hasNext();) {
			TableDTO tdto = t.next();
			if(tdto.getMatch().getId().equals(id))
				return tdto.getMatch();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/printQuote")
	public void printQuoteAjax(@RequestParam Map<String,String> param) {
		//System.out.println("[DBG] Ajax request: " + param.entrySet());
		Match m = findById(param.get("id"));
		String type = param.get("type");
		
		if(m != null) {
			coupon.addMatch(m, type);
			coupon.printCoupon();
		}
	}
	
	@RequestMapping("/match")
	   public String viewMatches(Model model) {
		
		List<Match> list = matchRepository.selectSortedMatches("Football", "01/09/2019", "I1");
		tbl = new ArrayList<TableDTO>();
		
		for(Iterator<Match> l = list.iterator(); l.hasNext();) {
			Match match = l.next();
			
			List<AvgDTO> list2 = matchRepository.computeAverageOdds(match.getId());
			 tbl.add(new TableDTO(match, list2));
		}
		
		model.addAttribute("matches", tbl);

	      return "match";
	   }
}