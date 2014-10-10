package com.radioactivewasp.bithacker.free;


import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.radioactivewasp.bithacker.free.BHDialog;
import com.radioactivewasp.bithacker.free.Help;
import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.io.StatusRepository;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PuzzleMenu extends Activity {

		private LinearLayout menulayout;
		private ViewGroup[] botones;
		private Button back;
		private static int PUZLES=120;
		int modulo=-1;
		InterstitialAd interstitial;
		AdRequest request;
		boolean canshow=false;
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			setContentView(R.layout.puzzle_menu);
			
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
					if(canshow) interstitial.show();
				}
				
			});
			
			botones=new ViewGroup[PUZLES];
			
			refreshMenu();
			
		}

		
		
		public void showNotAvailableDialog()
		{
			final BHDialog dialog=new BHDialog(this,R.string.notavailable);
			dialog.addButton("OK", new OnClickListener(){

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
				
			});
			
			dialog.show();
		}
		
		public void showBuyDialog()
		{
			final BHDialog dialog=new BHDialog(this,R.string.buymsg);
			dialog.addButton(R.string.buy, new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.radioactivewasp.bithacker"));
					startActivity(i);
					dialog.dismiss();
				}
				
			});
			
			dialog.addButton("No", new OnClickListener(){

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
				
			});
			
			dialog.show();
		}
		
		public void refreshMenu()
		{
			menulayout=(LinearLayout)findViewById(R.id.puzzles_scroll);
			
			System.out.println(StatusRepository.nsaves);
			
			for(int i=0; i<menulayout.getChildCount(); i++)
			{
				botones[i]=(ViewGroup)menulayout.getChildAt(i);
				final int level=i;
				
				if(StatusRepository.nsaves>i && i<60)
				{
					TextView v=(TextView) botones[i].getChildAt(1);
					long time=StatusRepository.saves[i].time;

					long s,m,h;
					s=time/1000;
					m=(s/60);
					h=m/60;
					s=s%60;
					m=m%60;
					
					String str="";
					if(h>0) str+=h+":";
					str+=String.format("%02d", m)+":";
					str+=String.format("%02d", s);
					v.setText(str);
					
					int touches=StatusRepository.saves[i].touches;
					TextView v2=(TextView) botones[i].getChildAt(2);
					v2.setText(((Integer)touches).toString());
					
					Button b=(Button) botones[i].getChildAt(0);
					
					if(StatusRepository.saves[i].score<7000){
						try {
						    XmlResourceParser parser = getResources().getXml(R.drawable.textcolor_button_ls);
						    ColorStateList colors = ColorStateList.createFromXml(getResources(), parser);
						    b.setTextColor(colors);
						} catch (Exception e) {
						    e.printStackTrace();
						}
						
						b.setBackgroundResource(R.drawable.menu_button_orange);
						v.setTextColor(Color.YELLOW);
						v2.setTextColor(Color.YELLOW);
						
					}
					else
					{
						try {
						    XmlResourceParser parser = getResources().getXml(R.drawable.textcolor_button_hs);
						    ColorStateList colors = ColorStateList.createFromXml(getResources(), parser);
						    b.setTextColor(colors);
						} catch (Exception e) {
						    e.printStackTrace();
						}
						
						b.setBackgroundResource(R.drawable.menu_button_blue);
						v.setTextColor(Color.CYAN);
						v2.setTextColor(Color.CYAN);
					}
					
					b.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							showObjDialog(level);
						}
						
					});
					
				}
				else
				{
					Button b=(Button) botones[i].getChildAt(0);
					if(i>StatusRepository.nsaves || i>=60){
						final int index=i;
						try {
						    XmlResourceParser parser = getResources().getXml(R.drawable.textcolor_buttonr);
						    ColorStateList colors = ColorStateList.createFromXml(getResources(), parser);
						    b.setTextColor(colors);
						} catch (Exception e) {
						    e.printStackTrace();
						}
						b.setBackgroundResource(R.drawable.menu_button_red);
						TextView v=(TextView) botones[i].getChildAt(1);
						v.setTextColor(Color.RED);
						v=(TextView) botones[i].getChildAt(2);
						v.setTextColor(Color.RED);
						b.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								if(index<60) showNotAvailableDialog();
								else showBuyDialog();
							}
							
						});
					}
					else{
						try {
						    XmlResourceParser parser = getResources().getXml(R.drawable.textcolor_button);
						    ColorStateList colors = ColorStateList.createFromXml(getResources(), parser);
						    b.setTextColor(colors);
						} catch (Exception e) {
						    e.printStackTrace();
						}
						b.setBackgroundResource(R.drawable.menu_button);
						TextView v=(TextView) botones[i].getChildAt(1);
						v.setTextColor(Color.GREEN);
						v=(TextView) botones[i].getChildAt(2);
						v.setTextColor(Color.GREEN);
						b.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								if(level==0 && StatusRepository.nsaves==0)
								{
									showHelpDialog(level);
								}
								else showObjDialog(level);
							}
							
						});
					}
				}
			}
			
			back=(Button)findViewById(R.id.PuzzleBack);
			back.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View arg0) {
					finish();
				}
				
			});
		}
		
		
		
		public void LoadLevel(int l)
		{
			if(modulo==-1) modulo=l%5;
			
			if(l%5==modulo){
		        interstitial.loadAd(request);
		        canshow=true;
			}
			else{
				canshow=false;
			}
			carryOn(l);
			
		}
		
		public void carryOn(int l){
			Intent i = new Intent(this, PuzzleScreen.class);
			Bundle b = new Bundle();
			b.putInt("Mode", 0);
			b.putInt("PuzzleN", l);
			i.putExtras(b);
			//startActivity(i);
			startActivityForResult(i, 1234);
		}
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			if(resultCode==RESULT_OK && requestCode==1234)
			{
				refreshMenu();
				if(data.getBooleanExtra("next", false)==true) LoadLevel(data.getIntExtra("np", 0)+1);
			}
		}
		
		public void showObjDialog(int l)
		{
			final int lvl=l;
			
			int arrayId=getResources().getIdentifier("puzzle"+l, "array", getPackageName());
			String[] lineas=getResources().getStringArray(arrayId);
			
			String opttouch=lineas[1].split(" ")[0];
			long opttime=Long.parseLong(lineas[1].split(" ")[1]);
			
			View view = getLayoutInflater().inflate( R.layout.objective_layout,null);
			
			final Dialog dialog = new Dialog( this );
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		    lp.copyFrom(dialog.getWindow().getAttributes());
		    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		    
			dialog.setContentView(view);
			dialog.getWindow().setAttributes(lp);
			
			TextView finaltouches=(TextView)dialog.findViewById(R.id.finishTouches);
			finaltouches.setText(opttouch);
			
			TextView finaltime=(TextView)dialog.findViewById(R.id.finishTime);
			finaltime.setText(timeToString(opttime));
			
			Button fforward = (Button)dialog.findViewById(R.id.finishForward);
			fforward.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();
					LoadLevel(lvl);
					//goNext();
				}
				
			});
			
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
			    @Override
				public void onCancel(DialogInterface dialog)
			    {
			    	dialog.dismiss();
			    	LoadLevel(lvl);
			    }
			});

			dialog.show();  
		}
		
		public String timeToString(long elapsed)
		{
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
		
		public void showHelpDialog(final int l){
			
			final BHDialog dialog=new BHDialog(this,R.string.showhelp);
			dialog.addButton("OK", new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					showHelp();
				}
				
			});
			
			dialog.addButton("No", new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					showObjDialog(l);
				}
				
			});
			
			dialog.show();
			
		}
		
		public void showHelp()
		{
			Intent i = new Intent(this, Help.class);
			startActivity(i);
		}
		
		
	}

