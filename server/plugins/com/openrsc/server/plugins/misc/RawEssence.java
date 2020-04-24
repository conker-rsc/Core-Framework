package com.openrsc.server.plugins.misc;

import com.openrsc.server.constants.ItemId;
import com.openrsc.server.constants.Skills;
import com.openrsc.server.event.custom.BatchEvent;
import com.openrsc.server.model.entity.GameObject;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.triggers.OpLocTrigger;
import com.openrsc.server.plugins.skills.mining.Mining;

import static com.openrsc.server.plugins.Functions.*;

public class RawEssence implements OpLocTrigger {
	@Override
	public boolean blockOpLoc(GameObject obj, String command, Player player) {
		return obj.getID() == 1227;
	}

	@Override
	public void onOpLoc(GameObject obj, String command, Player player) {
		int axeID = Mining.getAxe(player);
		if (axeID < 0)
		{
			mes(player, "You need a pickaxe to mine Rune Essence.");
			return;
		}

		player.setBatchEvent(new BatchEvent(player.getWorld(), player, player.getWorld().getServer().getConfig().GAME_TICK, "Mining rune essence", player.getCarriedItems().getInventory().getFreeSlots(), true) {
			public void action() {
				give(player, ItemId.RUNE_ESSENCE.id(), 1);
				player.incExp(Skills.MINING, 20, true);
				if (player.getCarriedItems().getInventory().full())
					interruptBatch();
			}
		});
	}
}
