package com.radioactivewasp.bithacker.free;

import com.google.example.games.basegameutils.BaseGameActivity;
import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.io.StatusRepository;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PlayScreen extends BHActivity {
	
	private Button[] botones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		setContentView(R.layout.play_screen);
		
		botones=new Button[4];
		
		botones[0]=(Button)findViewById(R.id.Play0);
		botones[1]=(Button)findViewById(R.id.Play1);
		botones[2]=(Button)findViewById(R.id.Play2);
		botones[3]=(Button)findViewById(R.id.PlayBack);

		
		botones[0].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				playPuzzle();		
			}
			
		});
		
		botones[1].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				playSurvival();
			}
			
		});
		
		botones[2].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(isSignedIn()) requestAchievement();
				else {
					 showConnectDialog();
				}
			}
			
		});
		
		
		botones[3].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goMain();
			}
			
		});
	}
	
	protected void playPuzzle()
	{
		Intent i = new Intent(this, PuzzleMenu.class);
		startActivity(i);
	}
	
	public void playSurvival() {
		Intent i = new Intent(this, SurvivalMenu.class);
		startActivity(i);
		
	}


	protected void goLogros()
	{
		Intent i = new Intent(this, LogrosMenu.class);
		startActivity(i);
	}
	
	protected void goMain()
	{
		finish();
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
	}
	
	
	
	protected void showConnectDialog()
	{
		final BHDialog dialog=new BHDialog(this,"You must be connected");
		dialog.addButton("OK", new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
			
		});
		
		dialog.show();
	}
	
}
