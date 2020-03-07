package com.openrsc.server.plugins.listeners;

import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.GroundItem;
import com.openrsc.server.model.entity.player.Player;

public interface InvUseOnGroundItemListener {
	void onInvUseOnGroundItem(Item myItem, GroundItem item, Player player);
	boolean blockInvUseOnGroundItem(Item myItem, GroundItem item, Player player);
}