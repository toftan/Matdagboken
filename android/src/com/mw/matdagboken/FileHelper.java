package com.mw.matdagboken;

import java.io.File;
import android.content.Context;
import android.os.Environment;

public class FileHelper 
{
	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() 
	{
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) 
	    {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() 
	{
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) 
	    {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Returns the absolute path to the directory on the filesystem where files created with openFileOutput(String, int) are stored.
	 * No permissions are required to read or write to the returned path, since this path is internal storage.
	 * The files will be deleted when the application in uninstalled
	 */
	public static File getApplicationInternalStoragePath(Context context)
	{
		return context.getFilesDir();
	}
	
	/**
	 * This is like getFilesDir() in that these files will be deleted when the application is uninstalled, however there are some important differences: 
	 * External files are not always available: they will disappear if the user mounts the external storage on a computer or removes it. 
	 * See the APIs on Environment for information in the storage state.
	 * There is no security enforced with these files. For example, any application holding WRITE_EXTERNAL_STORAGE can write to these files. 
	 */
	public static File getApplicationExternalStoragePath()
	{
		return Environment.getExternalStorageDirectory();
	}
	
	/**
	 * Get a top-level public external storage directory for placing files of a particular type. 
	 * This is where the user will typically place and manage their own files, so you should be careful about what you put here to 
	 * ensure you don't erase their files or get in the way of their own organization. 
	 */
	public static File getCameraStoragePath() 
	{
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
	}
	public static File getPicturesStoragePath() 
	{
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	}

	

}
