package net.buycraft.commands;

import net.buycraft.BukkitInterface;
import net.buycraft.util.Chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuycraftCommand
{
	public static Boolean process(CommandSender commandSender, String[] args)
	{
		if(args.length > 0)
		{
			if(commandSender instanceof Player == false || commandSender.hasPermission("buycraft.admin") || commandSender.isOp())
			{
				if(args[0].equalsIgnoreCase("secret"))
				{
					if(args.length == 2)
					{
						BukkitInterface.getInstance().getSettings().setString("secret", args[1]);
						
						commandSender.sendMessage(Chat.header());
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("secretKeySet"));
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.footer());
						
						BukkitInterface.getInstance().getServer().reload();
						
						return true;
					}
					else
					{
						commandSender.sendMessage(Chat.header());
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.seperator() + ChatColor.RED + BukkitInterface.getInstance().getLanguage().getString("enterValidSecret"));
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.footer());
						
						return true;
					}
				}
				
				if(BukkitInterface.getInstance().requireStarted(commandSender))
				{
					if(args[0].equalsIgnoreCase("reload"))
					{
						BukkitInterface.getInstance().getPackageManager().loadPackages();
						
						commandSender.sendMessage(Chat.header());
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("packageCacheReloaded"));
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.footer());
				
						return true;
					}
					
					if(args[0].equalsIgnoreCase("forcecheck"))
					{
						BukkitInterface.getInstance().getPackageChecker().process();
						
						commandSender.sendMessage(Chat.header());
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.seperator() + ChatColor.GREEN + BukkitInterface.getInstance().getLanguage().getString("forceCheckPerformed"));
						commandSender.sendMessage(Chat.seperator());
						commandSender.sendMessage(Chat.footer());
				
						return true;
					}
				}
				else
				{
					return true;
				}
			}
			else
			{
				commandSender.sendMessage(ChatColor.RED + "You do not have permission to execute that command.");
				
				return true;
			}
		}
		else
		{
			if(BukkitInterface.getInstance().requireStarted(commandSender))
			{
				commandSender.sendMessage(Chat.header());
				commandSender.sendMessage(Chat.seperator());
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + BukkitInterface.getInstance().getLanguage().getString("mainCommand") + ChatColor.GREEN + " " + BukkitInterface.getInstance().getLanguage().getString("viewAvailablePackagesHelp"));
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + BukkitInterface.getInstance().getLanguage().getString("mainCommand") + " page <ID>:" + ChatColor.GREEN + " " + BukkitInterface.getInstance().getLanguage().getString("navigateThroughPackagesHelp"));
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + BukkitInterface.getInstance().getLanguage().getString("mainCommand") + " <ID>: " + ChatColor.GREEN + " " + BukkitInterface.getInstance().getLanguage().getString("purchaseSpecificPackageHelp"));
				commandSender.sendMessage(Chat.seperator());
				commandSender.sendMessage(Chat.seperator());
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + "Server ID: " + ChatColor.GREEN + String.valueOf(BukkitInterface.getInstance().getServerID()));
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + "Plugin version: " + ChatColor.GREEN + String.valueOf(BukkitInterface.getInstance().getVersion()));
				commandSender.sendMessage(Chat.seperator() + ChatColor.LIGHT_PURPLE + "Website: " + ChatColor.GREEN + "https://buycraft.net");
				commandSender.sendMessage(Chat.footer());
			}
			
			return true;
		}
		
		return false;
	}
}
