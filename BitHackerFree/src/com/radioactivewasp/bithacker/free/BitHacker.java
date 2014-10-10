package com.radioactivewasp.bithacker.free;


import com.radioactivewasp.bithacker.free.AppPromt;
import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.game.GraphicsManager;
import com.radioactivewasp.bithacker.free.io.StatusRepository;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.example.games.basegameutils.BaseGameActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BitHacker extends BHActivity {

	private Button[] botones;
	private AdView adView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		StatusRepository.Initialize();
		StatusRepository.LoadSaveGames(this);
		//StatusRepository.loadAchievements(this);
		StatusRepository.checkLogros(this);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		setContentView(R.layout.main_menu);
		
		botones=new Button[8];
		
		botones[0]=(Button)findViewById(R.id.Main0);
		botones[1]=(Button)findViewById(R.id.Main1);
		botones[2]=(Button)findViewById(R.id.Main2);
		botones[3]=(Button)findViewById(R.id.Main3);
		botones[4]=(Button)findViewById(R.id.fb);
		botones[5]=(Button)findViewById(R.id.twitter);
		botones[6]=(Button)findViewById(R.id.signin);
		botones[7]=(Button)findViewById(R.id.bbb);
		
		findViewById(R.id.signin).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 beginUserInitiatedSignIn();		
			}
			
		});
		
		findViewById(R.id.signout).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				signOut();
				setSignState();		
			}
			
		});
		
		botones[0].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				jugar();			
			}
			
		});
		
		botones[1].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				showHelp();
			}
			
		});
		
		botones[2].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goExtras();
			}
			
		});
		
		
		botones[3].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
				System.exit(0);
			}
			
		});
		
		botones[4].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goFacebook();			
			}
			
		});
		
		botones[5].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goTwitter();			
			}
			
		});
		
		botones[7].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.radioactivewasp.bithacker"));
				startActivity(i);		
			}
			
		});
		
		AppPromt.mostrarRate(this);
	}
	

	protected void jugar()
	{
		Intent i = new Intent(this, PlayScreen.class);
		startActivity(i);
	}
	
	protected void goExtras()
	{
		Intent i = new Intent(this, Extras.class);
		startActivity(i);
	}
	
	protected void showHelp()
	{
		Intent i = new Intent(this, Help.class);
		startActivity(i);
	}
	
	protected void goFacebook()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/RadioactiveWasp"));
		startActivity(i);
	}
	
	protected void goTwitter()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/Spanishdev"));
		startActivity(i);
	}


	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		setSignState();
	}


	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		setSignState();
	}
	
	public void setSignState()
	{
		 if(isSignedIn()) {
			 findViewById(R.id.signin).setVisibility(View.GONE);
			 findViewById(R.id.signout).setVisibility(View.VISIBLE);
	            //findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
	            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	        } else {
	        	findViewById(R.id.signin).setVisibility(View.VISIBLE);
	        	findViewById(R.id.signout).setVisibility(View.GONE);
	            //findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	            //findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);          
	        }  
	}
	
	
	
}
	
	
	

