package com.ss.airline;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

import com.ss.airline.Seat.SeatType;

public class SeatingChart {

	

	public Airplane generateAirplaneLayout(int[][] layout) throws Exception {
		
		Airplane airplane = new Airplane();
		int maxGroups = layout.length;
		int colOffset = 0;
		int groupNumber = 0;
		for(int[] group:layout) {
			if(group.length != 2)
				throw new RuntimeException("Invalid Input, Group dimensions needs exactly 2 values");
			
			for(int i=0;i<group[1];i++) {
				for(int j=0;j<group[0];j++) {
					Seat seat = new Seat();
					seat.id=airplane.seats.size();
					seat.group=groupNumber;
					seat.row=i;
					seat.column = colOffset+j;
					seat.type = getSeatType(groupNumber, j, maxGroups-1, group[0]-1);
					airplane.seats.add(seat);
					airplane.totalCountByType.put(seat.type, airplane.totalCountByType.get(seat.type)+1);
					
					if(airplane.seatRowMap.containsKey(i))
						airplane.seatRowMap.get(i).add(seat);
					else {
						List<Seat> l = new ArrayList<Seat>();
						l.add(seat);
						airplane.seatRowMap.put(i,l);
					}
					
					if(airplane.maxColumns < j+1)
						airplane.maxColumns = j+1;
				}
				
				if(airplane.maxRows < i+1)
					airplane.maxRows = i+1;
			
			}
			colOffset+=group[0]+1;
			groupNumber++;
		}
		return airplane;

	}
	
	
	public SeatType getSeatType(int groupNumber, int column, int maxGroups, int maxColumns) {
		
		if(groupNumber == 0 && column == 0)
			return SeatType.W;
		
		if(groupNumber == maxGroups && column == maxColumns)
			return SeatType.W;
		
		if(column == 0 || column == maxColumns)
			return SeatType.A;

		
		return SeatType.M;
		
		
	}
	
	public SeatType nextSeatType(Airplane a) {
		if(a.filled.get(SeatType.A) < a.totalCountByType.get(SeatType.A))
			return SeatType.A;
		
		if(a.filled.get(SeatType.W) < a.totalCountByType.get(SeatType.W))
			return SeatType.W;
		
		return SeatType.M;
		
	}
	
	public void assignSeat(int id, Airplane a) {
		if(id > a.seats.size())
			throw new RuntimeException("Plane Full, cannot add more than "+ a.seats.size()+" passengers.");
			
		SeatType type = nextSeatType(a);
		
		for(int i=0;i<a.maxRows;i++) {
			for(Seat s:a.seatRowMap.get(i)) {
				if(s.type == type && s.passengerId == 0) {
					s.passengerId = id;
					a.filled.put(type, a.filled.get(type)+1);
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		SeatingChart s = new SeatingChart();
		int[][] layout = {
				{3,2}, 
				{4,3},
				{2,3},
				{3,4},
				
		};
		try {
			Airplane a = s.generateAirplaneLayout(layout);
			
			for(int i=1; i<=30; i++)
				s.assignSeat(i, a);
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
