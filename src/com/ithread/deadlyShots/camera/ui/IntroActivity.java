package com.ithread.deadlyShots.camera.ui;

import com.ithread.deadlyShots.R;

import android.app.Activity;
import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.apperhand.device.android.AndroidSDKProvider;

public class IntroActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidSDKProvider.initSDK(this);
		Toast.makeText(IntroActivity.this, "Tap to Start Camera ", Toast.LENGTH_SHORT).show();		
		if(CameraActivity.onPauseStatus)
		{	
			CameraActivity.onPauseStatus = false;
			startCameraActivity();
		}
		else
		{
		setContentView(R.layout.intro);
		
			 //The Application will start with Portrait View
		   	 try {
		   		camera_set_orientation();
				} catch (Throwable e) {
					e.printStackTrace();
				}
		   			    	
		((Button)findViewById(R.id.start_camera)).setOnClickListener(onButtonClick);
		}
	}
	
	private void startCameraActivity() {
		Intent intent = new Intent(IntroActivity.this,CameraActivity.class);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(intent);
		
		finish();
	}
	
    private void camera_set_orientation() throws Throwable{
    	switch (this.getResources().getConfiguration().orientation)
    	{
    	case Configuration.ORIENTATION_PORTRAIT:{
    	  // Do something here
    		  this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	    }break;
    	case Configuration.ORIENTATION_LANDSCAPE:{
    	  // Do something here
    		      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	    }break;
    	case Configuration.ORIENTATION_SQUARE:{
      	  // Do something here
		      this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }break;
    	default:
    	  throw new Exception("Unexpected orientation enumeration returned");
    	 
    	}
    }
    
	private View.OnClickListener onButtonClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
				case R.id.start_camera:
				{
					startCameraActivity();
					
					break;
				}
			}
		}
	};
/*
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{        
			finish();
			
			//moveTaskToBack(true);         

			Intent intent = new Intent(BestPic_GridView.this,IntroActivity.class);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			startActivity(intent);
			   
		}     
		//return super.onKeyDown(keyCode, event);
		return true;
	}	
*/	
}
