package com.example.Orgabet.dto;

import com.example.Orgabet.models.*;
import lombok.*;

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
}
