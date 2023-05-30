package org.riveros.coder.FileConfig;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.MessageHandler;

public class Messages {
	private static FileConfiguration messages = null;
	private static File messagesFile = null;

	public static void load() {
		messages = getMessages();
		getMessages().options().copyDefaults(true);
		messages.addDefault("NOW_HAS_COINS", "&b{player} &fahora tiene &e{amount} monedas.");
		messages.addDefault("NOW_HAS_WINS", "&b{player} &fahora tiene &e{amount} victorias.");
		messages.addDefault("NOW_HAS_TAGS", "&b{player} &fahora tiene &e{amount} pasadas.");
		messages.addDefault("NOW_HAS_TAGGEDS", "&b{player} &fahora tiene &e{amount} pases.");
		messages.addDefault("AVAILABLE_ARENAS", "Arenas Disponibles: ");
		messages.addDefault("INVALID_ARENA", "&cArena Invalida!");
		messages.addDefault("RELOAD_COMPLETE", "&aPlugin reiniciado");
		messages.addDefault("THERE_WAS_A_RELOAD", "&aPlugin reiniciado");
		messages.addDefault("ARENA_CREATED", "&bNueva arena creada {arena}");
		messages.addDefault("ARENA_DELETED", "&cArena eliminada {arena}!");
		messages.addDefault("UNSPECIFIED_ARENA", "&cEspecifica la arena para eliminar");
		messages.addDefault("STATS", " ");
		messages.addDefault("COINS_CHECK", " ");
		messages.addDefault("NOT_IN_ARENA", "&cDebes estar en una arena para salir de ella");
		messages.addDefault("LEAVE_CURRENT_ARENA", "&cTe encuentras en una Arena");
		messages.addDefault("COMMAND_ERROR", "&cNo puede ejecutar este comando en Juego");
		messages.addDefault("JOINED_FROM_BED", " ");
		messages.addDefault("ARENA_ALREADY_STARTED", "Ya inicio la arena");
		messages.addDefault("FULL_ARENA", "&6Arena llena!");
		messages.addDefault("JOINED_GAME", "&b{player} &fIngreso &7(&e{size}&8/&6{max_players}&7)");
		messages.addDefault("ARENA_NOT_FOUND", "&cArena no disponible");
		messages.addDefault("FORCE_STARTING", "&6Iniciando la arena");
		messages.addDefault("ARENA_STARTED_WHEN_FORCE_START", "&cLa arena ya esta en Juego!");
		messages.addDefault("MINIMUM_REQUIRED_NOT_REACHED", "&cLos jugadores minimos deben ser 2!");
		messages.addDefault("FORCE_ENDING", "todavia no ta");
		messages.addDefault("FORCE_END_KICKED", "todavia no ta");
		messages.addDefault("FORCE_START_ERROR", "La arena ya inicio");
		messages.addDefault("SECONDS_UNTIL_GAME_STARTS", "&fLa partida iniciara en &6{time}s");
		messages.addDefault("SECOND_UNTIL_GAME_STARTS", "&fLa partida iniciara en &b{time}s!");
		messages.addDefault("TNT_RELEASED", "&bLa TNT fue dada al azar!");
		messages.addDefault("EARNED_50_COINS_BONUS", "&6Ganaste una bonificacion!");
		messages.addDefault("LINE_BREAK", "&6#&7------------------&6#");
		messages.addDefault("PLAYER_BLEW_UP", "&cBOOM! El jugador &n{player}&r&c volo por los aires!");
		messages.addDefault("ROUND_ENDED", "&eRonda Terminada!");
		messages.addDefault("PLAYER_IS_IT", "&a{player} &eTiene la bomba!");
		messages.addDefault("ARENA_TEMP_SAVED", "Ubicacion de arena establecida");
		messages.addDefault("LOBBY_TEMP_SAVED", "Ubicacion de lobby establecida");
		messages.addDefault("SPECTATOR_TEMP_SAVED", "Ubicacion de spect establecida");
		messages.addDefault("MISSING_LOBBY", "Falta ubicacion de lobby");
		messages.addDefault("MISSING_ARENA", "Falta ubicacion de arena");
		messages.addDefault("MISSING_SPECTATOR", "&cFalta la ubicacion de espectador.");
		messages.addDefault("WIN_MESSAGE", "&b{player} &aGano la Partida!");
		messages.addDefault("INVALID_NUMBER", "Numero Invalido.");
		save();
	}

	public static void reload() {
		if (messagesFile == null) {
			messagesFile = new File("plugins/ZC-TNTTAG/messages.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
	}

	public static FileConfiguration getMessages() {
		if (messages == null) {
			reload();
		}
		return messages;
	}

	public static void save() {
		if ((messages == null) || (messagesFile == null)) {
			return;
		}
		try {
			messages.save(messagesFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save configFile to " + messagesFile, ex);
		}
	}

	public static String getMessage(Message message) {
		return MessageHandler.getMessage(message);
	}
}
