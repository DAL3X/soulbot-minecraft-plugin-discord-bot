package de.dal3x.scAuth.format;

public class Output {

	public static final String prefixConsole = "[SoulcraftAuth] ";
	public static final String onlineStart = "Der Discordserver wurde gefunden. Plugin startet im online Modus.";
	public static final String offlineStart = "Der Discordserver konnte nicht gefunden werden. Plugin startet im offline Modus.";
	
	public static final String prefix = "§8[§5DC-Verify§8]§f ";
	
	public static final String unvalidTag = "§cDieser Name ist ungültig! Beispiel: DiscordName#1234";
	public static final String alreadyAuth = "§cDu bist bereits mit diesem Account registriert.";
	public static final String notAuth = "§cDu musst dich erst registrieren.";
	public static final String playerNotFound = "§cEs wurde kein Discord-Account mit diesem Benutzernamen gefunden. Beispiel: DiscordName#1234";
	public static final String error = "§cEin interner Fehler ist aufgetreten, bitte versuche es erneut.";
	public static final String offline = "§cDer Discordbot ist im Moment nicht online. Versuche es später erneut.";
	public static final String noPerm = "§cDu hast nicht die Rechte, um diesen Command zu nutzen.";
	public static final String playerNotAuth = "§cDieser Spieler ist nicht registriert.";
	
	public static final String authUsage = "§eNutze /"+ Config.authcmdName + "  , um dich mit deinem Discord-Account zu registrieren.";
	public static final String deauthUsage = "§eNutze /"+ Config.deauthcmdName + " , um die Registrierung deines Discord-Accounts aufzuheben.";
	public static final String getTagUsage = "§eNutze /" + Config.gettagcmdName + " [spielername]";
	
	public static final String successAuth = "§aDu hast dich erfolgreich registriert! Du wirst im Discord nun als Spieler angezeigt.";
	public static final String successDeAuth = "§aDu hast dich erfolgreich abgemeldet! Du wirst im Discord nun nicht mehr als Spieler angezeigt.";
	public static final String dcsuccessAuth = "Du hast dich erfolgreich registriert! Du wirst im Discord nun als Spieler angezeigt.";
	public static final String dcsuccessDeAuth = "Du hast dich erfolgreich abgemeldet! Du wirst im Discord nun nicht mehr als Spieler angezeigt.";
	public static final String successGetTag = "§aDiscord Benutzername des Spielers: ";

	public static final String authDiscord = " §eSchreibe den generierten Pin unverändert an " + Config.botName + " auf dem Discordserver um die Verifizierung abzuschließen.";
	public static final String deauthDiscord = "§eSchreibe den generierten Pin unverändert an " + Config.botName + " auf dem Discordserver um die Verifizierung aufzuheben.";
	
	public static final String authRetired = "§cDein Verifizierungs-Pin ist ausgelaufen, nutze den verifizierungs-Command bitte erneut.";
	public static final String deauthRetired = "§cDein Entverifizierungs-Pin ist ausgelaufen, nutze den entverifizierungs-Command bitte erneut.";
	
	public static final String pinGen = "§eDein generierter Pin ist:§d ";
	
	public static final String joinDC = "Du kannst dich nicht verifizieren, ohne im Soulcraft Discord Mitgleid zu sein.";
}
