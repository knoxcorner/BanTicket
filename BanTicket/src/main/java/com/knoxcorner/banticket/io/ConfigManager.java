package com.knoxcorner.banticket.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;



import com.knoxcorner.banticket.BanTicket;
import com.knoxcorner.banticket.util.Util;

public class ConfigManager
{
	public final static int CONFIG_VERSION = 1;
	
	private BanTicket pl;
	private File configFile;
	
	private long timeTillExpiry;
	private boolean approveOnExpire;
	private boolean logLogins;
	private boolean logDisconnects;
	private int numCommonIps;
	private int minLoginsFromIp;
	private int minNumTotalLogins;
	private boolean saveToMinecraft;
	private boolean broadcastWarn;
	private boolean broadcastBan;
	
	public ConfigManager(BanTicket plugin)
	{
		this.pl = plugin;
		this.configFile = new File(pl.getDataFolder(), "config.yml");
	}
	
	public void loadConfig()
	{
		if(!configFile.exists())
			this.saveConfig();
		FileConfiguration config = pl.getConfig();
		
		if(config.getInt("ConfigVersion", CONFIG_VERSION) > CONFIG_VERSION)
		{
			pl.getLogger().warning("Config version is ahead of plugin! Will continue anyways.");
		}
		else if(config.getInt("ConfigVersion", CONFIG_VERSION) < CONFIG_VERSION)
		{
			pl.getLogger().warning("That... That config version... That shouldn't exist!");
			pl.getLogger().warning("Glitch in the matrix");
		}
		
		this.approveOnExpire = config.getBoolean("ApproveOnExpire", false);
		String timeToExpire = config.getString("ExpireTime", "2d0h0m0s");
		long msToExpire = Util.msFromTime(timeToExpire);
		if(msToExpire == -1)
		{
			pl.getLogger().severe("\"" + timeToExpire + "\" is not a valid time for ExpireTime. Tickets will expire on default of 48 hours.");
			msToExpire = Util.msFromTime("2d");
		}
		this.timeTillExpiry = msToExpire;
		
		this.logLogins = config.getBoolean("LogLogins", false);
		this.logDisconnects = config.getBoolean("LogDisconnects", false);
		this.numCommonIps = config.getInt("BanXMostCommonIPs", 0);
		this.minLoginsFromIp = config.getInt("MinLoginsFromIPToBan", 0);
		this.minNumTotalLogins = config.getInt("MinNumTotalLogins", 0);
		this.saveToMinecraft = config.getBoolean("PutBansOnMinecraftFile", false);
	    this.broadcastWarn = config.getBoolean("BroadcastWarnings", false);
	    this.broadcastBan = config.getBoolean("BroadcastBans", false);
		
	}
	
	public void saveConfig()
	{
		if(!configFile.exists())
		{
			pl.saveResource("config.yml", false);
			return;
		}
		
		/*
		FileConfiguration config = pl.getConfig();
		config.set("ApproveOnExpire", true);
		config.set("ExpireTime", Util.msToTime(timeTillExpiry));
		
		config.set("LogLogins", this.logLogins);
		config.set("LogDisconnects", this.logDisconnects);
		config.set("BanXMostCommonIPs", this.numCommonIps);
		config.set("MinLoginsFromIPToBan", this.minLoginsFromIp);
		config.set("MinNumTotalLogins", this.minNumTotalLogins);
		
		try
		{
			config.save(configFile);
		} catch (IOException e)
		{
			e.printStackTrace();
		}*/
		
	}
	
	public long getExpireTime()
	{
		return timeTillExpiry;
	}
	
	public boolean getApproveOnExpire()
	{
		return approveOnExpire;
	}
	
	public boolean getLogLogins()
	{
		return this.logLogins;
	}
	
	public boolean getLogDisconnect()
	{
		return this.logDisconnects;
	}
	
	public int getNumberCommonIps()
	{
		return this.numCommonIps;
	}
	
	public int getMinLoginsFromIp()
	{
		return this.minLoginsFromIp;
	}
	
	public int getMinTotalLogins()
	{
		return this.minNumTotalLogins;
	}
	
	public boolean getSaveToMinecraft()
	{
		return this.saveToMinecraft;
	}

	public boolean broadcastWarnings()
	{
		return this.broadcastWarn;
	}

	public boolean broadcastBans()
	{
		return this.broadcastBan;
	}
}
