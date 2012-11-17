package net.buycraft.packages;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.buycraft.BukkitInterface;
import net.buycraft.util.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PackageChecker extends Thread
{
	public void run()
	{
		if(BukkitInterface.getInstance().getServer().getOnlinePlayers().length > 0)
		{
			process();
		}
	}
	
	private void executeCommand(String command, String username)
	{
		command = command.replace("{name}", username);
		command = command.replace("(name)", username);
		command = command.replace("{player}", username);
		command = command.replace("(player)", username);
		command = command.replace("{username}", username);
		command = command.replace("(username)", username);
		command = command.replace("<name>", username);
		command = command.replace("<name>", username);
		command = command.replace("<player>", username);
		command = command.replace("<player>", username);
		command = command.replace("<username>", username);
		command = command.replace("<username>", username);
		command = command.replace("[name]", username);
		command = command.replace("[name]", username);
		command = command.replace("[player]", username);
		command = command.replace("[player]", username);
		command = command.replace("[username]", username);
		command = command.replace("[username]", username);
		
		if(command.startsWith("{mcmyadmin}"))
		{
			String newCommand = command.replace("{mcmyadmin}", "");
			
			Logger.getLogger("McMyAdmin").info("Buycraft tried command: " + newCommand);
		}
		else
		{
			BukkitInterface.getInstance().getServer().dispatchCommand(BukkitInterface.getInstance().getServer().getConsoleSender(), command);
		}
	}
	
	/**
	 * Performs the actual checker, we put it in its own method to allow external access
	 */
	public void process()
	{
		try
		{
			JSONObject apiResponse = BukkitInterface.getInstance().getApi().checkerGetAction();
            
			if(apiResponse != null)
			{
				JSONObject apiPayload = apiResponse.getJSONObject("payload");
				JSONArray expirysPayload = apiPayload.getJSONArray("expirys");
				JSONArray claimablesPayload = apiPayload.getJSONArray("claimables");
				
				ArrayList<String> executedExpirys = new ArrayList<String>();
				ArrayList<String> executedClaimables = new ArrayList<String>();
				
				/**
				 * Process expirys
				 */
			    if(expirysPayload.length() > 0)
				{
					for (int i = 0; i < expirysPayload.length(); i++) 
					{
						JSONObject row = expirysPayload.getJSONObject(i);
						
						String username = row.getString("ign");
						Boolean requireOnline = row.getBoolean("requireOnline");
						JSONArray commands = row.getJSONArray("commands");
						
						Player currentPlayer = BukkitInterface.getInstance().getServer().getPlayer(username);
					
						if(currentPlayer != null || requireOnline == false)
						{
							BukkitInterface.getInstance().getLogger().info("Executing expiry command(s) on behalf of user '" + username + "'.");
							
							for (int e = 0; e < commands.length(); ++e) 
							{
							    executeCommand(commands.getString(e), username);
							}
							
							if(executedExpirys.contains(username) == false) 
							{
								executedExpirys.add(username);
							}
							
							if(currentPlayer != null)
							{
								currentPlayer.sendMessage(Chat.header());
								currentPlayer.sendMessage(Chat.seperator());
								currentPlayer.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("purchasedPackageExpired"));
								currentPlayer.sendMessage(Chat.seperator());
								currentPlayer.sendMessage(Chat.footer());
							}
						}
					}
				}
			    
			    /**
			     * Process claimables
			     */
			    if(claimablesPayload.length() > 0)
				{
					for (int i = 0; i < claimablesPayload.length(); i++) 
					{
						JSONObject row = claimablesPayload.getJSONObject(i);
						
						String username = row.getString("ign");
						Boolean requireOnline = row.getBoolean("requireOnline");
						JSONArray commands = row.getJSONArray("commands");
						
						Player currentPlayer = BukkitInterface.getInstance().getServer().getPlayer(username);

						if(currentPlayer != null || requireOnline == false)
						{
							BukkitInterface.getInstance().getLogger().info("Executing claimable command(s) on behalf of user '" + username + "'.");
							
							for (int c = 0; c < commands.length(); ++c) 
							{
							    executeCommand(commands.getString(c), username);
							}
							
							if(executedClaimables.contains(username) == false)
							{
								executedClaimables.add(username);
							}
							
							if(currentPlayer != null)
							{
								currentPlayer.sendMessage(Chat.header());
								currentPlayer.sendMessage(Chat.seperator());
								currentPlayer.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("purchasedPackageClaimed"));
								currentPlayer.sendMessage(Chat.seperator());
								currentPlayer.sendMessage(Chat.footer());
							}
						}
					}
				}
			    
			    if(executedExpirys.size() > 0 || executedClaimables.size() > 0)
			    {
			    	BukkitInterface.getInstance().getApi().checkerDeleteAction(
			    			new JSONArray(executedClaimables.toArray()).toString(), 
			    			new JSONArray(executedExpirys.toArray()).toString());
			    }
			}
		}
		catch(JSONException e)
		{
			BukkitInterface.getInstance().getLogger().severe("JSON Parsing error.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
