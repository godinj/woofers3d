package com.jaxson.lib.gdx.util;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;

public class GdxFileReader
{
	public GdxFileReader()
	{

	}

	public void add(String location, String contents)
	{
		write(location, contents, false);
	}

	private Files getFiles()
	{
		System.out.println(Gdx.files == null);
		return Gdx.files;
	}

	public FileHandle getAbsoluteFileHandle(String path)
	{
		return getFiles().absolute(path);
	}

	public FileHandle getInternalFileHandle(String path)

	{
		return getFiles().internal(path);
	}

	public FileHandle getLocalFileHandle(String path)
	{
		return getFiles().local(path);
	}

	public String read(String location)
	{
		return getAbsoluteFileHandle(location).readString();
	}

	public void write(String location, String contents)
	{
		write(location, contents, true);
	}

	public void write(String location, String contents, boolean overwrite)
	{
		getLocalFileHandle(location).writeString(contents, !overwrite);
	}
}
