package me.mrCookieSlime.TARDISPlus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.mrCookieSlime.CSCoreLibPlugin.PluginUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibSetup.CSCoreLibLoader;
import me.mrCookieSlime.TARDISPlus.TARDIS.Rotation;
import me.mrCookieSlime.TARDISPlus.TARDIS.TARDIS;
import me.mrCookieSlime.TARDISPlus.items.AccessoireListener;
import me.mrCookieSlime.TARDISPlus.races.TimeLord.RegenerationListener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TARDISPlus extends JavaPlugin implements Listener {
	
	private static TARDISPlus instance;
	Config cfg;
	
	@Override
	public void onEnable() {
		CSCoreLibLoader loader = new CSCoreLibLoader(this);
		
		if (loader.load()) {
			if (!new File("TARDIS+/worlds").exists()) new File("TARDIS+/worlds").mkdirs();
			if (!new File("TARDIS+/TARDIS").exists()) new File("TARDIS+/TARDIS").mkdirs();
			if (!new File("TARDIS+/schematics").exists()) new File("TARDIS+/schematics").mkdirs();
			
			for (File file: new File("TARDIS+/TARDIS").listFiles()) {
				new TARDIS(file);
			}
			
			for (Rotation direction: Rotation.values()) {
				InputStream stream = getClass().getResourceAsStream("schematics/TARDIS_" + direction.toString() + ".schematic");
			    OutputStream out = null;
			    int read;
			    byte[] buffer = new byte[4096];
			    try {
			        out = new FileOutputStream(new File("TARDIS+/schematics/" + direction.toString() + ".schematic"));
			        while ((read = stream.read(buffer)) > 0) {
			            out.write(buffer, 0, read);
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    } finally {
			        try {
						stream.close();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			    }
			}
			
			PluginUtils utils = new PluginUtils(this);
			utils.setupConfig();
			cfg = utils.getConfig();
			
			new AccessoireListener(this);
			new RegenerationListener(this);
			
			getServer().getPluginManager().registerEvents(this, this);
			
			instance = this;
		}
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public static TARDISPlus getInstance() 		{			return instance;		}
	public Config getCfg() 						{			return cfg;				}
}
