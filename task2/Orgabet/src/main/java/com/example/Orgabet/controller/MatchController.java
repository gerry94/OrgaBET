package com.example.Orgabet.controller;

import java.util.*;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.TableDTO;
import com.example.Orgabet.models.*;
import com.example.Orgabet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.Orgabet.repositories.MatchRepository;
import com.example.Orgabet.services.MongoUserDetailsService;

@Controller
public class MatchController {
	@Autowired
	MatchRepository matchRepository;
	@Autowired
	private MongoUserDetailsService userService;
	@Autowired
	UserRepository userRepository;
	
	public List<TableDTO> tbl;
	public Coupon coupon = new Coupon();
	
	public TableDTO findById(String id) {
		for(Iterator<TableDTO> t = tbl.iterator(); t.hasNext();) {
			TableDTO tdto = t.next();
			if(tdto.getMatch().getId().equals(id))
				return tdto;
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = "/printQuote")
	public void printQuoteAjax(@RequestParam Map<String,String> param) {
		TableDTO t = findById(param.get("id"));
		if(t == null) return;
		
		String oddType = param.get("type");
		Match m = t.getMatch();
		Bet b = new Bet();//(m.getHomeTeam(), m.getAwayTeam(), oddType, t.getQuote(oddType), m.getQuoteList(oddType));
		b.setHomeTeam(m.getHomeTeam());
		b.setAwayTeam(m.getAwayTeam());
		b.setResult(oddType);
		b.setAvgOdd(t.getQuote(oddType));
		b.addQuotes(m.getQuoteList(oddType));
		
		coupon.addMatch(b);
		coupon.printCoupon();
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveCoupon")
	public void saveCoupon() {
		System.out.println("Saving Coupon...");
		
		//codice che salva il Coupon nel current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = userService.findUserByUsername(auth.getName());
		
		coupon.setDate(new Date(System.currentTimeMillis()));
		currentUser.addCoupon(coupon);
		userRepository.save(currentUser);
		
		System.out.println("Coupon saved!");
		//clear and refresh the object
		coupon = new Coupon();
	}
	
	@RequestMapping("/match")
	   public String viewMatches(@RequestParam(required = false, defaultValue = "Football", value="sport") String sport, @RequestParam(required = false, defaultValue = "I1", value="division")String division,@RequestParam(required = false, defaultValue = "01/09/2019", value="date") String date, Model model) {


		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
		

		List<Match> list = matchRepository.selectSortedMatches(sport, date, division);

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