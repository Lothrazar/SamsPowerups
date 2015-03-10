package com.lothrazar.samscontent;

import java.util.ArrayList;

public class RuntimeChangelog 
{

	private ArrayList<String> lines = new 	ArrayList<String>();
	public void log(String change)
	{
		lines.add(change);
	}
}
