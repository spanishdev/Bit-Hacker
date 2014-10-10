package com.radioactivewasp.bithacker.free.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.example.games.basegameutils.BaseGameActivity;
import com.radioactivewasp.bithacker.free.BHActivity;
import com.radioactivewasp.bithacker.free.BHDialog;
import com.radioactivewasp.bithacker.free.BitHacker;
import com.radioactivewasp.bithacker.free.R;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class StatusRepository {
	
	public static SaveGame[] saves;
	public static ArrayList<SurvivalScore> survival_scores;
	public static boolean[] achievements;
	private static StatusRepository INSTANCE=new StatusRepository();
	public static int nsaves;
	private static int PUZLES=120;
	
	public static StatusRepository getInstance()
	{
		return INSTANCE;
	}
	
	public static void Initialize()
	{
		nsaves=0;
		getInstance().saves = new SaveGame[PUZLES];
		getInstance().survival_scores=new ArrayList<SurvivalScore>();
		getInstance().achievements=new boolean[12];
	}
	
	public static void insertSave(int level, long time, int touches, long otime, int otouch, int score)
	{
		if(nsaves<PUZLES){
			getInstance().saves[level]=new SaveGame(time,touches,otime,otouch,score);
			nsaves++;
		}
	}
	
	public static void AddSave(int level, long time, int touches, long optimal_time, int optimal_touches)
	{
		if(level==nsaves){
			int score=0;
			
			if(touches<=optimal_touches) score+=2000;
			else if(touches<=(optimal_touches*2)) score+=500; 
			else score+=0; 
				
			if(time<=optimal_time) score+=5000;
			else if(time<=(optimal_time*2)) score+=1000;
			else score+=0;
			
			getInstance().saves[level]=new SaveGame(time,touches, optimal_time, optimal_touches,score);
			nsaves++;
			
			System.out.println(getInstance().nsaves);
		}
		else ChangeSave(level,time,touches, optimal_time, optimal_touches);
	}
	
	public static void ChangeSave(int level, long time, int touches, long optimal_time, int optimal_touches)
	{
		long tm;
		int tt,score;
		score=0;
		tt=getInstance().saves[level].touches;
		tm=getInstance().saves[level].time;
		if(touches<tt) tt=touches;
		if(time<tm) tm=time;
		
		if(tt<=optimal_touches) score+=2000;
		else if(tt<=(optimal_touches*2)) score+=500; 
		else score+=0; 
			
		if(tm<=optimal_time) score+=5000;
		else if(tm<=(optimal_time*2)) score+=1000;
		else score+=0;
		
		getInstance().saves[level]=new SaveGame(tm,tt,optimal_time,optimal_touches,score);
	}
	
	public static void InsertSurvivalScore(String name, long time, int touches, int score, int oleadas)
	{
		int posicion=99;
		boolean finished=false;
		int i=0;
		if(getInstance().survival_scores.size()>0)
		{
			while(!finished && i<getInstance().survival_scores.size())
			{
				if(getInstance().survival_scores.get(i).score<=score)
				{
					posicion=i;
					finished=true;
				}
				i++;
			}
		}
		else posicion=0;
		
		getInstance().survival_scores.add(posicion, new SurvivalScore(name,time,touches,score,oleadas));
		if(getInstance().survival_scores.size()>100) getInstance().survival_scores.remove(100);
		
		/*for(int i=0; !finished && i<nscores+1; i++)
		{
			if(posicion==99)
			{
				if(survival_scores.get(i).score<=score || survival_scores.get(i)==null)
				{
					posicion=i;
				}
			}
			else
			{
				if(i>0)
				{
					if(survival_scores[i-1]!=null)
					{
						survival_scores[i]=survival_scores[i-1];
					}
					else finished=true;
				}
				else finished=true;
			}
		}
		
		getInstance().survival_scores[posicion]=new SurvivalScore(name,time,touches,score,oleadas);
		nscores++;*/
	}
	
	public static void LoadSaveGames(Context context)
	{
		try{

			FileInputStream input=context.openFileInput("SaveGames.json");
			byte[] data=new byte[input.available()];
			input.read(data);
			input.close();
			String jstring = new String(data,"UTF8");
			
			JSONObject jsaves = new JSONObject(jstring);
			JSONArray array= jsaves.getJSONArray("Saves");
			
			for(int i=0; i<array.length(); i++)
			{
				JSONObject object=array.getJSONObject(i);
				insertSave(i,object.getLong("time"),object.getInt("touches"),object.getLong("otime"),object.getInt("otouches"),object.getInt("score"));
			}
			//System.out.println(array.toString());
			
		}
		catch (FileNotFoundException fne)
		{
			System.out.println("NO EXISTS");
			nsaves=0;
		}
		catch(JSONException je)
		{
			 je.printStackTrace();
			 final BHDialog dialog=new BHDialog(context,"Couldn't load the status");
				dialog.addButton("OK", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
					
				});
				
				dialog.show();
		}
		catch (IOException ioe)
		{
			 ioe.printStackTrace();
			 final BHDialog dialog=new BHDialog(context,"Couldn't load the status");
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
	
	
	public static void SaveSaveGames(Context context)
	{
		System.out.println("BEGIN");
		try
		{
			JSONObject saves = new JSONObject();
			JSONArray array=new JSONArray();
			
			for(int i=0; i<nsaves; i++)
			{
				JSONObject score=new JSONObject();
				score.put("score", getInstance().saves[i].score);
				score.put("time", getInstance().saves[i].time);
				score.put("touches", getInstance().saves[i].touches);
				score.put("otouches", getInstance().saves[i].opttouch);
				score.put("otime", getInstance().saves[i].opttime);
				array.put(score);
			}
			
			saves.put("Saves", array);
			
			System.out.println(saves.toString());
			
			FileOutputStream fos = context.openFileOutput("SaveGames.json", Context.MODE_PRIVATE);
			fos.write(saves.toString().getBytes());
			fos.flush();
			fos.close();
			
			File f=context.getFileStreamPath("SaveGames.json");
			if(f.exists()) System.out.println("YESSSS");
			else System.out.println("NOOOOO");
		}
		catch(JSONException je)
		{
			 final BHDialog dialog=new BHDialog(context,"Couldn't load the status");
				dialog.addButton("OK", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
					
				});
				
				dialog.show();
		}
		catch (IOException ioe)
		{
			 final BHDialog dialog=new BHDialog(context,"Couldn't load the status");
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
	
	public static void SaveSurvivalScores(Context context)
	{
		try
		{
			JSONObject scores = new JSONObject();
			JSONArray array=new JSONArray();
			
			for(int i=0; i<getInstance().survival_scores.size(); i++)
			{
				JSONObject score=new JSONObject();
				score.put("name", getInstance().survival_scores.get(i).name);
				score.put("score", getInstance().survival_scores.get(i).score);
				score.put("time", getInstance().survival_scores.get(i).time);
				score.put("touches", getInstance().survival_scores.get(i).touches);
				score.put("rounds", getInstance().survival_scores.get(i).rounds);
				array.put(score);
			}
			
			scores.put("Scores", array);
			
			System.out.println(scores.toString());
			
			FileOutputStream fos = context.openFileOutput("SurvivalScores.json", Context.MODE_PRIVATE);
			fos.write(scores.toString().getBytes());
			fos.close();
		}
		catch(JSONException je)
		{
			 final BHDialog dialog=new BHDialog(context,"There has been a problem saving the scores");
				dialog.addButton("OK", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
					
				});
				
				dialog.show();
		}
		catch (IOException ioe)
		{
			final BHDialog dialog=new BHDialog(context,"There has been a problem saving the scores");
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
	
	
	
	/*public static void saveAchievements(Context context)
	{
		try {
			FileOutputStream fos = context.openFileOutput("Achievements.save", Context.MODE_PRIVATE);
			String s="";
			for(int i=0; i<12; i++)
			{
				if(achievements[i]){
					if(i<11) s+="true,";
					else s+="true";
				}
				else{
					if(i<11) s+="false,";
					else s+="false";
				}
			}
			fos.write(s.getBytes());
			fos.close();
		} catch (IOException e) {
			final BHDialog dialog=new BHDialog(context,"There has been a problem saving the achievements");
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
	
	public static void loadAchievements(Context context)
	{
		try {
			FileInputStream input=context.openFileInput("Achievements.save");
			byte[] data=new byte[input.available()];
			input.read(data);
			input.close();
			String str = new String(data,"UTF8");
			
			for(int i=0; i<12; i++)
			{
				achievements[i]=(str.split(",")[i]).equals("true");
			}
			input.close();
			
		} 
		catch (FileNotFoundException fne)
		{
			for(int i=0; i<12; i++) achievements[i]=false;
		}
		catch (IOException e) {
			 final BHDialog dialog=new BHDialog(context,"Couldn't load the achievements");
				dialog.addButton("OK", new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
					
				});
				
				dialog.show();
		}
	}*/
	
	
	
	public static void checkLogros(BHActivity activity)
	{
		if(nsaves==PUZLES && !achievements[0]) achievements[0]=true;
		if(nsaves>0 && !achievements[2]) achievements[2]=true;
		boolean otm, otc, ott;
		
		if(nsaves>0) otm=otc=true;
		else otm=otc=false;
		
		for(int i=0; i<nsaves; i++)
		{
			if(!achievements[3] && saves[i].time<=saves[i].opttime) achievements[3]=true;
			if(!achievements[4] && saves[i].touches<=saves[i].opttouch) achievements[4]=true;
			if(!achievements[5] && saves[i].touches<=saves[i].opttouch && saves[i].time<=saves[i].opttime) achievements[5]=true;
			if(!achievements[6] && saves[i].time<3000) achievements[6]=true;
			
			if(nsaves==PUZLES){
				if(otm) otm=otm && saves[i].time<=saves[i].opttime;
				if(otc) otc=otc && saves[i].touches<=saves[i].opttouch;
			}
			else
			{
				otm=false;
				otc=false;
				ott=false;
			}
		}
		
		achievements[1]=otm && otc;
		achievements[7]=otm;
		achievements[8]=otc;
		
		//BitHacker game = (BitHacker)context;
		
		if(achievements[0]) activity.unlockAchievement(R.string.achievement_1);
		if(achievements[1]) activity.unlockAchievement(R.string.achievement_2);
		if(achievements[2]) activity.unlockAchievement(R.string.achievement_3);
		if(achievements[3]) activity.unlockAchievement(R.string.achievement_4);
		if(achievements[4]) activity.unlockAchievement(R.string.achievement_5);
		if(achievements[5]) activity.unlockAchievement(R.string.achievement_6);
		if(achievements[6]) activity.unlockAchievement(R.string.achievement_7);
		if(achievements[7]) activity.unlockAchievement(R.string.achievement_8);
		if(achievements[8]) activity.unlockAchievement(R.string.achievement_9);
		
		//saveAchievements(activity);
	}
	
}
