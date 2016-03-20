package me.Alessandro.Roulette;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
	
	Countdown c;
	Location l;
	Main plugin;
	
	public Timer(Main plugin) {
		this.plugin = plugin;
		
		l = new Location(Bukkit.getWorld("world"), 922, 115, 13);
		c = new Countdown(this.plugin);
	}
	
	@Override
	public void run() {
		
		System.out.println(c.getCount());
		
		if(c.getCount() > 0)
		{
			//Change blocks here / maybe countdown also with xp bar!
			
			Bukkit.getWorld("world").getBlockAt(l).setType(c.countDownBlocks.get(20 - c.getCount()).getType());			
			c.setCount(c.getCount() - 1);
		}
		else
		{
			c.calculateResults();
			c.payout(c.checkWinners(), c.amountRecord);
			if (c.isRunning) {
				c.startCountdown();
			}
		}

	}

}
