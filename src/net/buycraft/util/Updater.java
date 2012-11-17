package net.buycraft.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.buycraft.BukkitInterface;

public class Updater 
{	
	public static void performUpdate(String latestDownloadUrl)
	{
		BukkitInterface.getInstance().getLogger().info("Please wait, downloading latest version...");
		
		loadAllClasses();
		
		try
		{
			File local = new File("plugins/Buycraft.jar");
			
	        OutputStream outputStream = new FileOutputStream(local);
	        
	        URL url = new URL(latestDownloadUrl);
	        URLConnection connection = url.openConnection();
	        
	        InputStream inputStream = connection.getInputStream();

	        byte[] buffer = new byte[1024];
	        int read = 0;
 
	        while ((read = inputStream.read(buffer)) > 0) 
	        {
	        	outputStream.write(buffer, 0, read);
	        }
	        	
	        outputStream.close();
	        inputStream.close();
	        
	        BukkitInterface.getInstance().getLogger().info("Installed latest version, please restart to apply changes.");
		} 
		catch (IOException e) 
		{
			BukkitInterface.getInstance().getLogger().info("Failed to download new version. " + e.getLocalizedMessage());
		}
	}
	
    private static void loadAllClasses() 
    {
        try
        {
            JarFile jar = new JarFile(BukkitInterface.getInstance().getJarFile());
            
            Enumeration<JarEntry> enumeration = jar.entries();

            while (enumeration.hasMoreElements()) 
            {
                JarEntry entry = enumeration.nextElement();
                String name = entry.getName();

                if (name.endsWith(".class")) 
                {
                    String path = name.replaceAll("/", ".");
                    path = path.substring(0, path.length() - ".class".length());

                    BukkitInterface.getInstance().getClass().getClassLoader().loadClass(path);
                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
