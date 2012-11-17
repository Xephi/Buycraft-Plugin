package net.buycraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerListener implements Listener 
{
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		BukkitInterface.getInstance().getChatManager().enableChat(event.getPlayer());
	}	
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(PlayerChatEvent event)
	{
		if(BukkitInterface.getInstance().getChatManager().isDisabled(event.getPlayer()))
		{
			event.setCancelled(true);
		}
		else
		{
			for(String playerName: BukkitInterface.getInstance().getChatManager().getDisabledChatList())
			{
				Player player = BukkitInterface.getInstance().getServer().getPlayer(playerName);
				
				event.getRecipients().remove(player);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		if(event.getPlayer().getName().equalsIgnoreCase("Buycraft"))
		{
			event.disallow(Result.KICK_OTHER, "This user has been disabled due to security reasons.");
		}
	}
}
