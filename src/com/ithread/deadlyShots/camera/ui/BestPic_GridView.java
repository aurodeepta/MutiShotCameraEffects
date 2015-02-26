package com.ithread.deadlyShots.camera.ui;


import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.ithread.deadlyShots.R;

public class BestPic_GridView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);    
        gridview.setAdapter(new ImageAdapter(this));    
        gridview.setOnItemClickListener(new OnItemClickListener() {        
        	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {     
        		File SelectedBestPicDir = new File(String.format("/mnt/sdcard/BestPicImages/sample%d.jpg",(position+1)));
       		 
        		File SaveBestPicDir = new File(String.format("/mnt/sdcard/Bestpic_%d.jpg",System.currentTimeMillis())); 
        				SelectedBestPicDir.renameTo(SaveBestPicDir);
        		Toast.makeText(BestPic_GridView.this, "Selected Picture Index "+ (position+1) + " Saved !!! " , Toast.LENGTH_SHORT).show();        
        		}    
        	});
		
		
	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{     
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{         
			//moveTaskToBack(true);         
			Intent intent = new Intent(BestPic_GridView.this,CameraActivity.class);
			
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			startActivity(intent);   
		}     
		com.ithread.deadlyShots.camera.ui.CameraActivity.statusOnGridView = false;
		//return super.onKeyDown(keyCode, event);
		return true;
	}
	 

}