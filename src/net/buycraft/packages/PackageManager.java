package net.buycraft.packages;

import java.util.ArrayList;
import java.util.List;

import net.buycraft.BukkitInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Manages all packages available for sale
 */
public class PackageManager 
{
	private List<PackageModel> packagesForSale;
	
	public PackageManager()
	{
		this.packagesForSale = new ArrayList<PackageModel>();
	}
	
	public void loadPackages()
	{
		packagesForSale.clear();
		
		try
		{
			JSONObject apiResponse = BukkitInterface.getInstance().getApi().packagesAction();
			
			if(apiResponse != null && apiResponse.isNull("payload") == false)
			{
				JSONArray packages = apiResponse.getJSONArray("payload");
				
				for (int i = 0; i < packages.length(); i++) 
				{
					if(packages.isNull(i) == false)
					{
						JSONObject row = packages.getJSONObject(i);
				   			
						addPackage(row.getInt("id"), 
								row.get("name").toString(),
								row.get("price").toString());
					}
				}
				
				BukkitInterface.getInstance().getLogger().info("Loaded " + packages.length() + " package(s) into the cache.");
			}
			else
			{
				BukkitInterface.getInstance().getLogger().severe("Failed to load packages due to null payload.");
			}
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			
			BukkitInterface.getInstance().getLogger().severe("Failed to load packages due to JSON parse error.");
		}
	}
	
	/**
	 * Add a package to the package list
	 */
	private void addPackage(int id, String name, String price)
	{
		packagesForSale.add(new PackageModel(id, name, price));
	}
	
	/**
	 * Returns all packages currently loaded and available for purchase
	 */
	public List<PackageModel> getPackagesForSale()
	{
		return packagesForSale;
	}
	
	/**
	 * Return a package from its ID
	 */
	public PackageModel getPackageById(int packageId)
	{
		for(PackageModel packageModel : packagesForSale)
		{
			if(packageModel.getId() == packageId)
			{
				return packageModel;
			}
		}
		
		return null;
	}
}
