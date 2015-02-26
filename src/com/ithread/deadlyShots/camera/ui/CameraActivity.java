package com.ithread.deadlyShots.camera.ui;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;



import com.ithread.deadlyShots.camera.callback.CameraCallback;
import com.ithread.deadlyShots.camera.preview.CameraSurface;
import com.ithread.deadlyShots.R;

public class CameraActivity extends Activity implements CameraCallback {
	private FrameLayout cameraholder = null;
	private CameraSurface camerasurface = null;
	
	private int currentCountChoice = 0;
	static boolean onPauseStatus = false;
	int arraySetting[] = { 7, 15, 30 };
	static boolean IsPictureCaptured = false;
	static int count = 1;
	int snapcount = 7;
	static boolean statusOnGridView = false;
	int defTimeOut = 0;
	private static final int DELAY = 45000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

	
		defTimeOut = Settings.System.getInt(getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, DELAY);
		Settings.System.putInt(getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, DELAY);

		cameraholder = (FrameLayout) findViewById(R.id.camera_preview);

		setupPictureMode();

		((ImageButton) findViewById(R.id.takepicture))
				.setOnClickListener(onButtonClick);
		((ImageButton) findViewById(R.id.about))
				.setOnClickListener(onButtonClick);
		((ImageButton) findViewById(R.id.gallary))
				.setOnClickListener(onButtonClick);
		((ImageButton) findViewById(R.id.setting))
				.setOnClickListener(onButtonClick);
		((ImageButton) findViewById(R.id.coloreffect))
				.setOnClickListener(onButtonClick);
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (IsPictureCaptured) {
			camerasurface.surfaceDestroyed(camerasurface.getHolder());
		}
		if (!statusOnGridView) {
			finish();
			statusOnGridView = false;
		}
		IsPictureCaptured = false;

		// camerasurface.onPauseAct();
	}

	@Override
	protected void onResume() {

		super.onResume();
		onPauseStatus = false;
		camerasurface.surfaceChanged(camerasurface.getHolder(),
				PixelFormat.JPEG, 240, 320);

	}

	private void setupPictureMode() {
		camerasurface = new CameraSurface(this);

		cameraholder.addView(camerasurface, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		camerasurface.setCallback(this);
	}

	@Override
	public void onJpegPictureTaken(byte[] data, Camera camera) {

		FileOutputStream outStream = null;
		try {
			outStream = CameraActivity.this.openFileOutput(
					String.format("sample%d.jpg", count), 0);

			outStream = new FileOutputStream(String.format(
					"/mnt/sdcard/BestPicImages/sample%d.jpg", count));

			outStream.write(data);
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		camerasurface.startPreview();

		if (count < snapcount) {
			count++;
			if (!(android.os.Environment.getExternalStorageState()
					.equals(android.os.Environment.MEDIA_MOUNTED))) // Chk for
																	// MMC card
			{
				IsPictureCaptured = false;
				count = 1;
				Toast.makeText(CameraActivity.this,
						"Please Insert MMC card to proceed... ",
						Toast.LENGTH_SHORT).show();
			} else {
				camerasurface.startTakePicture();
			}
		} else {
			IsPictureCaptured = false;
			count = 1;
		}

	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
	}

	@Override
	public void onRawPictureTaken(byte[] data, Camera camera) {
	}

	@Override
	public void onShutter() {
	}

	@Override
	public String onGetVideoFilename() {
		String filename = String.format("/sdcard/%d.3gp",
				System.currentTimeMillis());

		return filename;
	}

	// Rohit Added InitToTakePicture ()...
	private void launch_gridview() {

		Intent intent = new Intent(CameraActivity.this, BestPic_GridView.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(intent);

	}

	static public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	private void InitToTakePicture() {
		boolean Dir_result = false;

		File BestPicDir = new File("/mnt/sdcard/BestPicImages/");

		if (!(BestPicDir.exists())) {
			Dir_result = BestPicDir.mkdirs();
		} else if (BestPicDir.exists()) {
			Dir_result = deleteDirectory(BestPicDir);
			if (Dir_result) {
				BestPicDir.mkdirs();
			}
		}

		camerasurface.startTakePicture();

	}

	private void displayAboutDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.app_name));
		if (snapcount == 7)
			builder.setMessage("Insruction: BestPic App worked best on 2MP or more Camera resolution. Please set that in System Camera Setting. This app captures Seven frames in one single click and provides an option for user to choose the desired frame and save it to the BestPicImages folder.\n\n App will append BestPick_ with the filename and file will be saved. in SD Card\n\nUse Camera key to capture the image.");
		else if (snapcount == 15)
			builder.setMessage("Insruction: BestPic App worked best on 2MP or more Camera resolution. Please set that in System Camera Setting. This app captures Fifteen frames in one single click and provides an option for user to choose the desired frame and save it to the BestPicImages folder.\n\n App will append BestPick_ with the filename and file will be saved. in SD Card\n\nUse Camera key to capture the image.");
		else if (snapcount == 30)
			builder.setMessage("Insruction: BestPic App worked best on 2MP or more Camera resolution. Please set that in System Camera Setting. This app captures Thirty frames in one single click and provides an option for user to choose the desired frame and save it to the BestPicImages folder.\n\n App will append BestPick_ with the filename and file will be saved. in SD Card\n\nUse Camera key to capture the image.");

		builder.show();
	}

	private void displayColorEffectDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.color_effect));
		builder.setSingleChoiceItems(camerasurface.getSupportedColorEffects(),
				camerasurface.getCurrentColorEffect(),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						camerasurface.setColorEffect(which);

						dialog.dismiss();
					}
				});

		builder.show();
	}

	private void displaySnapCountDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String snapcount[] = { "7 shots", "15 shots", "30 shots" };

		builder.setTitle(getString(R.string.snap_shots));
		builder.setSingleChoiceItems(snapcount, currentCountChoice,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						currentCountChoice = which;

						setSnapCount(currentCountChoice);

						dialog.dismiss();
					}
				});

		builder.show();
	}

	private void setSnapCount(int snapCountChoice) {

		switch (snapCountChoice) {
		case 0:
			snapcount = 7;
			break;
		case 1:
			snapcount = 15;
			break;
		case 2:
			snapcount = 30;
			break;
		default:
			snapcount = 7;
			break;
		}

	}

	private View.OnClickListener onButtonClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.about:
				displayAboutDialog();
				break;

			case R.id.coloreffect:
				displayColorEffectDialog();
				break;

			case R.id.takepicture:
				if (!(android.os.Environment.getExternalStorageState()
						.equals(android.os.Environment.MEDIA_MOUNTED))) // Chk
																		// for
																		// MMC
																		// card
				{
					Toast.makeText(CameraActivity.this,
							"Please Insert MMC card to proceed... ",
							Toast.LENGTH_SHORT).show();
				} else if (!IsPictureCaptured) {
					IsPictureCaptured = true;
					InitToTakePicture();
				}
				break;
			case R.id.gallary:
				if (!IsPictureCaptured) {
					launch_gridview();
					statusOnGridView = true;
				}
				break;
			case R.id.setting:
				displaySnapCountDialog();
				break;
			}
		}
	};

	protected void onDestroy() {
		
		super.onDestroy();
		Settings.System.putInt(getContentResolver(),
				Settings.System.SCREEN_OFF_TIMEOUT, defTimeOut);
	}

}
