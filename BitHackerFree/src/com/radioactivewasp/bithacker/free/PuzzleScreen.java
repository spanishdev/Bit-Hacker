package com.radioactivewasp.bithacker.free;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
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

public class PuzzleScreen extends BHActivity implements OnTouchListener, OnClickListener{

	private Puzzle puzzle;
	private Button back, pause, restart;
	private TextView toquestxt,timetxt;
	private int mode,puzlenum, opttouch;
	private Chronometer cronometro;
	PopupWindow bhpause;
	long time=0, opttime;
	
	//ADS
	private AdView adview;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		GraphicsManager.Initialize(this);
		setContentView(R.layout.puzzle_screen);
		     
        
		mode=getIntent().getIntExtra("Mode",0);
		puzlenum=getIntent().getIntExtra("PuzzleN",0);
		
		puzzle = (Puzzle) findViewById(R.id.puzlview);
		
		back=(Button) findViewById(R.id.GameBack);
		pause=(Button) findViewById(R.id.GamePause);
		restart=(Button) findViewById(R.id.GameRestart);
		
		back.setOnClickListener(this);
		pause.setOnClickListener(this);
		restart.setOnClickListener(this);
		
		timetxt=(TextView)findViewById(R.id.puzzleTime);
		cronometro=(Chronometer) findViewById(R.id.puzzleTime);
		
		LayoutInflater cview=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bhpause=new PopupWindow(cview.inflate(R.layout.pause_layout,null), LayoutParams.WRAP_CONTENT,  
                 LayoutParams.WRAP_CONTENT);
		bhpause.setBackgroundDrawable(new BitmapDrawable());
		bhpause.setOutsideTouchable(true);
		bhpause.setFocusable(true);
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
		if(mode==0)
		{
			int arrayId=getResources().getIdentifier("puzzle"+puzlenum, "array", getPackageName());
			String[] lineas=getResources().getStringArray(arrayId);
			
			int files=Integer.parseInt(lineas[0].split(" ")[0]);
			int columnes=Integer.parseInt(lineas[0].split(" ")[1]);
			
			opttouch=Integer.parseInt(lineas[1].split(" ")[0]);
			opttime=Long.parseLong(lineas[1].split(" ")[1]);
			
			puzzle.initialize(files,columnes,0);
			
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
	}
	
	public void restartPuzzle()
	{
		if(mode==0)
		{
			int arrayId=getResources().getIdentifier("puzzle"+puzlenum, "array", getPackageName());
			String[] lineas=getResources().getStringArray(arrayId);
			
			int files=Integer.parseInt(lineas[0].split(" ")[0]);
			int columnes=Integer.parseInt(lineas[0].split(" ")[1]);
			
			opttouch=Integer.parseInt(lineas[1].split(" ")[0]);
			opttime=Long.parseLong(lineas[1].split(" ")[1]);
			
			for(int j=0; j<files*columnes; j++)
			{
				String value=lineas[j+2].split(" ")[0];
				int type=Integer.parseInt(lineas[j+2].split(" ")[1]);
				puzzle.modifyCell(value, type, j);
				puzzle.restart();
				restartChrono();
			}
			
		}
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
						Exit_NoSave();
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
		toquestxt=(TextView) findViewById(R.id.puzzleTouches);
		toquestxt.setText(s);
	}
	
	
	/*public void setTime()
	{
		timetxt=(TextView) findViewById(R.id.puzzleTime);
		timetxt.setText(cronometro.getTimeElapsed());
	}*/
	
	public void setTime(String s)
	{
		timetxt=(TextView) findViewById(R.id.puzzleTime);
		timetxt.setText(s);
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		//crono.setRunning(false);
	}
	
	public void startChrono()
	{
		cronometro.setBase(SystemClock.elapsedRealtime()+time);
		cronometro.start();
	}
	
	public void stopChrono()
	{
		time=cronometro.getBase()-SystemClock.elapsedRealtime();
		cronometro.stop();
	}
	
	public void restartChrono()
	{
		time=0;
		cronometro.setBase(SystemClock.elapsedRealtime()+time);
		cronometro.start();
	}
	
	@Override
	public void onBackPressed()
	{
		if(bhpause.isShowing())
		{
			bhpause.dismiss();
		}
		else{
			if(!puzzle.isFinished()) Exit_NoSave();
			else Exit_Save(false);
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
	
	public long getChronoTime()
	{
		return SystemClock.elapsedRealtime() - cronometro.getBase();
	}
	
	public String getChronoString()
	{
		long elapsed=getChronoTime();
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
	
	public void showEndDialog()
	{
		
		if(mode==0)
		{
			StatusRepository.AddSave(puzlenum, getChronoTime(),(Integer)puzzle.getToques(), opttime, opttouch);
			StatusRepository.SaveSaveGames(this);
			StatusRepository.checkLogros(this);
		}
		
		String s=getChronoString();
		stopChrono();
		View view = getLayoutInflater().inflate( R.layout.finish_dialog_layout,null);
		
		final Dialog dialog = new Dialog( this );
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
	    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    
		dialog.setContentView(view);
		dialog.getWindow().setAttributes(lp);
		
		TextView finaltouches=(TextView)dialog.findViewById(R.id.finishTouches);
		finaltouches.setText(((Integer)puzzle.getToques()).toString());
		
		TextView finaltime=(TextView)dialog.findViewById(R.id.finishTime);
		finaltime.setText(s);
		
		Button fback = (Button)dialog.findViewById(R.id.finishBack);
		fback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				Exit_Save(false);
			}
			
		});
		
		Button frestart = (Button)dialog.findViewById(R.id.finishRestart);
		frestart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				restartPuzzle();
			}
			
		});
		
		Button fforward = (Button)dialog.findViewById(R.id.finishForward);
		fforward.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				Exit_Save(true);
				//goNext();
			}
			
		});
		
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
		    @Override
			public void onCancel(DialogInterface dialog)
		    {
		    	dialog.dismiss();
		        Exit_Save(false);
		    }
		});

		dialog.show();  
	}
	
	/**
	 * EXIT
	 */
	
	protected void Exit_NoSave()
	{
		finish();
	}
	
	protected void Exit_Save(boolean forward)
	{
		Intent i=new Intent();
		i.putExtra("next", forward);
		i.putExtra("np", puzlenum);
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



}
