package com.radioactivewasp.bithacker.free;

import com.radioactivewasp.bithacker.free.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class Extras extends Activity {
	
	private Button[] botones;
	private PopupWindow credits;
	//private TextView creditsView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		setContentView(R.layout.extras_screen);

		//creditsView=new TextView(this);
		//creditsView.setText("@string/credits");
		
		//credits=new PopupWindow(this);
		//credits.setContentView(creditsView);
		
		LayoutInflater cview=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		credits=new PopupWindow(cview.inflate(R.layout.credits_layout,null), LayoutParams.WRAP_CONTENT,  
                 LayoutParams.WRAP_CONTENT);
		credits.setBackgroundDrawable(new BitmapDrawable());
		credits.setOutsideTouchable(true);
		credits.setFocusable(true);
		
		final LinearLayout layout=(LinearLayout) findViewById(R.id.ExtrasRoot);
		
		botones=new Button[3];
		
		botones[0]=(Button)findViewById(R.id.Extras0);
		botones[1]=(Button)findViewById(R.id.Extras1);
		botones[2]=(Button)findViewById(R.id.ExtrasBack);
		
		botones[0].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				credits.showAtLocation(botones[1], Gravity.CENTER, 0, 0);
				
			}
			
		});
		
		botones[1].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				moreGames();
			}
			
		});
		
		botones[2].setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(credits!=null)
				{
					if(credits.isShowing())
					{
						credits.dismiss();
					}
				}
				finish();		
			}
			
		});
	}
	
	protected void moreGames()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/search?q=radioactive+wasp"));
		startActivity(i);
	}
	
	@Override
	public void onBackPressed()
	{
		if(credits!=null)
		{
			if(credits.isShowing())
			{
				credits.dismiss();
			}
			else finish();
		}
		else finish();
	}

}
