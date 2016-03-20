package me.Alessandro.Roulette;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceBet implements Listener{
	
	Countdown c;
	
	@EventHandler
	public void onPlaceBet(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getInventory().getItemInHand().getType() == Material.GOLD_NUGGET)
		{
			// Zahlen 0-36
			for (int i = 0; i < c.countDownBlocks.size(); i++) {
				if (e.getClickedBlock().getType() == c.countDownBlocks.get(i).getType()) {
					c.bettingRecord.put(p.getUniqueId(), 36-i);
				}
			}
			
			ItemStack red = new ItemStack(Material.WOOL, 1, (short)14);
			ItemStack black = new ItemStack(Material.WOOL, 1, (short)15);
			
			if (e.getClickedBlock().getType() == red.getType())
			{
				c.bettingRecord.put(p.getUniqueId(), 40);
			}
			else if(e.getClickedBlock().getType() == black.getType())
			{
				c.bettingRecord.put(p.getUniqueId(), 41);
			}
			
			c.amountRecord.put(p.getUniqueId(), c.amountRecord.get(p.getUniqueId()) + 1);
			
			// !!! Currently winners for all fields are calculated, even though you cannot bet on sectors yet !!!
		}
	}
}
