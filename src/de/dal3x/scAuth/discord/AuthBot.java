package de.dal3x.scAuth.discord;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.dal3x.scAuth.exceptions.GuildNotLoadedException;
import de.dal3x.scAuth.exceptions.PlayerAlreadyAuthentificatedException;
import de.dal3x.scAuth.exceptions.PlayerNotAuthentificatedException;
import de.dal3x.scAuth.format.Config;
import de.dal3x.scAuth.format.Output;
import de.dal3x.scAuth.handler.FileHandler;
import de.dal3x.scAuth.main.SCAuth;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class AuthBot {

	private JDA jda;
	private Guild soulcraft;
	private FileHandler fileHandler;
	SCAuth plugin;
	private HashMap<String, Player> authqueue;
	private HashMap<String, Player> deauthqueue;
	private List<Player> blocked;

	public AuthBot(SCAuth plugin) throws GuildNotLoadedException {
		this.plugin = plugin;
		this.authqueue = new HashMap<>();
		this.deauthqueue = new HashMap<>();
		this.blocked = new LinkedList<Player>();
		this.fileHandler = new FileHandler();
		try {
			this.jda = new JDABuilder(Config.token).build();
			jda.awaitReady();
			this.soulcraft = jda.getGuildById(Config.soulcraftID);
			AnnotatedEventManager eventManager = new AnnotatedEventManager();
			eventManager.register(this);
			jda.setEventManager(eventManager);
		} catch (LoginException | InterruptedException e) {
			System.out.println(Output.prefixConsole + e.getStackTrace());
		}
		if (this.soulcraft == null) {
			throw new GuildNotLoadedException();
		}
	}

	public void close() {
		jda.shutdown();
	}

	public void authentificate(Player p) throws PlayerAlreadyAuthentificatedException {
		if (fileHandler.hasAFile(p.getName())) {
			throw new PlayerAlreadyAuthentificatedException();
		}
		if (this.blocked.contains(p)) {
			return;
		}
		this.blocked.add(p);
		String pin = Integer.toString(new Random().nextInt(89999999) + 10000000);
		p.sendMessage(Output.pinGen + pin);
		p.sendMessage(Output.prefix + Output.authDiscord);
		this.authqueue.put(pin, p);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (authqueue.containsKey(pin) && blocked.contains(p)) {
					authqueue.remove(pin);
					blocked.remove(p);
					p.sendMessage(Output.prefix + Output.authRetired);
				}
			}
		}, 2000L);
	}

	public void deauthentificate(Player p) throws PlayerNotAuthentificatedException {
		if (!fileHandler.hasAFile(p.getName())) {
			throw new PlayerNotAuthentificatedException();
		}
		if (this.blocked.contains(p)) {
			return;
		}
		this.blocked.add(p);
		String pin = Integer.toString(new Random().nextInt(89999999) + 10000000);
		p.sendMessage(Output.pinGen + pin);
		p.sendMessage(Output.prefix + Output.deauthDiscord);
		this.deauthqueue.put(pin, p);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (deauthqueue.containsKey(pin) && blocked.contains(p)) {
					deauthqueue.remove(pin);
					blocked.remove(p);
					p.sendMessage(Output.prefix + Output.deauthRetired);
				}
			}
		}, 2000L);
	}

	public String getDiscordTag(Player p) throws PlayerNotAuthentificatedException {
		if (!fileHandler.hasAFile(p.getName())) {
			throw new PlayerNotAuthentificatedException();
		}
		return fileHandler.getDiscordTagFromFile(p.getName());
	}

	@SubscribeEvent
	public void onPrivateMessage(PrivateMessageReceivedEvent event) {
		String userTag = event.getAuthor().getAsTag();
		PrivateChannel channel = event.getChannel();
		String pin = event.getMessage().getContentStripped();
		// auth
		if (this.authqueue.containsKey(pin)) {
			Member member = soulcraft.getMemberByTag(userTag);
			if (member == null) {
				channel.sendMessage(Output.joinDC);
				return;
			}
			for (Role role : soulcraft.getRoles()) {
				if (role.getName().equals(Config.roleName)) {
					try {
						fileHandler.createFileForPlayer(this.authqueue.get(pin).getName(), userTag);
					} catch (IOException e) {
						return;
					}
					soulcraft.addRoleToMember(member, role).queue();
					this.authqueue.get(pin).sendMessage(Output.prefix + Output.successAuth);
					this.blocked.remove(authqueue.get(pin));
					this.authqueue.remove(pin);
					channel.sendMessage(Output.dcsuccessAuth).queue();
					return;
				}
			}
		}
		// deauth
		else if (this.deauthqueue.containsKey(pin)) {
			Member member = soulcraft.getMemberByTag(userTag);
			if (member == null) {
				channel.sendMessage(Output.joinDC);
				return;
			}
			for (Role role : soulcraft.getRoles()) {
				if (role.getName().equals(Config.roleName)) {
					fileHandler.deleteFile(this.deauthqueue.get(pin).getName());
					soulcraft.removeRoleFromMember(member, role).queue();
					this.deauthqueue.get(pin).sendMessage(Output.prefix + Output.successDeAuth);
					this.blocked.remove(deauthqueue.get(pin));
					this.deauthqueue.remove(pin);
					channel.sendMessage(Output.dcsuccessDeAuth).queue();
					return;
				}
			}
		}
	}

}