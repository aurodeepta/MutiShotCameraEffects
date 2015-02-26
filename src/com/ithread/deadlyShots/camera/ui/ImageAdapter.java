package com.ithread.deadlyShots.camera.ui;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public static final int THUMBNAIL_HEIGHT = 48;
	public static final int THUMBNAIL_WIDTH = 66;
	final int THUMBNAIL_SIZE = 64;

	public ImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {

		File path = new File("/mnt/sdcard/BestPicImages/");
		if (path.exists()) {
			File[] files = path.listFiles();
			if (files.length == 0)
				Toast.makeText(ImageAdapter.this.mContext,
						"No Picture to Selected", Toast.LENGTH_SHORT).show();
			return files.length;
		} else {
			Toast.makeText(ImageAdapter.this.mContext,
					"No Picture to Selected", Toast.LENGTH_SHORT).show();
			return 0;
		}
		// 9;//mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		// imageView.setImageResource(mThumbIds[position]);
		Bitmap bmp = BitmapFactory.decodeFile(String.format(
				"/mnt/sdcard/BestPicImages/sample%d.jpg", (position + 1)));

		if (bmp != null)
			bmp = Bitmap.createScaledBitmap(bmp, THUMBNAIL_SIZE,
					THUMBNAIL_SIZE, true);

		imageView.setImageBitmap(bmp);

		/*
		 * //// byte[] imageData = null; try { final int THUMBNAIL_SIZE = 64;
		 * FileInputStream fis = new
		 * FileInputStream(String.format("/mnt/sdcard/BestPicImages/sample%d.jpg"
		 * , (position+1))); Bitmap imageBitmap =
		 * BitmapFactory.decodeStream(fis); imageBitmap =
		 * Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_SIZE,
		 * THUMBNAIL_SIZE, false); // ByteArrayOutputStream baos = new
		 * ByteArrayOutputStream(); //
		 * imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //
		 * imageData = baos.toByteArray(); } catch(Exception ex) { } ////
		 * 
		 * /* ///// bmp = BitmapFactory.decodeByteArray(mImageData, 0,
		 * mImageData.length);
		 * 
		 * //bmp = BitmapFactory.decodeByteArray(mImageData, 0,
		 * mImageData.length); Float width = new Float(bmp.getWidth()); Float
		 * height = new Float(bmp.getHeight()); Float ratio = width/height; bmp
		 * = Bitmap.createScaledBitmap(bmp, (int)(THUMBNAIL_HEIGHT*ratio),
		 * THUMBNAIL_HEIGHT, false); int padding = (THUMBNAIL_WIDTH -
		 * bmp.getWidth())/2; imageView.setPadding(padding, 0, padding, 0);
		 * imageView.setImageBitmap(bmp); ByteArrayOutputStream baos = new
		 * ByteArrayOutputStream(); bmp.compress(Bitmap.CompressFormat.PNG, 100,
		 * baos);
		 * 
		 * byte[] byteArray = baos.toByteArray();
		 * 
		 * 
		 * /////
		 */

		return imageView;
	}
	// references to our images
	/*
	 * private Integer[] mThumbIds = { R.drawable.sample_2, R.drawable.sample_3,
	 * R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6,
	 * R.drawable.sample_7, R.drawable.sample_0, R.drawable.sample_1,
	 * R.drawable.sample_2, R.drawable.sample_3, R.drawable.sample_4,
	 * R.drawable.sample_5, R.drawable.sample_6, R.drawable.sample_7,
	 * R.drawable.sample_0, R.drawable.sample_1, R.drawable.sample_2,
	 * R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
	 * R.drawable.sample_6, R.drawable.sample_7 };
	 */

}