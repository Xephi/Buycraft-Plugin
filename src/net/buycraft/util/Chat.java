package net.buycraft.util;

import org.bukkit.ChatColor;

public class Chat 
{
	public static String header()
	{
		return ChatColor.WHITE + "|----------------------" + ChatColor.LIGHT_PURPLE + " BUYCRAFT " + ChatColor.WHITE + "---------------------";
	}
	
	public static String footer()
	{
		return ChatColor.WHITE + "|----------------------------------------------------";
	}
	
	public static String seperator()
	{
		return ChatColor.WHITE + "| ";
	}
}
