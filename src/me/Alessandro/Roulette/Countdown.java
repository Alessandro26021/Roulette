package me.Alessandro.Roulette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.vault.economy.EconomyResponse;

public class Countdown implements Listener{
	
	int winningNumber;
	int winningSector;
	int colour;
	
	int count;
	boolean isRunning;
	Main plugin;
	Random r;
	
	ArrayList<ItemStack> countDownBlocks = new ArrayList<ItemStack>();
	
	// What did the player bet on? 0-36 for numbers and 37-39 for sections and 40-41 for colour (40 = RED, 41 = BLACK)
	HashMap<UUID, Integer> bettingRecord = new HashMap<UUID, Integer>();
	
	// How much did a player bet on a certain field? !! For obvious reasons both of the HashMaps always have to be the same size !!
	HashMap<UUID, Integer> amountRecord = new HashMap<UUID, Integer>();
	
	public Countdown(Main plugin) {
		this.plugin = plugin;
		r = new Random();
		this.setup();
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public boolean isRoundFinished()
	{
		if (count <= 0) {
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(Bukkit.getServer().getOnlinePlayers().length == 1)
		{
			this.startCountdown();
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		if(Bukkit.getServer().getOnlinePlayers().length == 0)
		{
			this.stopCountdown();
		}
	}
	
	@SuppressWarnings("deprecation")
	protected void startCountdown() {
		this.count = 20;
		this.winningNumber = -1;
		this.winningSector = -1;
		this.colour = -1;
		this.bettingRecord.clear();
		this.amountRecord.clear();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin.getPlugin(), new Timer(plugin), 40L, 20L);
		Bukkit.broadcastMessage(ChatColor.AQUA + "[Roulette] " + ChatColor.YELLOW + "A new round of roulette has started! Place your bets!");

	}
	
	protected void stopCountdown() {
		
		isRunning = false;
	}
	
	private void setup()
	{
		// Don't need this part since the count is max: 20
		// Blocks 36-21
		this.countDownBlocks.add(new ItemStack(Material.EMERALD_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.WOOD, 1, (short)3));
		this.countDownBlocks.add(new ItemStack(Material.WOOD, 1, (short)5));
		this.countDownBlocks.add(new ItemStack(Material.WOOD, 1, (short)4));
		this.countDownBlocks.add(new ItemStack(Material.ENDER_STONE, 1));
		this.countDownBlocks.add(new ItemStack(Material.PRISMARINE, 1, (short)2));
		this.countDownBlocks.add(new ItemStack(Material.PRISMARINE, 1, (short)1));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)4));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)8));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)14));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)10));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)6));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)1));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)2));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)5));
		//Blocks 20-0
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)3));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)13));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)7));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)9));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)12));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)11));
		this.countDownBlocks.add(new ItemStack(Material.STAINED_GLASS, 1, (short)15));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)1));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)2));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)3));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)4));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)6));
		this.countDownBlocks.add(new ItemStack(Material.NOTE_BLOCK, 1));
		this.countDownBlocks.add(new ItemStack(Material.STONE, 1, (short)5));
		this.countDownBlocks.add(new ItemStack(Material.COAL_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.IRON_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.GOLD_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.LAPIS_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.DIAMOND_ORE, 1));
		this.countDownBlocks.add(new ItemStack(Material.MOSSY_COBBLESTONE, 1));
		this.countDownBlocks.add(new ItemStack(Material.COAL_BLOCK, 1));
		
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void payout(HashMap<UUID, Integer> winners, HashMap<UUID, Integer> amountRecord)  // Pays the money to everybody who bet!
	{		
		for (Entry<UUID, Integer> entry : winners.entrySet()) {
		    UUID key = entry.getKey();
		    Integer value = entry.getValue();
		    Player player = Bukkit.getPlayer(key);
		    
		    if(value == 2)
		    {
		    	EconomyResponse r = plugin.econ.depositPlayer(player, value * amountRecord.get(key));
		    }
		    else if(value == 3)
		    {
		    	EconomyResponse r = plugin.econ.depositPlayer(player, value * amountRecord.get(key));
		    }
		    else if(value == 36)
		    {
		    	EconomyResponse r = plugin.econ.depositPlayer(player, value * amountRecord.get(key));
		    }
		}
	}

	public void calculateResults() {
		// Calculate winning number
		winningNumber = r.nextInt(37);
		
		if(winningNumber >= 1 && winningNumber <= 12)
		{
			winningSector = 37;		// Sector 1: 1 - 12
		}
		else if(winningNumber >= 13 && winningNumber <= 24)
		{
			winningSector = 38;		// Sector 2: 13 - 24
		}
		else if(winningNumber >= 25 && winningNumber <= 36)
		{
			winningSector = 39;		// Sector 3: 25 - 36
		}
		
		switch (winningNumber) {
		case 1:
			colour = 40;
			break;
		case 2:
			colour = 41;
			break;
		case 3:
			colour = 40;
			break;
		case 4:
			colour = 41;
			break;
		case 5:
			colour = 40;
			break;
		case 6:
			colour = 41;
			break;
		case 7:
			colour = 40;
			break;
		case 8:
			colour = 41;
			break;
		case 9:
			colour = 40;
			break;
		case 10:
			colour = 41;
			break;
		case 11:
			colour = 41;
			break;
		case 12:
			colour = 40;
			break;
		case 13:
			colour = 41;
			break;
		case 14:
			colour = 40;
			break;
		case 15:
			colour = 41;
			break;
		case 16:
			colour = 40;
			break;
		case 17:
			colour = 41;
			break;
		case 18:
			colour = 40;
			break;
		case 19:
			colour = 40;
			break;
		case 20:
			colour = 41;
			break;
		case 21:
			colour = 40;
			break;
		case 22:
			colour = 41;
			break;
		case 23:
			colour = 40;
			break;
		case 24:
			colour = 41;
			break;
		case 25:
			colour = 40;
			break;
		case 26:
			colour = 41;
			break;
		case 27:
			colour = 40;
			break;
		case 28:
			colour = 41;
			break;
		case 29:
			colour = 41;
			break;
		case 30:
			colour = 40;
			break;
		case 31:
			colour = 41;
			break;
		case 32:
			colour = 40;
			break;
		case 33:
			colour = 41;
			break;
		case 34:
			colour = 40;
			break;
		case 35:
			colour = 41;
			break;
		case 36:
			colour = 40;
			break;
			
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public HashMap<UUID, Integer> checkWinners() {
		
		// The player that won and the payout multiplier!
		HashMap<UUID, Integer> winners = new HashMap<UUID, Integer>();
		
		Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
		
		for (int j = 0; j < onlinePlayers.length; j++) {
			if (bettingRecord.get(onlinePlayers[j].getUniqueId()) == winningNumber)
			{
				winners.put(onlinePlayers[j].getUniqueId(), 36);
			}
			else if (bettingRecord.get(onlinePlayers[j].getUniqueId()) == winningSector)
			{
				winners.put(onlinePlayers[j].getUniqueId(), 3);
			}
			else if (bettingRecord.get(onlinePlayers[j].getUniqueId()) == colour)
			{
				winners.put(onlinePlayers[j].getUniqueId(), 2);
			}/* Don't need the following since the bet amount will be subtracted from balance when placing the bet anyways!
			else
			{
				winners.put(onlinePlayers[j].getUniqueId(), 1);
			}*/
		}
		return winners;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getKeyFromValue(Map hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
	
}
