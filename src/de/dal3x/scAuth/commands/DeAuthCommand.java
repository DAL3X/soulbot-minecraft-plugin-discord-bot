package de.dal3x.scAuth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.dal3x.scAuth.discord.AuthBot;
import de.dal3x.scAuth.exceptions.PlayerNotAuthentificatedException;
import de.dal3x.scAuth.format.Output;

public class DeAuthCommand implements CommandExecutor {

	private AuthBot connection;

	public DeAuthCommand() {
		this.connection = null;
		// Command just returns errors when used now
	}

	public DeAuthCommand(AuthBot connection) {
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
			sender.sendMessage(Output.prefix + Output.deauthUsage);
			return true;
		}
		try {
			connection.deauthentificate(p);
		} catch (PlayerNotAuthentificatedException e) {
			sender.sendMessage(Output.prefix + Output.notAuth);
		}
		return true;
	}
}
