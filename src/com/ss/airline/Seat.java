package com.ss.airline;

public class Seat {

	public enum SeatType{
		A, W, M;
	}

	int id;
	int row;
	int column;
	SeatType type;
	int group;
	int passengerId;

	public String toString() {
		if(passengerId > 0)
			return type+":"+passengerId;
		
		return type+"";
		
	}
	
}
