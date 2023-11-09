package com.ollicafe.ollissupertools;

import org.bukkit.plugin.java.JavaPlugin;


public class OllisSuperTools extends JavaPlugin{
	
	@Override
	public void onEnable() {
		Tool tool = new Tool();
		tool.init(this);
		this.getServer().getPluginManager().registerEvents(new ToolListener(this), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	

}
