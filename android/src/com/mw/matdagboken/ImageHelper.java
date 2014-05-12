package com.mw.matdagboken;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class ImageHelper
{
	public static class EntryFile
	{
		public File mJsonFile = null;
		public File mPngFile = null;
	}
	
    private static class PngFileFilter implements FilenameFilter
    {
        public boolean accept(File dir, String filename) 
        {
            return filename.endsWith(".png");
        }
    }
    private static PngFileFilter sPngFileFilter = new PngFileFilter();
    
    public static ArrayList<EntryFile> getEntryFiles()
    {
        ArrayList<EntryFile> images = new ArrayList<EntryFile>();
        
        String applicationDir = FileHelper.getApplicationExternalStoragePath().getAbsolutePath() + "/Matdagboken/";
        File storageDir = new File(applicationDir);       
        File entryDirs[] = storageDir.listFiles();

        for (int i=0; i<entryDirs.length; i++)
        {
            File entryDir = entryDirs[i];
            
            if (entryDir.isDirectory())
            {
            	String fileName = entryDir.getName();
            	String fullFileName = entryDir + "/MD_" + fileName;
            	File jsonFile = new File(fullFileName + ".json");
            	File pngFile = new File(fullFileName + ".png");
            	
            	if(jsonFile.exists() && pngFile.exists())
            	{
            		EntryFile entryFile = new ImageHelper.EntryFile();
            		entryFile.mJsonFile = jsonFile;
            		entryFile.mPngFile = pngFile;
            		images.add(entryFile);
            	}
            }
        }
        return images;
    }
    
    public static Bitmap decodeAndCropFileToFitSize(File imageFile, int targetSize)
	{
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	    int minPhotoSize = Math.min(photoW, photoH);
	    
	    //This indicates that we failed to decode the image and can not proceed
	    if (minPhotoSize <= 0)
	    	return null;
	    
	    //Try to get target size
	    targetSize = Math.min(targetSize, minPhotoSize);

	    // Determine how much to scale down the image
	    int scaleFactor = Math.max(1, Math.min(photoW/targetSize, photoH/targetSize));

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	    
	    //This will decode the bitmap into memory
	    Bitmap fullBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    
	    photoW = bmOptions.outWidth;
	    photoH = bmOptions.outHeight;
	    minPhotoSize = Math.min(photoW, photoH);
	    if (minPhotoSize <= 0)
	    	return null;
	    
	    //Create the target bitmap
	    Bitmap croppedBitmap = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(croppedBitmap);
	    
	    //Paint the loaded image into the new bitmap which later on will be saved
	    int size = Math.min(photoW, photoH);
	    int x = (photoW - size) / 2;
	    int y = (photoH - size) / 2;
	    Rect srcRect = new Rect(x, y, x + size, y + size);
	    Rect dstRect = new Rect(0, 0, targetSize, targetSize);
	    canvas.drawBitmap(fullBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

	    fullBitmap.recycle();
	    return croppedBitmap;
	}
    
}
