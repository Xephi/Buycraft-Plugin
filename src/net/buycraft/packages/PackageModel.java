package net.buycraft.packages;

/**
 * Represents a package data structure
 */
public class PackageModel 
{
	private int id;
	private String name;
	private String price;
	
	public PackageModel(int id, String name, String price)
	{
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPrice()
	{
		return price;
	}
}
