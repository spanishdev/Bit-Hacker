package com.radioactivewasp.bithacker.free;

import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.io.StatusRepository;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LogrosMenu extends Activity {

		private LinearLayout menulayout;
		private ViewGroup[] logros;
		private Button back;
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			setContentView(R.layout.achievements_menu);
			
			logros=new ViewGroup[12];
			
			refreshMenu();
			
		}
		
		public void refreshMenu()
		{
			menulayout=(LinearLayout)findViewById(R.id.achievs);
			
			for(int i=0; i<menulayout.getChildCount(); i++)
			{
				if(StatusRepository.achievements[i]){
					logros[i]=(ViewGroup)menulayout.getChildAt(i);
					
					logros[i].setBackgroundResource(R.drawable.button1bg);
					
					ImageView v=(ImageView)logros[i].getChildAt(0);
					
					changeImage(v,i);
					
					LinearLayout l=(LinearLayout)logros[i].getChildAt(1);
					
					((TextView)l.getChildAt(0)).setTextColor(Color.WHITE);
					((TextView)l.getChildAt(1)).setTextColor(Color.WHITE);
				}
				
			}
			
			back=(Button)findViewById(R.id.AchievBack);
			back.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0) {
					finish();
				}
				
			});
		}
		
		
		public void changeImage(ImageView v, int i)
		{
			switch(i)
			{
				case 0:
					v.setImageResource(R.drawable.l1b);
					break;
					
				case 1:
					v.setImageResource(R.drawable.l2b);
					break;
					
				case 2:
					v.setImageResource(R.drawable.l3b);
					break;
					
				case 3:
					v.setImageResource(R.drawable.l4b);
					break;
					
				case 4:
					v.setImageResource(R.drawable.l5b);
					break;
					
				case 5:
					v.setImageResource(R.drawable.l6b);
					break;
					
				case 6:
					v.setImageResource(R.drawable.l7b);
					break;
					
				case 7:
					v.setImageResource(R.drawable.l8b);
					break;
					
				case 8:
					v.setImageResource(R.drawable.l9b);
					break;
					
				case 9:
					v.setImageResource(R.drawable.l10b);
					break;
					
				case 10:
					v.setImageResource(R.drawable.l11b);
					break;
					
				case 11:
					v.setImageResource(R.drawable.l12b);
					break;
			}
			
		}
		
	}

