package net.buycraft;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ChatManager extends Thread
{
	private ArrayList<String> disabledChatList;
	
	public ChatManager()
	{
		disabledChatList = new ArrayList<String>();
	}
	
	public Boolean isDisabled(Player player)
	{
		if(disabledChatList.contains(player.getName()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void enableChat(Player player)
	{
		if(disabledChatList.contains(player.getName()))
		{
			disabledChatList.remove(player.getName());
		}
	}
	
	public void disableChat(Player player)
	{
		if(!disabledChatList.contains(player.getName()))
		{
			disabledChatList.add(player.getName());
		}
	}
	
	public ArrayList<String> getDisabledChatList()
	{
		return disabledChatList;
	}
}
