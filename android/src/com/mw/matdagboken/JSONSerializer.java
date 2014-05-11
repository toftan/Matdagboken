package com.mw.matdagboken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.owlike.genson.Genson;
import com.owlike.genson.TransformationException;

public class JSONSerializer 
{	
	private Genson mGenson = new Genson();
	
	public void Write(Object obj, FileOutputStream fileOutpuStream)
	{
		try 
		{
			mGenson.serialize(obj, fileOutpuStream);
		} 
		catch (TransformationException e) 
		{
			System.err.println("JSONSerializer: input object not compatible with Genson serializer.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.err.println("JSONSerializer: failed to write to file output stream.");
			e.printStackTrace();
		}
	}
	
	public <T> T Read(Class<T> objectType, FileInputStream fileInputSteram)
	{
		T objectInstance = null;
		/*try
		{
			objectInstance = (T) objectType.newInstance();
		} 
		catch (InstantiationException e1)
		{
			System.err.println("JSONSerializer: input object could not be instantiated, probably missing a default constructor.");
			e1.printStackTrace();
		} 
		catch (IllegalAccessException e1)
		{
			System.err.println("JSONSerializer: accessed null object, most likely input object could not be instantiated, probably missing a default constructor.");
			e1.printStackTrace();
		}*/
		
		try
		{
			objectInstance = mGenson.deserialize(fileInputSteram, objectType);
		} 
		catch (TransformationException e)
		{
			System.err.println("JSONSerializer: input object not compatible with Genson serializer.");
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			System.err.println("JSONSerializer: failed to read from file input stream.");
			e.printStackTrace();
		}
		
		return objectInstance;
	}
}
