package net.runelite.client.plugins.firemaker.tasks;

import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;


public class Bank implements ScriptTask
{
	private static final WorldPoint BANK_TILE = new WorldPoint(3382, 3270, 0);

	@Override
	public boolean validate()
	{
		return !Inventory.contains(1519);
	}



	@Override
	public int execute()
	{
		Player local = Players.getLocal();
		if (!net.unethicalite.api.items.Bank.isOpen())
		{
			if (!Movement.isRunEnabled())
			{
				Movement.toggleRun();
				return 1000;
			}

			if (Movement.isWalking())
			{
				return 1000;
			}

			TileObject booth = TileObjects.getFirstAt(BANK_TILE, x -> x.hasAction("Bank", "Collect"));
			if (booth == null || booth.distanceTo(local) > 20 || !Reachable.isInteractable(booth) && !Players.getLocal().isAnimating() && !Inventory.contains("Willow logs") )
			{
				Movement.walkTo(BANK_TILE);
				return 2000;
			}

			booth.interact("Bank");
			return 3000;
		}

		if (!Inventory.contains(ItemID.WILLOW_LOGS) && net.unethicalite.api.items.Bank.isOpen())
		{
			net.unethicalite.api.items.Bank.withdraw(1519,27, net.unethicalite.api.items.Bank.WithdrawMode.ITEM);
			return 1000;
		}

		return 1000;
	}
}
