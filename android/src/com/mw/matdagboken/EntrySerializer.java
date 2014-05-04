package com.mw.matdagboken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.graphics.Bitmap;

public class EntrySerializer 
{
	private static File tryCreateEntryFile(String timeStamp, String fileExstension)
	{
		if (!FileHelper.isExternalStorageWritable())
			return null;
		
	    String imageFileName = "MD_" + timeStamp + fileExstension;
	    File storageDir = new File(FileHelper.getApplicationExternalStoragePath().getAbsolutePath() + "/Matdagboken/" + timeStamp);

	    if (storageDir.mkdirs() || storageDir.exists())
	    {
		    try
	        {
		    	File file = new File(storageDir, imageFileName);
		    	file.createNewFile();
		    	return file;
	        } 
	        catch (IOException ex) 
	        {
	            //TODO make dialogue saying there is not enough space left or whatever fails...
	        	ex.printStackTrace();
	        }
	    }
	    return null;
	}
	
	private static boolean trySaveJSONFile(File file, Entry entry)
	{
		FileOutputStream fileOutpuStream;
		try 
		{
			fileOutpuStream = new FileOutputStream(file);
			JSONSerializer jsonSerializer = new JSONSerializer();
			jsonSerializer.Write(entry, fileOutpuStream);
			
			try 
			{
				fileOutpuStream.flush();
				fileOutpuStream.close();
				return true;
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean trySavePGNFile(File file, Bitmap bitmap)
	{
		FileOutputStream fileOutpuStream;
		try 
		{
			fileOutpuStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutpuStream);
			
			try
			{
				fileOutpuStream.flush();
				fileOutpuStream.close();
				return true;
			} 
			catch(IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean trySaveEntry(Entry entry, Bitmap bitmap)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
	    String timeStamp = dateFormat.format(new Date());

	    File jsonFile = tryCreateEntryFile(timeStamp, ".json");
		if (jsonFile != null)
		{
			if (!trySaveJSONFile(jsonFile, entry))
				return false;
		}
		
		File imageFile = tryCreateEntryFile(timeStamp, ".jpg");
		if (imageFile != null)
		{
			if (!trySavePGNFile(imageFile, bitmap))
				return false;
		}
		
		return true;
	}
}
