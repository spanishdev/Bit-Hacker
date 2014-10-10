package com.radioactivewasp.bithacker.free.io;

public class SurvivalScore {
	
	public long time; //Time in milliseconds
	public int touches; //how many buttons touched the player
	public int score; //self explanatory
	public int rounds;
	public String name;
	
	public SurvivalScore(String n, long tiempo, int toques, int punt, int oleadas)
	{
		name=n;
		time=tiempo;
		touches=toques;
		score=punt;
		rounds=oleadas;
	}
}
