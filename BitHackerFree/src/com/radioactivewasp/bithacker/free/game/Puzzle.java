package com.radioactivewasp.bithacker.free.game;

import com.radioactivewasp.bithacker.free.PuzzleScreen;
import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.SurvivalScreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class Puzzle extends View{

	private Cell[][] graella;
	private int F,C;
	private int num_cells;
	private boolean initialized=false;
	private int fontSize;
	private Paint p;
	private float[] touch,touchup;
	private int[] selected;
	private boolean locked=false;
	private int toques;
	private Status estado;
	private int mode;
	
	public enum Status { INGAME, PAUSED, FINISHED }
	
	/***
	 * 
	 * CONSTRUCTORS
	 * 
	 */
	public Puzzle(Context context)
	{
		super(context);
		p=new Paint();
		fontSize=context.getResources().getInteger(R.integer.sizeb);
		touch=new float[]{-1f,-1f};
		touchup=new float[]{-1f,-1f};
		selected=new int[]{-1,-1};
	}
	
	public Puzzle(Context context, AttributeSet attr)
	{
		super(context,attr);
		p=new Paint();
		fontSize=context.getResources().getInteger(R.integer.sizeb);
		touch=new float[]{-1f,-1f};
		touchup=new float[]{-1f,-1f};
		selected=new int[]{-1,-1};
	}
	
	public Puzzle(Context context,AttributeSet attr, int defstyle)
	{
		super(context,attr,defstyle);
		p=new Paint();
		fontSize=context.getResources().getInteger(R.integer.sizeb);
		touch=new float[]{-1f,-1f};
		touchup=new float[]{-1f,-1f};
		selected=new int[]{-1,-1};
	}
	
	public void initialize(int files, int columnes, int _mode)
	{
		F=files;
		C=columnes;
		mode=_mode;
		graella=new Cell[F][C];
		num_cells=0;
		toques=0;
		estado=Status.INGAME;
		if(mode==0) ((PuzzleScreen)(this.getContext())).setTouches(((Integer)toques).toString());
		else ((SurvivalScreen)(this.getContext())).setTouches(((Integer)toques).toString());
	}
	
	public void restart()
	{
		invalidate();
		toques=0;
		estado=Status.INGAME;
		if(mode==0) ((PuzzleScreen)(this.getContext())).setTouches(((Integer)toques).toString());
		else ((SurvivalScreen)(this.getContext())).setTouches(((Integer)toques).toString());
	}
	
	public void addCell(String value, int type, int[] children,  int w, int h)
	{
		Cell c=new Cell(this,num_cells,value,type,children.length,w,h);
		for(int i=0; i<children.length; i++) c.addChild(i, children[i]);
		graella[num_cells/C][num_cells%C]=c;
		num_cells++;
	}
	
	public void modifyCell(String value, int type, int j)
	{
		graella[j/C][j%C].setValue(value);
		graella[j/C][j%C].setType(type);
	}
	
	/***
	 * 
	 * GETS
	 * 
	 */
	
	public int getGridW() { 
		int w=0;
		w=C*GraphicsManager.getImagen(0).getIntrinsicWidth()+(C-1)*10;
		return w;
	}
	
	public int getGridH() { 
		int h=0;
		h=F*GraphicsManager.getImagen(0).getIntrinsicHeight()+(F-1)*10;
		return h;
	}
	
	public int lateralMargin(int width)
	{
		return (width-getGridW())/2;
	}
	
	public int frontalMargin(int height)
	{
		return (height-getGridH())/2;
	}
	
	public int getToques()
	{
		return toques;
	}
	
	public boolean isFinished() 
	{
		return estado.equals(Status.FINISHED);
	}
	
	public boolean isPaused() 
	{
		return estado.equals(Status.PAUSED);
	}
	
	public boolean isIngame() 
	{
		return estado.equals(Status.INGAME);
	}
	
	/***
	 * 
	 * SETS
	 * 
	 */
	
	public void selectCell(int i, boolean parent)
	{
		graella[i/C][i%C].select(parent);
	}
	
	public void unselectCell(int i, boolean parent)
	{
		graella[i/C][i%C].unselect(parent);
	}
	
	public void switchTypeCell(int i, boolean b)
	{
		graella[i/C][i%C].switchType(b);
	}
	
	public void setTouchCoord(float x, float y)
	{
		touch[0]=x;
		touch[1]=y;
	}
	
	public void setTouchUpCoord(float x, float y)
	{
		touchup[0]=x;
		touchup[1]=y;
	}
	
	public void setStatus(Status s) 
	{
		estado=s;
	}
	
	public void switchPaused()
	{
		if(!isFinished())
		{
			if(isPaused()) setStatus(Status.INGAME);
			else{
				setStatus(Status.PAUSED);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		
		if(isIngame()){
		
			switch(event.getAction()){
				
			case MotionEvent.ACTION_DOWN:
					if(!locked){
						for(int i=0; i<F; i++){
							for(int j=0; j<C; j++){
								if(graella[i][j].pressed((int)event.getX(), (int)event.getY())){
									graella[i][j].select(true);
									locked=true;
								}
							}
						}
					}
					break;
					
			case MotionEvent.ACTION_MOVE:
					break;
					
				case MotionEvent.ACTION_UP:
					
					if(locked){
						for(int i=0; i<F; i++){
							for(int j=0; j<C; j++){
								
								//if(graella[i][j].getStatus().equals(Cell.Status.PRESSED))
								//{
									if(graella[i][j].pressed((int)event.getX(), (int)event.getY())){
										graella[i][j].switchType(true);
										toques++;
										if(mode==0) ((PuzzleScreen)(this.getContext())).setTouches(((Integer)toques).toString());
										else ((SurvivalScreen)(this.getContext())).setTouches(((Integer)toques).toString());
									}
									else graella[i][j].unselect(true);
								//}
							}
						}
						
						locked=false;
					}
					break;
			}
			invalidate();
			
		}
		return true;
	}
	
	
	@Override
	public synchronized void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		if(!initialized)
		{
			int tmargin=0;
			for(int i=0; i<F; i++)
			{
				if(i==0) tmargin=frontalMargin(getHeight());
				else tmargin+=10+GraphicsManager.imageHeight();
				int lmargin=0;
				for(int j=0; j<C; j++){
					if(j==0) lmargin=lateralMargin(getWidth());
					else lmargin+=10+GraphicsManager.imageWidth();
					graella[i][j].setPos(lmargin, tmargin);
				}
			}
			initialized=true;
		}
		
		if(initialized){
			
			for(int i=0; i<F; i++)
			{
				for(int j=0; j<C; j++)
				{
					graella[i][j].draw(canvas, p,fontSize);
				}
			}
			
			if(!isPaused()) Update(canvas);
		}
		
		/*if(isFinished()){
			canvas.drawColor(Color.CYAN);
		}*/
	}
	
	public void Update(Canvas canvas)
	{
		int i=0;
		boolean b=true;
		while(i<(F*C) && b)
		{
			b=b && ( (graella[i/C][i%C].getType()==0 && graella[i/C][i%C].getStatus().equals(Cell.Status.ACTIVATED)) 
					|| (graella[i/C][i%C].getType()==1 && graella[i/C][i%C].isDeactivated()) );
			i++;
		}
		if(b){
			setStatus(Status.FINISHED) ;
			if(mode==0) ((PuzzleScreen)(this.getContext())).showEndDialog();
			else ((SurvivalScreen)(this.getContext())).puzzleDone(toques);
			
		}
		else{
			if(mode!=0) ((SurvivalScreen)(this.getContext())).setTime();
			invalidate();
		}
	}
	
}
