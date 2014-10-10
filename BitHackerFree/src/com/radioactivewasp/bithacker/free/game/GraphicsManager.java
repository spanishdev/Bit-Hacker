package com.radioactivewasp.bithacker.free.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.radioactivewasp.bithacker.free.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class GraphicsManager {
	
	private static GraphicsManager INSTANCE=new GraphicsManager();
	public static Drawable[] imagenes;
	
	public static GraphicsManager getInstance()
	{
		return INSTANCE;
	}
	
	public static void Initialize(Context context)
	{
		getInstance().imagenes=new Drawable[]{
			context.getResources().getDrawable(R.drawable.bgreeen0), context.getResources().getDrawable(R.drawable.bgreeen1), context.getResources().getDrawable(R.drawable.bgreeen2),
			context.getResources().getDrawable(R.drawable.bred0), context.getResources().getDrawable(R.drawable.bred1),context.getResources().getDrawable(R.drawable.bred2) , 
			context.getResources().getDrawable(R.drawable.borange0), context.getResources().getDrawable(R.drawable.borange1) , context.getResources().getDrawable(R.drawable.borange2)  
			};
	}
	
	public static Drawable getImagen(int i)
	{
		return getInstance().imagenes[i];
	}
	
	public static void drawImage(Canvas canvas, int i, int posx, int posy, int anchura, int altura)
	{
		canvas.save();
		//GraphicsManager.getImagen(1).setBounds(20,20,70,70);
		GraphicsManager.getImagen(i).setBounds(posx, posy, posx+anchura, posy+altura);
		GraphicsManager.getImagen(1).draw(canvas);
		canvas.restore();
	}
	
	public static void drawImage(Canvas canvas, int i, int posx, int posy)
	{
		canvas.save();
		//GraphicsManager.getImagen(1).setBounds(20,20,70,70);
		getImagen(i).setBounds(posx, posy, posx+imageWidth(), posy+imageWidth());
		getImagen(i).draw(canvas);
		canvas.restore();
	}
	
	public static int imageWidth()
	{
		return getImagen(0).getIntrinsicWidth();
	}
	
	public static int imageHeight()
	{
		return getImagen(0).getIntrinsicHeight();
	}
	
	
}
