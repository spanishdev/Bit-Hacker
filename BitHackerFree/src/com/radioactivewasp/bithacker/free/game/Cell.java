package com.radioactivewasp.bithacker.free.game;

import com.radioactivewasp.bithacker.free.R;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

public class Cell {

	private int ID,type, childs;
	private int[] children;
	private Status estado,aux;
	private String value;
	private int posx,posy,anchura,altura;
	public enum Status { NORMAL, ACTIVATED, PRESSED }
	private Puzzle puzzle;
	
	/*public Cell(int _id, String v, int t, int c, int px, int py, int w, int h)
	{
		ID=_id;
		value=v;
		type=t;
		posx=px;
		posy=py;
		anchura=w;
		altura=h;
		childs=c;
		children=new int[c];
		estado=Status.NORMAL;
		aux=Status.NORMAL;
	}*/
	
	public Cell(Puzzle p, int _id, String v, int t, int c, int w, int h)
	{
		ID=_id;
		value=v;
		type=t;
		anchura=w;
		altura=h;
		childs=c;
		children=new int[c];
		//estado=Status.NORMAL;
		//aux=Status.NORMAL;
		setValue(value);
		puzzle=p;
	}
	
	public void switchType(boolean parent)
	{
		if(value.equals("0")){
			value="1";
			estado=Status.ACTIVATED;
		}
		else if(value.equals("1")){
			value="0";
			estado=Status.NORMAL;
		}
		else if(value.equals("00")){
			value="01";
			estado=Status.NORMAL;
		}
		else if(value.equals("01")){
			value="10";
			estado=Status.NORMAL;
		}
		else if(value.equals("10")){
			value="11";
			estado=Status.ACTIVATED;
		}
		else if(value.equals("11")){
			value="00";
			estado=Status.NORMAL;
		}
		

		if(childs>0 && parent){
			for(int i=0; i<children.length; i++)
			{
				puzzle.switchTypeCell(children[i],false);
			}
		}
	}
	
	public boolean isDeactivated()
	{
		boolean b=false;
		if(estado.equals(Status.NORMAL))
		{
			if(value.equals("0") || value.equals("00")) b=true;
		}
		
		return b;
	}
	
	public void select(boolean parent)
	{
		if(!estado.equals(Status.PRESSED))
		{
			aux=estado;
			estado=Status.PRESSED;
			
			if(childs>0 && parent){
				for(int i=0; i<children.length; i++)
				{
					puzzle.selectCell(children[i],false);
				}
			}
		}
		/*else
		{
			estado=aux;
			if(childs>0 && parent){
				for(int i=0; i<children.length; i++)
				{
					puzzle.selectCell(children[i],false);
				}
			}
		}*/
	}
	
	public void unselect(boolean parent)
	{
		if(estado.equals(Status.PRESSED))
		{
			estado=aux;
			if(childs>0 && parent){
				for(int i=0; i<children.length; i++)
				{
					puzzle.unselectCell(children[i],false);
				}
			}
		}
	}
	
	public void setPos(int x, int y)
	{
		posx=x;
		posy=y;
	}
	
	/**
	 * 
	 * GETS
	 */
	
	public int getID() { return ID; }
	
	public String getValue() { return value; }
	
	public Status getStatus() { return estado; }
	
	public int getType() { return type; }
	
	public int getChild(int i){ return children[i]; }
	
	public int getX() { return posx; }
	
	public int getY() { return posy; }
	
	public int getW() { return anchura; }
	
	public int getH() { return altura; }
	
	public boolean pressed(int x, int y) {
		return x>=posx && x<=posx+anchura && y>=posy && y<=posy+altura;
	}
	
	public int[] getPos() { return new int[]{posx,posy}; }
	
	public int[] getSize() { return new int[]{anchura,altura}; }
	
	public int getStatusInt(Status s)
	{
		int i=-1;
		switch(s)
		{
			case NORMAL:
				i=0;
				break;
				
			case ACTIVATED:
				i=1;
				break;
				
			case PRESSED:
				i=2;
				break;
				
		}
		
		return i;
	}
	
	
	/**
	 * 
	 * SETS
	 */
	
	public void setValue(String n){
		value=n;
		if(value.equals("1") || value.equals("11"))
		{
			estado=Status.ACTIVATED;
		}
		else
		{
			estado=Status.NORMAL;
		}
		aux=estado;
	}
	
	public void setType(int t)
	{
		type=t;
	}
	
	public void setStatus(Status s)
	{
		estado=s;
	}
	
	
	public void addChild(int i, int c)
	{
		children[i]=c;
	}
	
	public void draw(Canvas canvas, Paint p, int fontSize)
	{
		GraphicsManager.drawImage(canvas, (3*type)+getStatusInt(estado), posx, posy);

		int previous=p.getColor();
		
		switch(type)
		{
			case 0:
				p.setColor(Color.GREEN);
				break;
				
			case 1:
				p.setColor(Color.RED);
				break;
				
			case 2:
				p.setColor(Color.rgb(255, 166, 0));
				break;
		}
		
		if(estado!=Status.NORMAL) p.setColor(Color.WHITE);
		
		p.setTextAlign(Paint.Align.CENTER);
		p.setTextSize(fontSize);
		Rect bounds = new Rect();
		p.getTextBounds(value, 0, value.length(), bounds);
		canvas.drawText(value, posx+anchura/2, posy+(altura/2)+(bounds.height()/2), p);
		p.setColor(previous);
	}
	
	
}
