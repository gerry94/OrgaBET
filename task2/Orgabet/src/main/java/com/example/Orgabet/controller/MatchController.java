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

	@GetMapping("/removeBet")
	public String rmeoveBet(@RequestParam(required=true, value="id") String id, Model model){
		coupon.removeBet(id);
		//coupon.printCoupon();
		
		model.addAttribute("coupon", coupon);
		return "fragments :: coupon";
	}
	
	@GetMapping("/printQuote")
	public String printQuoteAjax(@RequestParam(required = true, value = "id") String id, @RequestParam(required = true, value = "type") String type, Model model) {//@RequestParam Map<String,String> param) {
		TableDTO t = findById(id);
		if(t == null) return null;
		
		Match m = t.getMatch();
		//scorrere il coupon per controllare che non ci sia già un match con stesso ID
		List<Bet> couponBets = this.coupon.getBets();
		
		boolean error = false;
		for(Iterator<Bet> cb = couponBets.iterator(); cb.hasNext();) {
			if(cb.next().getMatchId().equals(m.getId())) {
				System.out.println("ERROR: Can't play same match twice!");
				error = true;
				break;
			}
		}
		
		if(!error) {
			Bet b = new Bet();
			b.setMatchId(m.getId());
			b.setHomeTeam(m.getHomeTeam());
			b.setAwayTeam(m.getAwayTeam());
			b.setResult(type);
			b.setAvgOdd(t.getQuote(type));
			b.addQuotes(m.getQuoteList(type));
			
			coupon.addMatch(b);
			//coupon.printCoupon();
		}
		model.addAttribute("coupon", coupon);
		return "fragments :: coupon";
	}
	
	@ResponseBody
	@RequestMapping(value = "/clearCoupon")
	public void clearCoupon()
	{
		System.out.println("Coupon cleared!");
		//clear and refresh the object
		coupon = new Coupon();
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
	   public String viewMatches(@RequestParam(required = false, defaultValue = "Football", value="sport") String sport, @RequestParam(required = false, defaultValue = "I2", value="division")String division,@RequestParam(required = false, defaultValue = "21/09/2019", value="date") String date, Model model) {
		
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
		model.addAttribute("coupon",coupon);

	      return "match";
	   }
}