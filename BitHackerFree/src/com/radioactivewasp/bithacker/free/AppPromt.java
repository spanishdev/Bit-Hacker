package com.radioactivewasp.bithacker.free;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AppPromt {
	
	private static final String APP= "com.radioactivewasp.bithacker.free";
	
	private static final int DIAS= 4;
	private static final int EJECUCIONES= 7;
	
	public static void mostrarRate(Context context)
	{

		SharedPreferences prefs = context.getSharedPreferences("apprater", 0);
		if (!prefs.getBoolean("mostrar", true)) return ; 
		
		SharedPreferences.Editor editor = prefs.edit();
		
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);
        
        Long date_firstLaunch = prefs.getLong("firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("firstlaunch", date_firstLaunch);
        }
        
        if (launch_count >= EJECUCIONES) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DIAS * 24 * 60 * 60 * 1000)) {
            	rateDialog(context, editor);
            }
        }

        editor.commit();
	}
	
	public static void rateDialog(Context context, final SharedPreferences.Editor editor)
	{
		final BHDialog dialog=new BHDialog(context,R.string.rate);
		dialog.setLayoutOrientation(1);
		
		final Context ctx=context;
		
		dialog.addButton("OK",new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editor.putBoolean("mostrar", false);
				editor.commit();
				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+APP));
				ctx.startActivity(i);
				dialog.dismiss();
			}
			
		});
		
		dialog.addButton(R.string.rateLater,new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
			
		});
		
		dialog.addButton("No",new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editor.putBoolean("mostrar", false);
				editor.commit();
				dialog.dismiss();
			}
			
		});
		
		dialog.show();
		
	}


}
