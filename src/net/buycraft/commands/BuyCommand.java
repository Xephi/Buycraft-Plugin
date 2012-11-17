package net.buycraft.commands;

import java.util.List;

import net.buycraft.BukkitInterface;
import net.buycraft.packages.PackageModel;
import net.buycraft.util.Chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyCommand
{
	public static Boolean process(CommandSender commandSender, String[] args)
	{
		if(commandSender instanceof Player == false)
		{
			BukkitInterface.getInstance().getLogger().info("You cannot execute this command from inside the console.");
			
			return true;
		}
		
		if(BukkitInterface.getInstance().getSettings().getBoolean("disableBuyCommand"))
		{
			commandSender.sendMessage(ChatColor.RED + "Command has been disabled, please use our webstore instead.");
			
			return true;
		}
		
		if(!BukkitInterface.getInstance().requireStarted(commandSender)) return true;
		
		String pageToView = "1";
		
		if(args.length > 0)
		{
			if(args[0].equalsIgnoreCase("page") && args.length == 2)
			{
				pageToView = args[1];
			}
			else
			{
				if(args.length == 1 && isNumber(args[0]))
				{
					Integer packageID = Integer.valueOf(args[0]);
					
					boolean packageExists = false;
					
					for(PackageModel row : BukkitInterface.getInstance().getPackageManager().getPackagesForSale())
					{
						if(row.getId() == packageID)
						{
							packageExists = true;
							
							break;
						}
					}
				
					if(packageExists == true)
					{
						String shortUrlLink = "http://is.gd/create.php?format=simple&url=http://buycraft.net/redirect/addcart/" + BukkitInterface.getInstance().getServerID() + "/" + packageID + "/" + commandSender.getName();
						String shortenedUrl =  BukkitInterface.getInstance().getApi().HttpRequest(shortUrlLink);
						
						if(shortenedUrl != null)
						{
							commandSender.sendMessage(Chat.header());
							commandSender.sendMessage(Chat.seperator());
							commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("pleaseVisit") + ":");
							commandSender.sendMessage(Chat.seperator());
							commandSender.sendMessage(Chat.seperator() + shortenedUrl);
							commandSender.sendMessage(Chat.seperator());
							commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("turnChatBackOn"));
							commandSender.sendMessage(Chat.seperator());
							commandSender.sendMessage(Chat.footer());
							
							BukkitInterface.getInstance().getChatManager().disableChat((Player) commandSender);
						}
						else
						{
							 BukkitInterface.getInstance().getLogger().severe("Could not generate a shortened URL.");
						}
					}
					else
					{
						commandSender.sendMessage(Chat.header());
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("packageNotFound"));
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.footer());
					}	
					
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		if(isNumber(pageToView) && pageToView.length() < 5)
		{
			Integer pageNumber = Integer.parseInt(pageToView);
			
			List<PackageModel> packages = BukkitInterface.getInstance().getPackageManager().getPackagesForSale();
			
			if(packages.size() == 0)
			{
				commandSender.sendMessage(Chat.header());
				commandSender.sendMessage(Chat.seperator());
				commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("noPackagesForSale"));
				commandSender.sendMessage(Chat.seperator());
				commandSender.sendMessage(Chat.footer());
			}
			else
			{
				int pageCount = (int) Math.ceil(packages.size() / 3.0);
				
				int startingPoint = -3 + (3 * pageNumber);
				int finishPoint = 0 + (3 * pageNumber);
				
				if(finishPoint > packages.size() || finishPoint < 3) finishPoint = packages.size();
				if(startingPoint > packages.size() || startingPoint < 0) startingPoint = packages.size();
				
				List<PackageModel> packagesToDisplay = packages.subList(startingPoint, finishPoint);
				
				if(packagesToDisplay.size() > 0)
				{
					BukkitInterface.getInstance().getChatManager().disableChat((Player) commandSender);
					
					commandSender.sendMessage(Chat.header());
					commandSender.sendMessage(Chat.seperator());
					commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("toPurchase") + " " + ChatColor.LIGHT_PURPLE + BukkitInterface.getInstance().getLanguage().getString("mainCommand") + " <ID>");
					
					if(pageCount > 1) 
					{
						commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("howToNavigate") + " " + ChatColor.LIGHT_PURPLE + BukkitInterface.getInstance().getLanguage().getString("mainCommand") + " page <1-" + pageCount  + ">");
					}
					
					commandSender.sendMessage(Chat.seperator());
					
					for(PackageModel row : packagesToDisplay)
					{
						commandSender.sendMessage(Chat.seperator() + ChatColor.YELLOW + BukkitInterface.getInstance().getLanguage().getString("packageId") + ": " + ChatColor.LIGHT_PURPLE + row.getId());
						commandSender.sendMessage(Chat.seperator() + ChatColor.YELLOW + BukkitInterface.getInstance().getLanguage().getString("packageName") + ": " + ChatColor.LIGHT_PURPLE + row.getName());
						commandSender.sendMessage(Chat.seperator() + ChatColor.YELLOW + BukkitInterface.getInstance().getLanguage().getString("packagePrice") + ": " + ChatColor.LIGHT_PURPLE + row.getPrice() + ' ' + BukkitInterface.getInstance().getServerCurrency());
						commandSender.sendMessage(Chat.seperator());
					}
					
					commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("turnChatBackOn"));
					commandSender.sendMessage(Chat.seperator());
					commandSender.sendMessage(Chat.footer());
				}
				else
				{
					commandSender.sendMessage(Chat.header());
					commandSender.sendMessage(Chat.seperator());
					commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("pageNotFound"));
					commandSender.sendMessage(Chat.seperator());
					commandSender.sendMessage(Chat.footer());
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private static boolean isNumber(String string)
	{
	      char[] c = string.toCharArray();
	      
	      for(int i=0; i < string.length(); i++)
	      {
	          if(!Character.isDigit(c[i]))
	          {
	             return false;
	          }
	     }
	      
	     return true;
	}
}
