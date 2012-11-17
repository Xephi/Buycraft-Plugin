package net.buycraft.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import net.buycraft.BukkitInterface;
import net.buycraft.util.Updater;

public class Api 
{
	private String apiUrl;
	private String apiKey;
	
	public Api()
	{
		this.apiKey = BukkitInterface.getInstance().getSettings().getString("secret");
		
		if(BukkitInterface.getInstance().getSettings().getBoolean("https"))
		{
			this.apiUrl = "https://api.buycraft.net/v3";
		}
		else
		{
			this.apiUrl = "http://api.buycraft.net/v3";
		}
	}

	public Boolean infoAction()
	{
		try 
		{
			HashMap<String, String> apiCall = new HashMap<String, String>();
			
			apiCall.put("action", "info");
						
			JSONObject apiResponse = call(apiCall);
		
			if(apiResponse != null)
			{
				if(apiResponse.getInt("code") == 0)
				{
					JSONObject payload = apiResponse.getJSONObject("payload");
					
					BukkitInterface.getInstance().setServerID(payload.getInt("serverId"));
					BukkitInterface.getInstance().setServerCurrency(payload.getString("serverCurrency"));
					
					if(payload.getDouble("latestVersion") > Double.valueOf(BukkitInterface.getInstance().getVersion()))
					{
						String downloadUrl = payload.getString("latestDownload");

						if(BukkitInterface.getInstance().getSettings().getBoolean("autoUpdate"))
						{
							Updater.performUpdate(downloadUrl);
						}
						else
						{
							BukkitInterface.getInstance().getLogger().info("Egnoring update due to auto update disabled.");
						}
					}
					
					return true;
				}
				else if(apiResponse.getInt("code") == 101)
				{
					BukkitInterface.getInstance().getLogger().severe("Could not find an account with the specified API secret!");
					
					return false;
				}
			}
		} 
		catch(JSONException e)
		{
			BukkitInterface.getInstance().getLogger().severe("JSON parsing error.");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		BukkitInterface.getInstance().getLogger().severe("Unexpected error occured in the authentication process.");
		
		return false;
	}
	
	public JSONObject packagesAction()
	{
		HashMap<String, String> apiCallParams = new HashMap<String, String>();
		
		apiCallParams.put("action", "packages");
		
		return call(apiCallParams);
	}

	public JSONObject checkerGetAction()
	{
		HashMap<String, String> apiCallParams = new HashMap<String, String>();
		
		apiCallParams.put("action", "checker");
		apiCallParams.put("do", "lookup");
		
		return call(apiCallParams);
	}

	public void checkerDeleteAction(String claimablesToDelete, String expirysToDelete)
	{
		HashMap<String, String> apiCallParams = new HashMap<String, String>();
		
		apiCallParams.put("action", "checker");
		apiCallParams.put("do", "remove");
		
		apiCallParams.put("expirys", expirysToDelete);
		apiCallParams.put("claimables", claimablesToDelete);
		
		call(apiCallParams);
	}
	
	private JSONObject call(HashMap<String, String> apiCallParams)
	{
		if(apiKey.length() == 0)
		{
			apiKey = "unspecified";
		}
		
		apiCallParams.put("secret", apiKey);
		apiCallParams.put("version", String.valueOf(BukkitInterface.getInstance().getVersion()));
		
		String url = apiUrl + generateUrlQueryString(apiCallParams);
		
		if(url != null)
		{
			String HTTPResponse = HttpRequest(url);
			
			try 
			{
				if(HTTPResponse != null)
				{
					return new JSONObject(HTTPResponse);
				}
				else
				{
					return null;
				}
			} 
			catch (JSONException e) 
			{
				BukkitInterface.getInstance().getLogger().severe("JSON parsing error.");
			}
		}
		
		return null;
	}
	
	public String HttpRequest(String url)
	{
		try
		{
			String content = "";
			
			URL conn = new URL(url);
	        URLConnection yc = conn.openConnection();
	        
	        yc.setConnectTimeout(5000);
	        yc.setReadTimeout(5000);
	        
	        BufferedReader in;
			
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

	        String inputLine;
	       
			while ((inputLine = in.readLine()) != null) 
			{
				content = content + inputLine;
			}
			
			in.close();
			
			return content;
		}
		catch(ConnectException e)
		{
			BukkitInterface.getInstance().getLogger().severe("HTTP request failed due to connection error.");
		}
		catch(SocketTimeoutException e)
		{
			BukkitInterface.getInstance().getLogger().severe("HTTP request failed due to timeout error.");
		}
		catch(FileNotFoundException e)
		{
			BukkitInterface.getInstance().getLogger().severe("HTTP request failed due to file not found.");
		}
		catch(UnknownHostException e)
		{
			BukkitInterface.getInstance().getLogger().severe("HTTP request failed due to unknown host.");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String generateUrlQueryString(HashMap<String, String> map)
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("?");
		
        for (Map.Entry<String, String> entry : map.entrySet()) 
        {
            if (sb.length() > 1) 
            {
                sb.append("&");
            }
            
            sb.append(String.format("%s=%s",
            	entry.getKey().toString(),
            	entry.getValue().toString()
            ));
        }

        return sb.toString();  
	}
}
