package com.radioactivewasp.bithacker.free;

import com.radioactivewasp.bithacker.free.R;
import com.radioactivewasp.bithacker.free.game.GraphicsManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class Splash extends Activity {
	
	RWView lienzo;
	Temporizador crono;
	boolean finalizado=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//setContentView(R.layout.splash_screen);
		
		super.onCreate(savedInstanceState);
		
		LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
         
        // Defining the LinearLayout layout parameters to fill the parent.
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);

        Bitmap img=((BitmapDrawable)getResources().getDrawable(R.drawable.rwlogo)).getBitmap();
        
        lienzo=new RWView(this,img);
        lienzo.setBackgroundColor(Color.BLACK);
        lienzo.setLayoutParams(llp);
        
        linearLayout.addView(lienzo);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        setContentView(linearLayout,llp);

		crono=new Temporizador(5000,1000);
		crono.start();
		
	}
	
	public void finalizar()
	{
		Intent i = new Intent(this, BitHacker.class);
		startActivity(i);
		finish();
	}
	
	public class RWView extends View{

		Context c;
		Bitmap src,img;
		Paint paint;
		
		public RWView(Context context, Bitmap b) {
			super(context);
			c=context;
			src=b;
			paint=new Paint();
		}
		
		@Override
		public void onDraw(Canvas canvas)
		{
			canvas.save();
			//Bitmap img = c.getResources().getDrawable(R.drawable.rwlogo).setBounds(40, 40, 480,800);
			//c.getResources().getDrawable(R.drawable.rwlogo).draw(canvas);
			//canvas.drawBitmap(img, 0, 0, paint);
			img= Bitmap.createScaledBitmap(src, canvas.getWidth(), canvas.getHeight(), false);
			//canvas.drawBitmap(img, new Rect(0,0,480,800), dst, paint);
			canvas.drawBitmap(img, 0, 0, paint);
			canvas.restore();
		}


		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			
			if(!finalizado){
				crono.cancel();
				finalizar();
				finalizado=true;
			}
			return true;
		}
		
	}
	
	public class Temporizador extends CountDownTimer{

		public Temporizador(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			finalizar();
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		}
	
	}
	
}
