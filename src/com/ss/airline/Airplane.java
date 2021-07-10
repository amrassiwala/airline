package com.ss.airline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ss.airline.Seat.SeatType;

public class Airplane {

	int id;
	String type;
	List<Seat> seats = new ArrayList<Seat>();


	int maxRows;
	int maxColumns;

	Map<Integer,Integer> groupColumnMapping = new HashMap<Integer, Integer>();
	
	//Map<SeatType, List<Seat>> seatTypeMap = new HashMap<Seat.SeatType, List<Seat>>(); 
	Map<Integer,List<Seat>> seatRowMap = new HashMap<Integer, List<Seat>>();

	Map<SeatType,Integer> totalCountByType = new HashMap<Seat.SeatType, Integer>();
	Map<SeatType,Integer> filled = new HashMap<Seat.SeatType, Integer>();

	
	public Airplane() {
		totalCountByType.put(SeatType.A, 0);
		totalCountByType.put(SeatType.W, 0);
		totalCountByType.put(SeatType.M, 0);
		
		filled.put(SeatType.A, 0);
		filled.put(SeatType.W, 0);
		filled.put(SeatType.M, 0);
	}
	

	public String toString() {
		StringBuilder str=new StringBuilder();

		for(int i=0;i<maxRows;i++) {
			int col = 0;
			for(Seat s:seatRowMap.get(i)) {
				while(col < s.column) {
					str.append("\t");
					col++;
				}
				str.append(s);
			}
			str.append("\n");
		}
		
		return str.toString();
	}

}
