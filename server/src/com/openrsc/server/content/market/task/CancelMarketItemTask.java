package com.openrsc.server.content.market.task;

import com.openrsc.server.Server;
import com.openrsc.server.content.market.MarketItem;
import com.openrsc.server.external.ItemDefinition;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.net.rsc.ActionSender;

public class CancelMarketItemTask extends MarketTask {

	private Player owner;
	private int auctionID;

	public CancelMarketItemTask(Player owner, final int auctionID) {
		this.owner = owner;
		this.auctionID = auctionID;
	}

	@Override
	public void doTask() {
		boolean updateDiscord = false;
		MarketItem item = owner.getWorld().getMarket().getMarketDatabase().getAuctionItem(auctionID);
		if (item != null) {
			int itemIndex = item.getItemID();
			int amount = item.getAmountLeft();
			ItemDefinition def = owner.getWorld().getServer().getEntityHandler().getItemDef(itemIndex);
			if (!owner.getInventory().full() && (!def.isStackable() && owner.getInventory().size() + amount <= 30)) {
				if (owner.getWorld().getMarket().getMarketDatabase().cancel(item)) {
					if (!def.isStackable()) for (int i = 0; i < amount; i++)
						owner.getInventory().add(new Item(itemIndex, 1));
					else owner.getInventory().add(new Item(itemIndex, amount));
					ActionSender.sendBox(owner, "@gre@[Auction House - Success] % @whi@ The item has been canceled and returned to your inventory.", false);
					updateDiscord = true;
				}
			} else if (!owner.getBank().full()) {
				if (owner.getWorld().getMarket().getMarketDatabase().cancel(item)) {
					owner.getBank().add(new Item(itemIndex, amount));
					ActionSender.sendBox(owner, "@gre@[Auction House - Success] % @whi@ The item has been canceled and returned to your bank. % Talk with a Banker to collect your item(s).", false);
					updateDiscord = true;
				}
			} else
				ActionSender.sendBox(owner, "@red@[Auction House - Error] % @whi@ Unable to cancel auction! % % @red@Reason: @whi@No space left in your bank or inventory.", false);
		}
		owner.getWorld().getMarket().addRequestOpenAuctionHouseTask(owner);
		if (updateDiscord) {
			Server.getServer().getDiscordService().auctionCancel(item);
		}
	}

}
