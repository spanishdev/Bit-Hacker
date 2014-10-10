package com.radioactivewasp.bithacker.free;

import com.google.example.games.basegameutils.BaseGameActivity;
import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.game.GraphicsManager;
import com.radioactivewasp.bithacker.free.game.Puzzle;
import com.radioactivewasp.bithacker.free.io.StatusRepository;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class SurvivalScreen extends BHActivity implements OnTouchListener, OnClickListener{

	private Puzzle puzzle;
	private Button back, pause, restart;
	private TextView toquestxt,timetxt, rondatxt;
	private int puzlenum,ronda;
	private Cronometro cronometro;
	PopupWindow bhpause;
	long restante,transcurrido;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		GraphicsManager.Initialize(this);
		setContentView(R.layout.survival_screen);
		
		puzlenum=getIntent().getIntExtra("PuzzleN",0);
		
		restante=getIntent().getLongExtra("Tiempo", 0);
		
		ronda=getIntent().getIntExtra("Ronda", 0);
		
		transcurrido=0;
		
		puzzle = (Puzzle) findViewById(R.id.puzlview);
		
		back=(Button) findViewById(R.id.GameBack);
		pause=(Button) findViewById(R.id.GamePause);
		restart=(Button) findViewById(R.id.GameRestart);
		
		back.setOnClickListener(this);
		pause.setOnClickListener(this);
		restart.setOnClickListener(this);
		
		LayoutInflater cview=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bhpause=new PopupWindow(cview.inflate(R.layout.pause_layout,null), LayoutParams.WRAP_CONTENT,  
                 LayoutParams.WRAP_CONTENT);
		bhpause.setBackgroundDrawable(new BitmapDrawable());
		bhpause.setOutsideTouchable(true);
		bhpause.setFocusable(true);
		
		rondatxt=(TextView) findViewById(R.id.TextRound);
		rondatxt.setText(((Integer)ronda).toString());
		//rondatxt.setText(puzlenum);
		
		bhpause.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss() {
				puzzle.switchPaused();
				startChrono();
			}
			
		});
		
		
		buildPuzzle();
		
		puzzle.setOnTouchListener(this);
		
	}
	
	public void buildPuzzle()
	{
		
			int arrayId=getResources().getIdentifier("survival"+puzlenum, "array", getPackageName());
			String[] lineas=getResources().getStringArray(arrayId);
			
			int files=Integer.parseInt(lineas[0].split(" ")[0]);
			int columnes=Integer.parseInt(lineas[0].split(" ")[1]);
			
			puzzle.initialize(files,columnes,1);
			
			int i=-1;
			int px,py;
			px=py=0;
			for(int j=0; j<files*columnes; j++)
			{
				
				String value=lineas[j+2].split(" ")[0];
				//String value=((Integer)(j)).toString();
				int type=Integer.parseInt(lineas[j+2].split(" ")[1]);
				int nchild=Integer.parseInt(lineas[j+2].split(" ")[2]);
				int[] children=new int[nchild];
				for(int k=0; k<nchild; k++) children[k]=Integer.parseInt(lineas[j+2].split(" ")[k+3]);
				puzzle.addCell(value, type, children, GraphicsManager.imageWidth(), GraphicsManager.imageHeight());
			}
			
			startChrono();
			/*startChrono();
			cronometro.setRunning(true);
			runThread();*/
			//cronometro.run();
			
		
	}
	
	public void restartPuzzle()
	{
		
			int arrayId=getResources().getIdentifier("survival"+puzlenum, "array", getPackageName());
			String[] lineas=getResources().getStringArray(arrayId);
			
			int files=Integer.parseInt(lineas[0].split(" ")[0]);
			int columnes=Integer.parseInt(lineas[0].split(" ")[1]);
			
			for(int j=0; j<files*columnes; j++)
			{
				String value=lineas[j+2].split(" ")[0];
				int type=Integer.parseInt(lineas[j+2].split(" ")[1]);
				puzzle.modifyCell(value, type, j);
				puzzle.restart();
			}
			
			startChrono();
	}
	
	
	@Override
	public void onPause()
	{
		puzzle.switchPaused();
		if(puzzle.isPaused()){
			stopChrono();
			bhpause.showAtLocation(puzzle, Gravity.CENTER, 0, 0);
		}
		super.onPause();
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		
		return false;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.GameBack:
				stopChrono();
				final BHDialog dialog=new BHDialog(this, R.string.Backmsg);
				dialog.addButton("Ok", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Exit(0,0);
						dialog.dismiss();
					}
					
				});
				dialog.addButton("No", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						startChrono();
					}
					
				});
				
				dialog.show();
				break;
				
			case R.id.GamePause:
				
				puzzle.switchPaused();
				if(puzzle.isPaused()){
					stopChrono();
					bhpause.showAtLocation(puzzle, Gravity.CENTER, 0, 0);
				}
				else{

					startChrono();
				}
				break;
				
			case R.id.GameRestart:
				stopChrono();
				final BHDialog dialogR=new BHDialog(this, R.string.Restartmsg);
				dialogR.addButton("Ok", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						restartPuzzle();
						dialogR.dismiss();
					}
					
				});
				dialogR.addButton("No", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialogR.dismiss();
						startChrono();
					}
					
				});
				
				dialogR.show();
				break;
		}
	}
	
	
	public void setTouches(String s)
	{
		toquestxt=(TextView) findViewById(R.id.Touches);
		toquestxt.setText(s);
	}
	
	
	/*public void setTime()
	{
		timetxt=(TextView) findViewById(R.id.puzzleTime);
		timetxt.setText(cronometro.getTimeElapsed());
	}*/
	
	public void setTime()
	{
		timetxt=(TextView) findViewById(R.id.puzzleTime);
		timetxt.setText(getChronoString());
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		//crono.setRunning(false);
	}
	
	public void startChrono()
	{
		restante-=transcurrido;
		cronometro=new Cronometro(restante, 1000,transcurrido);
		cronometro.start();
	}
	
	public void stopChrono()
	{
		transcurrido=cronometro.trans;
		cronometro.cancel();
	}
	
	@Override
	public void onBackPressed()
	{
		if(bhpause.isShowing())
		{
			bhpause.dismiss();
		}
		else{
			if(!puzzle.isFinished()) Exit(0,0);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if (keyCode == KeyEvent.KEYCODE_MENU) {
        	puzzle.switchPaused();
        	if(puzzle.isPaused()){
				stopChrono();
				bhpause.showAtLocation(puzzle, Gravity.CENTER, 0, 0);
			}
			else{
				bhpause.dismiss();
			}
            return true;
        }
        return super.onKeyDown(keyCode, event); 
    } 
	
	public String getChronoString()
	{
		long elapsed=cronometro.getCountdown();
		long h,m,s;
		s=(elapsed/1000);
		m=(s/60);
		h=m/60;
		s=s%60;
		m=m%60;
		
		String str="";
		
		if(h>0) str+=h+":";
		str+=String.format("%02d", m)+":";
		str+=String.format("%02d", s);
		
		return str;
	}
	
	
	
	/**
	 * EXIT
	 */
	
	protected void Exit(int status, int toques)
	{
		stopChrono();
		Intent i=new Intent();
		i.putExtra("status", status);
		i.putExtra("np", puzlenum);
		i.putExtra("remaining",cronometro.getCountdown());
		i.putExtra("toques", toques);
		if(puzzle.isFinished())
		{
			if(ronda==5) unlockAchievement(R.string.achievement_10);
			if(ronda==10) unlockAchievement(R.string.achievement_11);
			if(ronda==20) unlockAchievement(R.string.achievement_12);
		}
		setResult(RESULT_OK, i);
		finish();
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void puzzleDone(int t)
	{
		stopChrono();
		Exit(1,t);
	}
	
	public class Cronometro extends CountDownTimer
	{
		
		public boolean finalizado;
		public long trans,total,remaining;
		
		public Cronometro(long millisInFuture, long countDownInterval, long _transcurrido) {
			super(millisInFuture, countDownInterval);
			trans=_transcurrido;
			total=millisInFuture;
			if(millisInFuture>0) finalizado=false;
			else finalizado=true;
			remaining=millisInFuture;
		}

		@Override
		public void onFinish() {
			finalizado=true;
			Exit(2,0);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			trans=total-millisUntilFinished;	
			remaining=millisUntilFinished;
		}
		
		public long getCountdown()
		{
			return remaining;
		}
		
	}



}
