package com.radioactivewasp.bithacker.free;

import android.os.Bundle;
import android.view.Window;

import com.google.example.games.basegameutils.BaseGameActivity;
import com.radioactivewasp.bithacker.free.R;

public class BHActivity extends BaseGameActivity{

	public static int RQNUMBER = 1040;
	public static int LDNUMBER = 1050;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}
	
	public void unlockAchievement(int id)
	{
		 if(isSignedIn()) getGamesClient().unlockAchievement(getResources().getString(id));
	}
	
	public void requestAchievement()
	{
		if (isSignedIn()) {
            startActivityForResult(getGamesClient().getAchievementsIntent(),
              RQNUMBER);
          }
	}
	
	
	
	public void requestLeaderboard()
	{
		if (isSignedIn()) {
            startActivityForResult(getGamesClient().getLeaderboardIntent(
                    getResources().getString(R.string.leaderboard_survival)),
              LDNUMBER);
          }
	}
	
	public boolean saveLeaderboard(int score)
	{
		if (isSignedIn() && score>=0) {
            getGamesClient().submitScore(
                    getResources().getString(R.string.leaderboard_survival), score);
            return true;
          }
		else return false;
	}
	
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
