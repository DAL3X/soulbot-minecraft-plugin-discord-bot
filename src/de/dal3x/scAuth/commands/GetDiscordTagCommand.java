package de.dal3x.scAuth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.dal3x.scAuth.discord.AuthBot;
import de.dal3x.scAuth.exceptions.PlayerNotAuthentificatedException;
import de.dal3x.scAuth.format.Config;
import de.dal3x.scAuth.format.Output;

public class GetDiscordTagCommand implements CommandExecutor {

	private AuthBot connection;

	public GetDiscordTagCommand() {
		this.connection = null;
		// Command just returns errors when used now
	}

	public GetDiscordTagCommand(AuthBot connection) {
		this.connection = connection;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission(Config.tagLookupPerm)) {
			sender.sendMessage(Output.prefix + Output.noPerm);
			return true;
		}
		if (this.connection == null) {
			sender.sendMessage(Output.prefix + Output.offline);
			return true;
		}
		if (!(sender instanceof Player)) {
			return true;
		}
		if (args.length != 1) {
			sender.sendMessage(Output.prefix + Output.getTagUsage);
			return true;
		}
		Player target = Bukkit.getServer().getPlayer(args[0]);
		if(target == null) {
			sender.sendMessage(Output.prefix + Output.playerNotFound);
			return true;
		}
		try {
			sender.sendMessage(Output.prefix + Output.successGetTag + connection.getDiscordTag(target));
		} catch (PlayerNotAuthentificatedException e) {
			sender.sendMessage(Output.prefix + Output.playerNotAuth);
		}
		return true;
	}

}
