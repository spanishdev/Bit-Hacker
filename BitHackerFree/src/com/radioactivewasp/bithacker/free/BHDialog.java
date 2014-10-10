package com.radioactivewasp.bithacker.free;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BHDialog extends Dialog{
	
	/***
	 * Constructor of Bit Hacker dialog
	 * @param context Aplication context
	 * @param rmsg Message id from Resources
	 */
	
	public enum sizes { NORMAL, LARGE, XLARGE };
	
	public sizes tamano;
	
	private int orientation;
	
	public BHDialog(Context context, int rmsg)
	{
		super(context,R.style.BH_dialog_theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.bhdialog);
		
		orientation=LinearLayout.HORIZONTAL;
		
		try{
			if(context.getResources().getString(R.string.tamano).equals("large")) tamano=sizes.LARGE;
			else if(context.getResources().getString(R.string.tamano).equals("xlarge")) tamano=sizes.XLARGE;
			else tamano=sizes.NORMAL;
		}
		catch(Exception e){ tamano=sizes.NORMAL; }
		
		TextView text= (TextView) findViewById(R.id.dialogtext);
		text.setText(rmsg);
	}
	
	
	public BHDialog(Context context, int rmsg, int gravity)
	{
		super(context,R.style.BH_dialog_theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.bhdialog);
		orientation=LinearLayout.HORIZONTAL;
		try{
			if(context.getResources().getString(R.string.tamano).equals("large")) tamano=sizes.LARGE;
			else if(context.getResources().getString(R.string.tamano).equals("xlarge")) tamano=sizes.XLARGE;
			else tamano=sizes.NORMAL;
		}
		catch(Exception e){ tamano=sizes.NORMAL; }
		
		TextView text= (TextView) findViewById(R.id.dialogtext);
		text.setText(rmsg);
		text.setGravity(gravity);
	}
	
	
	public BHDialog(Context context, String string) {
		super(context,R.style.BH_dialog_theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.bhdialog);
		orientation=LinearLayout.HORIZONTAL;
		try{
			if(context.getResources().getString(R.string.tamano).equals("large")) tamano=sizes.LARGE;
			else if(context.getResources().getString(R.string.tamano).equals("xlarge")) tamano=sizes.XLARGE;
			else tamano=sizes.NORMAL;
		}
		catch(Exception e){ tamano=sizes.NORMAL; }
		
		
		TextView text= (TextView) findViewById(R.id.dialogtext);
		text.setText(string);
	}
	
	public BHDialog(Context context, String string, int gravity) {
		super(context,R.style.BH_dialog_theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.bhdialog);
		orientation=LinearLayout.HORIZONTAL;
		if(context.getResources().getString(R.string.tamano).equals("large")) tamano=sizes.LARGE;
		else if(context.getResources().getString(R.string.tamano).equals("xlarge")) tamano=sizes.XLARGE;
		else tamano=sizes.NORMAL;
		
		
		TextView text= (TextView) findViewById(R.id.dialogtext);
		text.setText(string);
		text.setGravity(gravity);
	}
	
	public void setLayoutOrientation(int i)
	{
		if(i==0) orientation=LinearLayout.HORIZONTAL;
		else  orientation=LinearLayout.VERTICAL;
	}

	public void addButton(int tid, Button.OnClickListener listener)
	{
		LinearLayout ll=(LinearLayout)findViewById(R.id.dialogblayout);
		ll.setOrientation(orientation);
		Button b=new Button(getContext());
		b.setText(tid);
		
		if(tamano.equals(sizes.LARGE)) b.setTextSize(R.dimen.button_large);
		else if(tamano.equals(sizes.XLARGE)) b.setTextSize(R.dimen.button_xlarge);
		
		b.setBackgroundResource(R.drawable.menu_button);
		b.setOnClickListener(listener);
		
		int sz=50;
		
		if(tamano.equals(sizes.NORMAL)) b.setTextSize(20);
		if(tamano.equals(sizes.LARGE)){
			b.setTextSize(35);
			sz=80;
		}
		else if(tamano.equals(sizes.XLARGE)){
			b.setTextSize(60);
			sz=100;
		}
		
		if(orientation==0){
			float f = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sz, getContext().getResources().getDisplayMetrics());
			b.setWidth((int)f);
			b.setHeight((int)f);
		}
		else{
			LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
			p.bottomMargin=(int)px;
			b.setLayoutParams(p);
		}
		
		try {
		    XmlResourceParser parser = getContext().getResources().getXml(R.drawable.textcolor_button);
		    ColorStateList colors = ColorStateList.createFromXml(getContext().getResources(), parser);
		    b.setTextColor(colors);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.FILL_PARENT);
		Resources r = getContext().getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
		params.rightMargin=(int)px;
		ll.addView(b,params);
	}
	
	
	public void addButton(String txt, Button.OnClickListener listener)
	{
		LinearLayout ll=(LinearLayout)findViewById(R.id.dialogblayout);
		ll.setOrientation(orientation);
		Button b=new Button(getContext());
		b.setText(txt);
		b.setBackgroundResource(R.drawable.menu_button);
		
		int sz=50;
		
		if(tamano.equals(sizes.NORMAL)){
			b.setTextSize(20);
		}
		if(tamano.equals(sizes.LARGE)){
			b.setTextSize(35);
			sz=80;
		}
		else if(tamano.equals(sizes.XLARGE)){
			b.setTextSize(60);
			sz=100;
		}
		
		System.out.println("TAMAÑO "+tamano);
		
		if(orientation==0){
			float f = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sz, getContext().getResources().getDisplayMetrics());
			b.setWidth((int)f);
			b.setHeight((int)f);
		}
		else{
			LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
			p.bottomMargin=(int)px;
			b.setLayoutParams(p);
		}
		
		b.setOnClickListener(listener);
		
		try {
		    XmlResourceParser parser = getContext().getResources().getXml(R.drawable.textcolor_button);
		    ColorStateList colors = ColorStateList.createFromXml(getContext().getResources(), parser);
		    b.setTextColor(colors);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		Resources r = getContext().getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
		params.rightMargin=(int)px;
		ll.addView(b,params);
	}
	
	
}
