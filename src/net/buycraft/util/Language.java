package net.buycraft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

public class Language 
{
	private final String LOCATION = "plugins/Buycraft/language.conf";
	private File file;
	
	private HashMap<String, String> defaultProperties;
	private Properties properties;
	
	public Language()
	{
		this.file = new File(LOCATION);
		
		this.defaultProperties = new HashMap<String, String>();
		this.properties = new Properties();
		
		load();
		assignDefault();
	}
		
	private void load()
	{
		try 
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			properties.load(new FileInputStream(LOCATION));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void assignDefault()
	{
		Boolean toSave = false;
		
		defaultProperties.put("mainCommand", "/buy");
		defaultProperties.put("chatEnabled", "Your chat is now enabled");
		defaultProperties.put("chatAlreadyEnabled", "Your chat is already enabled");
		defaultProperties.put("viewAvailablePackagesHelp", "View available packages for sale");
		defaultProperties.put("navigateThroughPackagesHelp", "Navigate through package pages");
		defaultProperties.put("purchaseSpecificPackageHelp", "Purchase a specific package");
		defaultProperties.put("purchasedPackageExpired", "Your purchased package has now expired");
		defaultProperties.put("purchasedPackageClaimed", "Your purchased package has been credited to your account");
		defaultProperties.put("pleaseVisit", "Please click the link below to continue with the purchase");
		defaultProperties.put("turnChatBackOn", "Type /ec to turn your chat back on");
		defaultProperties.put("packageNotFound", "Package not found");
		defaultProperties.put("noPackagesForSale", "We currently do not have any packages for sale");
		defaultProperties.put("toPurchase", "To purchase a package, please type");
		defaultProperties.put("howToNavigate", "Browse through our packages by using");
		defaultProperties.put("packageId", "ID");
		defaultProperties.put("packageName", "Name");
		defaultProperties.put("packagePrice", "Price");
		defaultProperties.put("pageNotFound", "Page not found");
		defaultProperties.put("packageCacheReloaded", "Package cache successfully reloaded");
		defaultProperties.put("forceCheckPerformed", "Force check successfully performed");
		defaultProperties.put("notStarted", "Buycraft incorrectly setup, please read the README.txt file");
		defaultProperties.put("secretKeySet", "Secret key successfully updated. Reloading server...");
		defaultProperties.put("enterValidSecret", "Please enter a valid API secret");
		
		for (Entry<String, String> entry : defaultProperties.entrySet()) 
		{
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if(!properties.containsKey(key))
		    {
		    	properties.setProperty(key, value);
		    	
		    	toSave = true;
		    }
		}
		
		if(toSave)
		{
			try 
			{
				properties.store(new FileOutputStream(LOCATION), "Buycraft Plugin (English language file)");
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public String getString(String key)
	{
		if(properties.containsKey(key))
		{
			return properties.getProperty(key);
		}
		else
		{
			throw new RuntimeException("Language key '" + key + "' not found in the language.conf file.");
		}
	}
}
