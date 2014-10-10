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

public class SurvivalMenu extends BHActivity {
	
	private Button[] botones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		setContentView(R.layout.survival_menu);
		
		botones=new Button[4];
		
		botones[0]=(Button)findViewById(R.id.Survival0);
		botones[1]=(Button)findViewById(R.id.Survival1);
		botones[2]=(Button)findViewById(R.id.SurvivalBack);

		
		botones[0].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				PlaySurvival();	
			}
			
		});
		
		botones[1].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(isSignedIn()) requestLeaderboard();
				else {
					 showConnectDialog();
				}
			}
			
		});
		
		botones[2].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				goBack();
			}
			
		});

	}
	

	protected void goBack()
	{
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
	
	public void PlaySurvival()
	{
		Intent i = new Intent(this, SurvivalActivity.class);
		startActivity(i);
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
