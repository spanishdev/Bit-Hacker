package com.radioactivewasp.bithacker.free;

import java.util.ArrayList;
import java.util.Random;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;
import com.radioactivewasp.bithacker.free.R;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SurvivalActivity extends BHActivity {
	
	protected int waves,toques,score, num;
	protected int[] survivalpuzles;
	protected Random random;
	protected long tiempo;
	InterstitialAd interstitial;
	AdRequest request;
	
	private static int TIEMPO_TOTAL=600000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		LinearLayout linearLayout = new LinearLayout(this);
        
        // Setting the orientation to vertical
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLACK);
	    
        setContentView(linearLayout);
	    
	    tiempo=TIEMPO_TOTAL;
	    
	    random=new Random();
	    
	    waves=0;
	    toques=0;
	    score=0;
	    
	    survivalpuzles=new int[70];
	    
	    num=0;
	    
	    createPuzles();
	    
	    interstitial=new InterstitialAd(this, "a1519e13fe8be7a");
		request=new AdRequest();
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice("7D266BF81DC172DA9CE2A0F2C185A174");
        
		interstitial.setAdListener(new AdListener(){

			@Override
			public void onDismissScreen(Ad arg0) {
				// TODO Auto-generated method stub
			    
			}

			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
				interstitial.show();
			}
			
		});
		
		interstitial.loadAd(request);
		
		nextPuzle();

	}
	
	protected void createPuzles(){
		
		//String check="";
		
		int i=0;

		int min,max;
		min=0;
		max=(num/10)*100;
		while(i<70)
		{
			min=(num/10)*100;
			if(i<50) min+=28;
			
			if(i<40) max=min+100;
			else if(i<50) max=min+28;
			else max=min+100;
						
			int x=random.nextInt(max-min)+min;
			
			if(!contains(x))
			{
				survivalpuzles[num]=x;
				num++;
				i++;
				//check+=x+",";
			}
		}
		
		//System.out.println(check);
	}
	
	protected boolean contains(int x)
	{
		boolean b=false;
		for(int i=0; i<survivalpuzles.length; i++)
		{
			if(survivalpuzles[i]==x){
				b=true;
				break;
			}
		}
		
		return b;
	}
	
	protected void nextPuzle()
	{
		if(waves<70)
		{
			int next=survivalpuzles[waves];
			Intent i = new Intent(this, SurvivalScreen.class);
			Bundle b = new Bundle();
			b.putLong("Tiempo", tiempo);
			b.putInt("PuzzleN", next);
			b.putInt("Ronda", waves);
			i.putExtras(b);
			//startActivity(i);
			startActivityForResult(i, 5678);
		}
		else
		{
			int min,max;
			min=400;
			max=627;
			int next=random.nextInt(max-min)+min;
			Intent i = new Intent(this, SurvivalScreen.class);
			Bundle b = new Bundle();
			b.putLong("Tiempo", tiempo);
			b.putInt("PuzzleN", next);
			b.putInt("Ronda", waves);
			i.putExtras(b);
			startActivityForResult(i, 5678);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode==5678){
			if(resultCode==RESULT_OK)
			{
	
					if(data.getIntExtra("status", 0)==0){
						finish();
					}
					
					if(data.getIntExtra("status", 0)==1){

						waves++;
						toques+=data.getIntExtra("toques", 0);
						tiempo=data.getLongExtra("remaining", TIEMPO_TOTAL);
						score=waves*1000-toques;
						nextPuzle();
					}
					
					if(data.getIntExtra("status", 0)==2){
						showEndDialog();
					}
				}
			}
	}
	
	public void showEndDialog()
	{
		View view = getLayoutInflater().inflate( R.layout.survival_end,null);
		
		final Dialog dialog = new Dialog( this );
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
	    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    
		dialog.setContentView(view);
		dialog.getWindow().setAttributes(lp);
		
		TextView survivalrounds=(TextView)dialog.findViewById(R.id.survivalRounds);
		survivalrounds.setText(((Integer)waves).toString());
		
		TextView survivaltouches=(TextView)dialog.findViewById(R.id.survivalTouches);
		survivaltouches.setText(((Integer)toques).toString());
		
		TextView survivalscore=(TextView)dialog.findViewById(R.id.survivalScore);
		survivalscore.setText(((Integer)score).toString());
		
		
		Button fforward = (Button)dialog.findViewById(R.id.survivalForward);
		fforward.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				saveLeaderboard(score);
				finish();
			}
			
		});
		
		Button fback = (Button)dialog.findViewById(R.id.survivalBack);
		fback.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				finish();
			}
			
		});
		
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
		    @Override
			public void onCancel(DialogInterface dialog)
		    {
		    	dialog.dismiss();
		    	finish();
		    }
		});

		dialog.show();  
	}
	
}
