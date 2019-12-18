package com.example.Orgabet.models;

import java.util.*;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.support.PropertyComparator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document
public class Coupon implements Comparable<Coupon>{
	private ObjectId id;
	private Date date;
	private List<Tot> bookmakerTot; //list of total bet multipliers for each available bookmaker
	private List<Bet> bets; //list of my bets
	
	public Coupon() {
		this.id= new ObjectId();
		this.date = null;
		this.bookmakerTot = new ArrayList<>();
		this.bets = new ArrayList<>();
	}
	
	public void addMatch(Bet b) {
		if(this.bets.isEmpty())	{
			//add match with all its bookmakers
			this.bets.add(b);
			for(Iterator<Quotes> quotes = b.getQuotes().iterator(); quotes.hasNext();) {
				Tot t = new Tot();
				Quotes q = quotes.next();
				t.setBookmaker(q.getBookmaker());
				t.setQuoteTot(q.getOdd());
				
				this.bookmakerTot.add(t);
			}
		} else {
			this.bets.add(b);
			
			//keeps track of which Tot objects in bookmakersTot were updated
			List<Tot> exisitingQuotes = new ArrayList<>();
			
			//check each bookmaker and combine the quotes (if possible)
			for(Iterator<Quotes> quotes = b.getQuotes().iterator(); quotes.hasNext();) {
				Quotes q = quotes.next();
				Tot tmp = updateQuote(q);
				if(tmp != null) //if an elem is updatet, I keep track of it
					exisitingQuotes.add(tmp);
			}
			//remove from bookmakersTot all elems that are not in existingQuotes
			this.bookmakerTot.retainAll(exisitingQuotes);
			
		}
	}
	
	public Tot updateQuote(Quotes q) {
		for(Iterator<Tot> t = this.bookmakerTot.iterator(); t.hasNext();) {
			Tot tmp = t.next();
			if(tmp.getBookmaker().equals(q.getBookmaker())) {
				tmp.setQuoteTot(tmp.getQuoteTot()*q.getOdd());
				return tmp;
			}
		}
		return null;
	}
	
	public void removeBet(String id) {
		for(Iterator<Bet> cb = this.bets.iterator(); cb.hasNext();) {
			if(cb.next().getMatchId().equals(id))
			{
				cb.remove();
				return;
			}
		}
	}
	
	//function used for debug
	public void printCoupon() {
		System.out.print("\033[H\033[2J");  //"clear" the screen
		System.out.flush();
		
		System.out.println("\n=============== MyCoupon ================");
		for(Bet b: this.bets)
			System.out.println(b.getHomeTeam() + "-" + b.getAwayTeam() + "\t" + b.getResult() + "\t" + b.getAvgOdd());
		System.out.println("------------------------------------------");
		for(Tot t: this.bookmakerTot)
			System.out.println(t.getBookmaker() +": "+t.getQuoteTot());
		System.out.println("=========================================");
	}
  	
	public boolean equals(Coupon other){
	    boolean result;
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } // end if
	    else{
	        Coupon otherCoupon = (Coupon)other;
	        result = date.equals(other.date)&&id.equals(other.id);
	    } // end else

	    return result;
	} // end equals
	
	@Override
	public int compareTo(Coupon o) {
		
		return -(date.compareTo(o.getDate()));
	}
}
