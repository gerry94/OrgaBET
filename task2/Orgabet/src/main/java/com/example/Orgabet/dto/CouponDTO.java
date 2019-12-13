package com.example.Orgabet.dto;

import com.example.Orgabet.models.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class CouponDTO
{
	//this is a list of (Match, played_odd) elements example: <(Milan-Roma),  (OVER 2.5)>
	public List<BetMap> matchList; //---> usare il TableDTO e cancellare BetMap
	
	public CouponDTO() {
		this.matchList = new ArrayList<BetMap>();
	}
	//add a match to the list
	public void addMatch(Match m, String type) {
		BetMap b = new BetMap(m, type);
		
		if(matchList.isEmpty()) matchList.add(b);
		else {
			//if this match already exists don't do anything else add it
			Boolean found = false;
			
			for(int i=0; i<matchList.size(); i++) {
				if(found = matchList.get(i).getMatch().getId().equals(m.getId()))
				{
					System.out.println("You can't play this match again!");
					break;
				}
			}
			if(!found) matchList.add(b);
		}
	}
	
	public void printCoupon() {
		System.out.println("\n=============== MyCoupon ================");
		for(BetMap b: matchList)
			System.out.println(b.getMatch().getHomeTeam() + "-" + b.getMatch().getAwayTeam() + "\t" + b.getType());
		System.out.println("=========================================");
	}
}
