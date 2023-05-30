package org.riveros.coder.Utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import org.riveros.coder.FileConfig.Messages;

public class MessageHandler {

	public static String getMessage(Message message) {
		String tempMessage = "";
		switch (message) {
			case add:
				tempMessage = getConfigMessage("ADD_DESC");
				break;
			case checkStats:
				tempMessage = getConfigMessage("CHECK_STATS_DESC");
				break;
			case coins:
				tempMessage = getConfigMessage("CHECK_COINS_DESC");
				break;
			case createArena:
				tempMessage = getConfigMessage("CREATE_ARENA_DESC");
				break;
			case deleteArena:
				tempMessage = getConfigMessage("DELETE_ARENA_DESC");
				break;
			case forceStart:
				tempMessage = getConfigMessage("FORCE_START_DESC");
				break;
			case forceEnd:
				tempMessage = getConfigMessage("FORCE_END_DESC");
				break;
			case invalidNumber:
				tempMessage = getConfigMessage("INVALID_NUMBER");
				break;
			case join:
				tempMessage = getConfigMessage("JOIN_DESC");
				break;
			case leave:
				tempMessage = getConfigMessage("LEAVE_DESC");
				break;
			case listArenas:
				tempMessage = getConfigMessage("LIST_ARENAS_DESC");
				break;
			case nowHasCoins:
				tempMessage = getConfigMessage("NOW_HAS_COINS");
				break;
			case nowHasTaggeds:
				tempMessage = getConfigMessage("NOW_HAS_TAGGED");
				break;
			case nowHasTags:
				tempMessage = getConfigMessage("NOW_HAS_TAGS");
				break;
			case nowHasWins:
				tempMessage = getConfigMessage("NOW_HAS_WINS");
				break;
			case reload:
				tempMessage = getConfigMessage("RELOAD_DESC");
				break;
			case remove:
				tempMessage = getConfigMessage("REMOVE_DESC");
				break;
			case resetStats:
				tempMessage = getConfigMessage("RESET_STATS_DESC");
				break;
			case setArenaPoint:
				tempMessage = getConfigMessage("SET_ARENA_POINT_DESC");
				break;
			case setLobby:
				tempMessage = getConfigMessage("SET_LOBBY_POINT_DESC");
				break;
			case setSpectatorsPoint:
				tempMessage = getConfigMessage("SET_SPECTATORS_POINT_DESC");
				break;
			case transfer:
				tempMessage = getConfigMessage("TRANSFER_DESC");
				break;
			case update:
				tempMessage = getConfigMessage("UPDATE_DESC");
				break;
			case availableArenas:
				tempMessage = getConfigMessage("AVAILABLE_ARENAS");
				break;
			case invalidArena:
				tempMessage = getConfigMessage("INVALID_ARENA");
				break;
			case reloadComplete:
				tempMessage = getConfigMessage("RELOAD_COMPLETE");
				break;
			case thereWasReload:
				tempMessage = getConfigMessage("THERE_WAS_A_RELOAD");
				break;
			case arenaCreated:
				tempMessage = getConfigMessage("ARENA_CREATED");
				break;
			case arenaDeleted:
				tempMessage = getConfigMessage("ARENA_DELETED");
				break;
			case unspecifiedArena:
				tempMessage = getConfigMessage("UNSPECIFIED_ARENA");
				break;
			case confirmationMessage:
				tempMessage = getConfigMessage("CONFIRMATION_MESSAGE");
				break;
			case stats:
				tempMessage = getConfigMessage("STATS");
				break;
			case checkCoins:
				tempMessage = getConfigMessage("CHECK_COINS");
				break;
			case notInArena:
				tempMessage = getConfigMessage("NOT_IN_ARENA");
				break;
			case leaveCurrentArena:
				tempMessage = getConfigMessage("LEAVE_CURRENT_ARENA");
				break;
			case commandError:
				tempMessage = getConfigMessage("COMMAND_ERROR");
				break;
			case joinedFromBed:
				tempMessage = getConfigMessage("JOINED_FROM_BED");
				break;
			case arenaAlreadyStarted:
				tempMessage = getConfigMessage("ARENA_ALREADY_STARTED");
				break;
			case fullArena:
				tempMessage = getConfigMessage("FULL_ARENA");
				break;
			case joinedGame:
				tempMessage = getConfigMessage("JOINED_GAME");
				break;
			case arenaNotFound:
				tempMessage = getConfigMessage("ARENA_NOT_FOUND");
				break;
			case arenaHasAlreadyStarted:
				tempMessage = getConfigMessage("FORCE_STARTING");
				break;
			case forceEndKicked:
				tempMessage = getConfigMessage("ARENA_STARTED_WHEN_FORCE_START");
				break;
			case forceEnding:
				tempMessage = getConfigMessage("MINIMUM_REQUIRED_NOT_REACHED");
				break;
			case forceStartAlreadyStarted:
				tempMessage = getConfigMessage("FORCE_ENDING");
				break;
			case forceStarting:
				tempMessage = getConfigMessage("FORCE_STARTING");
				break;
			case minTwoPlayers:
				tempMessage = getConfigMessage("FORCE_START_ERROR");
				break;
			case secondCountdown:
				tempMessage = getConfigMessage("SECOND_UNTIL_GAME_STARTS");
				break;
			case secondsCountdown:
				tempMessage = getConfigMessage("SECONDS_UNTIL_GAME_STARTS");
				break;
			case lineBreak:
				tempMessage = getConfigMessage("LINE_BREAK");
				break;
			case arenaMissing:
				tempMessage = getConfigMessage("MISSING_ARENA");
				break;
			case arenaTempSaved:
				tempMessage = getConfigMessage("ARENA_TEMP_SAVED");
				break;
			case lobbyMissing:
				tempMessage = getConfigMessage("MISSING_LOBBY");
				break;
			case lobbyTempSaved:
				tempMessage = getConfigMessage("LOBBY_TEMP_SAVED");
				break;
			case spectatorsMissing:
				tempMessage = getConfigMessage("MISSING_SPECTATOR");
				break;
			case spectatorsTempSaved:
				tempMessage = getConfigMessage("SPECTATOR_TEMP_SAVED");
				break;
			case winMessage:
				tempMessage = getConfigMessage("WIN_MESSAGE");
				break;
			case TNTReleased:
				tempMessage = getConfigMessage("TNT_RELEASED");
				break;
			case coinsBonus:
				tempMessage = getConfigMessage("EARNED_50_COINS_BONUS");
				break;
			case playerBlewUp:
				tempMessage = getConfigMessage("PLAYER_BLEW_UP");
				break;
			case playerIsIt:
				tempMessage = getConfigMessage("PLAYER_IS_IT");
				break;
			case roundEnded:
				tempMessage = getConfigMessage("ROUND_ENDED");
				break;
			default:
				break;
		}
		return tempMessage;
	}

	private static String getConfigMessage(String string) {
		if (Messages.getMessages().getString(string) != null) {
			return Messages.getMessages().getString(string).replace("&", "§").replace("%newline", "\n");
		}
		Bukkit.getServer().getLogger().log(Level.SEVERE, "{0} Could not be found! If you have edited the messages.yml check that string. If nor, contact the plugin administrator.", new Object[] { string });
		return null;
	}
}
