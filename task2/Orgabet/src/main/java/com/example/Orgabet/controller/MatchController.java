package com.example.Orgabet.controller;

import java.util.*;

import com.example.Orgabet.dto.AvgDTO;
import com.example.Orgabet.dto.TableDTO;
import com.example.Orgabet.models.*;
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
		//System.out.println("[DBG] Ajax request: " + param.entrySet());
		TableDTO t = findById(param.get("id"));
		if(t == null) return;
		
		String oddType = param.get("type");
		Match m = t.getMatch();
		Bet b = new Bet(m.getHomeTeam(), m.getAwayTeam(), oddType, t.getQuote(oddType), m.getQuoteList(oddType));
		//System.out.println("\n[DBG] Bet object: " + b.toString());
		coupon.addMatch(b);
		coupon.printCoupon();
		
		//try { System.out.println("[DBG]: " + buildContent(coupon)); } catch(Exception e) { System.out.println("Exception caought!"); }
	}
	
	/*private String buildContent(Coupon c) {
		Context context = new Context();
		context.setVariable("coupons", c);
		Set<String> fragmentsSelectors = new HashSet<>();
		fragmentsSelectors.add("coupon");
		
		return templateEngine.process("coupon",fragmentsSelectors, context);
	}*/

	
	@RequestMapping("/match")
	   public String viewMatches(@RequestParam(required = false, defaultValue = "football", value="sport") String sport, @RequestParam(required = false, defaultValue = "I1", value="division")String division, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    		User currentUser = userService.findUserByUsername(auth.getName());
		model.addAttribute("currentUser", currentUser);
		
		List<Match> list = matchRepository.selectSortedMatches(sport, "01/09/2019", division);
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