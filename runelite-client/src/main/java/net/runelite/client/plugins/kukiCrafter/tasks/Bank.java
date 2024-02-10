package net.runelite.client.plugins.kukiCrafter.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Bank implements ScriptTask
{
	private static final WorldPoint BANK_TILE = new WorldPoint(3382, 3270, 0);

	@Override
	public boolean validate()
	{
		return true;
	}



	@Override
	public int execute()
	{
		if (!Inventory.contains("Leather") && net.unethicalite.api.items.Bank.isOpen() && !Inventory.contains("Leather gloves","Leather boots","Leather cowl","Leather vambraces","Leather chaps", "Leather body")) {
			net.unethicalite.api.items.Bank.withdraw(1741,26, net.unethicalite.api.items.Bank.WithdrawMode.ITEM);
			net.unethicalite.api.items.Bank.close();
			sleep(Constants.GAME_TICK_LENGTH);
			MessageUtils.addMessage("Withdrawing leather ");
			return 1000;
		}
		if (!Inventory.contains(1741) && !net.unethicalite.api.items.Bank.isOpen()) {
			MessageUtils.addMessage("Opening bank ");
			TileObjects.getNearest(3194).interact("Bank");
			sleepUntil(() -> net.unethicalite.api.items.Bank.isOpen(), 30000);
			return 2500;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather gloves")) {
			MessageUtils.addMessage("Depositing gloves");
			net.unethicalite.api.items.Bank.depositAll("Leather gloves");
			return -1;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather boots")) {
			MessageUtils.addMessage("Depositing boots");
			net.unethicalite.api.items.Bank.depositAll("Leather boots");
			return -1;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather vambraces")) {
			MessageUtils.addMessage("Depositing vambraces");
			net.unethicalite.api.items.Bank.depositAll("Leather vambraces");
			return -1;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather cowl")) {
			MessageUtils.addMessage("Depositing cowl");
			net.unethicalite.api.items.Bank.depositAll("Leather cowl");
			return -1;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather body")) {
			MessageUtils.addMessage("Depositing armour");
			net.unethicalite.api.items.Bank.depositAll("Leather body");
			return -1;
		}
		if (!Inventory.contains(1741) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains("Leather chaps")) {
			MessageUtils.addMessage("Depositing chaps ");
			net.unethicalite.api.items.Bank.depositAll("Leather chaps");
			return -1;
		}
		return 1000;
	}
}
