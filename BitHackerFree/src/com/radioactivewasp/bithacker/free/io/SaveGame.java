package com.radioactivewasp.bithacker.free.io;

public class SaveGame {
	
	public long time; //Time in seconds
	public int touches; //how many buttons touched the player
	public int score; //self explanatory
	public int opttouch;
	public long opttime;
	
	
	public SaveGame(long tiempo, int toques, long toptimo, int toqueoptimo, int puntuacion)
	{
		time=tiempo;
		touches=toques;
		score=puntuacion;
		opttouch=toqueoptimo;
		opttime=toptimo;
	}
}
