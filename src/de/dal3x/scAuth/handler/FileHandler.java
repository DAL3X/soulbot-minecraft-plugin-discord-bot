package de.dal3x.scAuth.handler;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.dal3x.scAuth.format.Config;

public class FileHandler {

	public void createFileForPlayer(String name, String discordTag) throws IOException {
		File authFile = getFile(name);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(authFile);
		cfg.set(Config.ymlSelect, discordTag);
		cfg.save(authFile);
	}

	public boolean hasAFile(String name) {
		File authFile = getFile(name);
		if (authFile.exists()) {
			return true;
		}
		return false;
	}
	
	public void deleteFile(String name) {
		File authFile = getFile(name);
		if(authFile.exists()) {
			authFile.delete();
		}
	}

	private File getFile(String name) {
		return new File(Config.ymlPath, name + ".yml");
	}
	
	public String getDiscordTagFromFile(String name) {
		File authFile = getFile(name);
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(authFile);
		return (String) cfg.get(Config.ymlSelect);
	}
}
