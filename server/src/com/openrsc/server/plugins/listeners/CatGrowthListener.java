package com.openrsc.server.plugins.listeners;

import com.openrsc.server.model.entity.player.Player;

public interface CatGrowthListener {

	/**
	 * Called when a player has accumulated sufficient activity to advance kitten growth
	 */
	public void onCatGrowth(Player p);

	/**
	 * Called when a player has accumulated sufficient activity to advance kitten growth
	 *
	 * @return
	 */
	boolean blockCatGrowth(Player p);
}