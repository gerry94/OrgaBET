package com.example.Orgabet.dto;

import com.example.Orgabet.models.*;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class TableStatsDTO {
	String team;
	Double homeWin;
	Double homeDraw;
	Double homeLost;
	Double homeOver;
	Double homeUnder;

	
	public TableStatsDTO(String t, Double HW, Double HD, Double HL, Double HO, Double HU) {
		this.team = t;
		this.homeWin = HW;
		this.homeDraw = HD;
		this.homeLost = HL;
		this.homeOver = HO;
		this.homeUnder = HU;

	}
}
