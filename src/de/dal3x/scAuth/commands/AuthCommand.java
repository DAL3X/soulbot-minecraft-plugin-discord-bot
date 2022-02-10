package de.dal3x.scAuth.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.dal3x.scAuth.discord.AuthBot;
import de.dal3x.scAuth.exceptions.PlayerAlreadyAuthentificatedException;
import de.dal3x.scAuth.format.Output;

public class AuthCommand implements CommandExecutor {

	private AuthBot connection;

	public AuthCommand() {
		this.connection = null;
		// Command just returns errors when used now
	}

	public AuthCommand(AuthBot connection) {
		this.connection = connection;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (this.connection == null) {
			sender.sendMessage(Output.prefix + Output.offline);
			return true;
		}
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (args.length > 0) {
			sender.sendMessage(Output.prefix + Output.authUsage);
			return true;
		}
		try {
			this.connection.authentificate(p);
		} catch (PlayerAlreadyAuthentificatedException e) {
			sender.sendMessage(Output.prefix + Output.alreadyAuth);
		}
		return true;
	}

}
