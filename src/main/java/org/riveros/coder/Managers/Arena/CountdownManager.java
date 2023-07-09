package org.riveros.coder.Managers.Arena;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.riveros.coder.Main.TNTTag;
import org.riveros.coder.FileConfig.Config;
import org.riveros.coder.FileConfig.Messages;
import org.riveros.coder.Managers.Arena.Arena;
import org.riveros.coder.Managers.InventoryManager;
import org.riveros.coder.Utils.Message;
import org.riveros.coder.Utils.TNTUtils;
import org.riveros.coder.Utils.Utils;

public class CountdownManager {

	private TNTTag plugin;
	private Arena arena;
	private List<Integer> timesToBroadcast;

	public CountdownManager(TNTTag plugin, Arena arena) {
		this.plugin = plugin;
		this.arena = arena;
		this.timesToBroadcast = plugin.getFileManager().getConfig().getIntegerList("BroadcastTimes");
	}

	public void startGame(int seconds1) {
		if (!arena.runningCountdown() && arena.getPlayers().size() >= arena.getMinPlayers()) {
			arena.setSeconds(seconds1);
			arena.setRunningCountdown(Boolean.valueOf(true));
			arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				public void run() {
					if (timesToBroadcast.contains(arena.getSeconds()))
						if(arena.getSeconds() <= 5){
							plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.secondCountdown).replace("{time}", (new StringBuilder(String.valueOf(arena.getSeconds()))).toString()), arena);
							plugin.getMessageManager().sendInGamePlayersTitle("&6&l" + (new StringBuilder(String.valueOf(arena.getSeconds()))) + "&6&L!", "&fPreparate...", arena);
						} else if (arena.getSeconds() == 1) {
							plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.secondCountdown).replace("{time}", (new StringBuilder(String.valueOf(arena.getSeconds()))).toString()), arena);
						} else {
							plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.secondsCountdown).replace("{time}", (new StringBuilder(String.valueOf(arena.getSeconds()))).toString()), arena);
						}
					for (Player player : arena.getPlayers()) {
						arena.setBoard(player, arena.getSeconds());
						player.setLevel(arena.getSeconds());
					}

					if (arena.getSeconds() == 0) {
						Bukkit.getScheduler().cancelTask(arena.getTaskID());
						arena.setInGame(true);
						Location loc = arena.getArenaLocation();
						for (Player player : arena.getPlayers()) {
							player.teleport(loc);
						}
						pickRandomTNT();
						startRound();
					}
					int seconds = arena.getSeconds();
					arena.setSeconds(seconds - 1);
				}
			}, 20L, 20L));
		}
	}

	protected void startRound() {
		arena.setSeconds(arena.getPlayers().size() <= 6 ? 30 : 50);
		arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

			public void run() {
				for (Player player : arena.getPlayers()) {
					arena.setBoard(player, arena.getSeconds());
					player.setLevel(arena.getSeconds());
				}

				if (arena.getSeconds() == 0) {
					Bukkit.getScheduler().cancelTask(arena.getTaskID());
					if (arena.getPlayers().size() == 2) {
						Player p = arena.getTNTPlayers().get(0);
						blowUpTNTs();
						plugin.getMessageManager().sendWinMessage(p, arena.getAlivePlayers().get(0).getName(), arena);

						arena.setRunningCountdown(false);
						for (Player player : arena.getPlayers()) {

							int wins = plugin.getFileManager().getPlayerData().getInt(player.getName() + ".wins");
							plugin.getFileManager().getPlayerData().set(player.getName() + ".wins", Integer.valueOf(wins + 1));

							int money = plugin.getFileManager().getPlayerData().getInt(player.getName() + ".money");
							plugin.getFileManager().getPlayerData().set(player.getName() + ".money", Integer.valueOf(money + 50));

							if(plugin.getConfig().getBoolean("givebonus") && plugin.getEconomy() != null) {
								plugin.getEconomy().depositPlayer(player.getName(), plugin.getConfig().getInt("bonus.Win"));
							}
							
							plugin.getMessageManager().sendNoPrefixMessage(player, Messages.getMessage(Message.coinsBonus));
//							plugin.getMessageManager().sendNoPrefixMessage(player, Messages.getMessage(Message.lineBreak));
							Config.executeGameWinCommand(player);
							arena.getAlivePlayers().remove(player.getName());

							plugin.getArenaManager().removePlayer(player);
							if (arena.getPlayers().size() == 0) {
								arena.setInGame(false);
								arena.setState(ArenaState.FINISHING);

								return;
							}
						}

					} else if (arena.getPlayers().size() <= 6) {
						blowUpTNTs();
						arena.setRound(arena.getRound() + 1);
						startDelayedRound();
					} else {
						blowUpTNTs();
						arena.setRound(arena.getRound() + 1);
						startDelayedRound();
					}
				}
				int seconds = arena.getSeconds();
				arena.setSeconds(seconds - 1);
			}
		}, 20L, 20L));
	}

	protected void startDelayedRound() {
		arena.setSeconds(5);
		arena.setTaskID(Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run() {
				if (arena.getSeconds() == 0) {
					Bukkit.getScheduler().cancelTask(arena.getTaskID());
					startRound();
					Location loc = arena.getArenaLocation();
					for (Player player : arena.getPlayers()) {
						player.teleport(loc);
						player.setPlayerListName(Utils.CCS("&7" + player.getName()));
					}
					plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.TNTReleased), arena);

					pickRandomTNT();
					for (Player player : arena.getPlayers()) {
						Utils.CCC(player, "");
						Utils.CCC(player, "&f&l¡Ronda " + arena.getRound() + " iniciada!");
						Utils.CCC(player, "");
						Utils.CCC(player, "&eLa TNT fue entregada a:");
						for(Player t : arena.getTNTPlayers()){
							Utils.CCC(player, "&c[T] " + t.getName());
						}
						Utils.CCC(player, "");
					}
				}
				int seconds = arena.getSeconds();
				arena.setSeconds(seconds - 1);
			}
		}, 20L, 20L));
	}

	public void blowUpTNTs() {
		Iterator<Player> it = arena.getTNTPlayers().iterator();
		while(it.hasNext()) {
			Player player = it.next();
			World world = player.getWorld();

			world.createExplosion(player.getLocation(), 0.0F);

			plugin.getMessageManager().sendInGamePlayersTitle("&c&lBOOM!", "&cLos jugadores con la bomba explotaron...", arena);
			plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.playerBlewUp).replace("{player}", player.getName()), arena);

			TNTUtils.update(player, false);

			it.remove();
			arena.getPlayers().remove(player);
			arena.removeBoard(player);
			InventoryManager.restoreInventory(player);
		}
		finishBlowingUp();
	}

	private void finishBlowingUp() {
		plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.roundEnded), arena);
		for (Player player : arena.getPlayers()) {
			Config.executeRoundWinCommand(player);
			int moneyAmount = plugin.getFileManager().getConfig().getInt("money.RoundSurvive");
			player.sendMessage(ChatColor.GOLD + "+" + moneyAmount + " coins!");
			/*int money = plugin.getFileManager().getPlayerData().getInt(player.getName() + ".money");
			plugin.getFileManager().getPlayerData().set(player.getName() + ".money", Integer.valueOf(money + 2));*/
			if(plugin.getConfig().getBoolean("givebonus") && plugin.getEconomy() != null) {
				plugin.getEconomy().depositPlayer(player.getName(), moneyAmount);
			}
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
			plugin.getFileManager().saveConfig();
		}

	}

	public void pickRandomTNT() {
		int playerThatWillBePicked = (int) Math.ceil(arena.getPlayers().size() >= 6 ? arena.getPlayers().size() / 2 : 1);
		while (playerThatWillBePicked > 0) {
			pickPlayers();
			playerThatWillBePicked--;
		}

		for (Player player : arena.getTNTPlayers()) {
			plugin.getMessageManager().sendInGamePlayersMessage(Messages.getMessage(Message.playerIsIt).replace("{player}", player.getName()), arena);
			if(!arena.getTNTPlayers().contains(player)) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 3));
			}
		}
	}

	private void pickPlayers() {
		Random random = new Random();

		Player[] players = new Player[arena.getAlivePlayers().size()];
		int i = 0;
		for (Player player : arena.getAlivePlayers()) {
			players[i] = player;
			i++;
		}
		int randomInt = 0;
		randomInt = random.nextInt(players.length);
		arena.getTNTPlayers().add(players[randomInt]);
		arena.getAlivePlayers().remove(players[randomInt]);
		if (players[randomInt] != null) {
			Player player = players[randomInt];
			TNTUtils.update(player, true);
		}
		players = null;
	}

	public void cancelTask() {
		Bukkit.getScheduler().cancelTask(arena.getTaskID());
		arena.setRunningCountdown(false);
		arena.setInGame(false);
	}
}
