---- Buycraft Plugin ------------------------

For setup instructions, please visit https://buycraft.net.

Purchase premium for added features such as an online webstore (https://example.buycraft.net)

You may contact a member of support via visiting the support link inside of your server panel located at https://server.buycraft.net.


--- INSTALLATION --- ------------------------------------------------------

Please run the command "/buycraft secret (Your secret key here)" in game/console, to find your secret key, go to https://server.buycraft.net and visit the server settings page.
Refer to installation videos and tutorials available on Buycraft.net for more help/info.


---- MCMYADMIN INTERGRATION ------------------------------------------------

If you are aiming to use McMyAdmin commands in packages, prefix the commands you enter with "{mcmyadmin}", for example,
to execute the McMyAdmin "stop" command, you would enter this: "{mcmyadmin}stop". After you have setup the commands, you need
to give administrator access to the user "Buycraft" (Case sensitive) in the McMyAdmin permissions page. The user "Buycraft" has
been disabled from logging in to your server, to prevent administrator access in game.


---- CHANGING THE /BUY COMMAND ---------------------------------------------

If you are using a plugin which already uses the /buy command, follow the instructions below to fix this problem:
	
	1. Download a program such as WinRar which is capable of opening .jar files.
	2. Open the Buycraft.jar file with the archive program you previously downloaded.
	3. Inside Buycraft.jar, you will find a file called plugin.yml, open this file with a text editor.
	4. Scroll down to line 7 of the plugin.yml file, and change "buy" to either "donate", "store", "shop", or "purchase".
	5. Open language.conf  which is located in the Buycraft directory, and change the "mainCommand" variable to the command which you chose above.
	6. Save all modified files, and restart your server - job done!


---- PERMISSION NODES -----------------------------------------------------

Listed below are the permission nodes for the plugin:

	buycraft.admin - Enables use of the "/buycraft <reload/forcecheck>" commands


---- A FURTHER NOTE --------------------------------------------------------

You are welcome to modify or improve the supplied source code inside of the jar file, however releasing it is not
allowed and you will be banned from accessing the Buycraft API.


---- CHANGE LOG ------------------------------------------------------------

	Version 4.4
	
		- Added the "/buycraft secret" command to set your secret key when in game/console
		- Commands will now show a message if the plugin is not enabled successfully, instead of displaying nothing

	Version 4.3
	
		- Settings value added for either using HTTP or HTTPS to call the buycraft.net servers. (HTTPS is advised!)
		- Settings value added to disable the /buy command, if you want to only allow transactions to be carried out via your online webstore
		
	Version 4.2
	
		- Rewrite of the entire Bukkit plugin structure
		- New settings/language handling
		- Now uses HTTPS for the API
		- Disable of auto-update (Not advised to actually enable, but requested to be added)
		- Fix of the settings.conf being formatted incorrectly
		- Addition of "buycraft.admin" permission node, to enable the "/buycraft <reload/forcecheck>" commands to specific users only (OP is allowed)