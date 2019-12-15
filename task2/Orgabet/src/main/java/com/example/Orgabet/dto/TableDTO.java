package com.example.Orgabet.dto;

import com.example.Orgabet.models.*;
import lombok.*;

import java.util.Iterator;
import java.util.List;

@ToString
@Getter
@Setter
public class TableDTO
{
	private Match match;
	private List<AvgDTO> avg;
	
	public TableDTO(Match m, List<AvgDTO> a) {
		this.match = m;
		this.avg = a;
	}
	
	//returns quote of given type
	public Double getQuote(String type) {
		for(Iterator<AvgDTO> a = avg.iterator(); a.hasNext();) {
			AvgDTO quote = a.next();
			if(quote.getId().equals(type))
				return quote.getAvg();
		}
		return null;
	}
}
