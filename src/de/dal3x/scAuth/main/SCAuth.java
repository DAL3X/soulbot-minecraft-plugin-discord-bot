package de.dal3x.scAuth.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.dal3x.scAuth.commands.AuthCommand;
import de.dal3x.scAuth.commands.DeAuthCommand;
import de.dal3x.scAuth.commands.GetDiscordTagCommand;
import de.dal3x.scAuth.discord.AuthBot;
import de.dal3x.scAuth.exceptions.GuildNotLoadedException;
import de.dal3x.scAuth.format.Config;
import de.dal3x.scAuth.format.Output;


public class SCAuth extends JavaPlugin {
	
	private AuthBot connection;
	
	public void onEnable() {
		try {
			//Online mode
			this.connection = new AuthBot(this);
			this.getCommand(Config.authcmdName).setExecutor(new AuthCommand(this.connection));
			this.getCommand(Config.deauthcmdName).setExecutor(new DeAuthCommand(this.connection));
			this.getCommand(Config.gettagcmdName).setExecutor(new GetDiscordTagCommand(this.connection));
			System.out.println(Output.prefixConsole + Output.onlineStart);
		} catch (GuildNotLoadedException e) {
			//Offline Mode
			this.getCommand(Config.authcmdName).setExecutor(new AuthCommand());
			this.getCommand(Config.deauthcmdName).setExecutor(new DeAuthCommand());
			this.getCommand(Config.tagLookupPerm).setExecutor(new GetDiscordTagCommand());
			System.out.println(Output.prefixConsole + Output.offlineStart);
		}
	}
	
	public void onDisable() {
		if (this.connection != null) {
			connection.close();
		}
	}
	
}
