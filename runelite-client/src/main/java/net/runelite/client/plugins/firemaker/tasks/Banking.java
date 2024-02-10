package net.runelite.client.plugins.firemaker.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;


public class Banking implements ScriptTask {
	private static final WorldPoint BANK_TILE = new WorldPoint(3382, 3270, 0);
	private static final WorldPoint NEAR_BANK_tILE = new WorldPoint(3383, 3269, 0);

	int logs;


	private void determineLogs() {

		logs = getLogIdForLevel();

	}

	private int getLogIdForLevel() {
		int firemakingLevel = Skills.getLevel(Skill.FIREMAKING);

		if (firemakingLevel < 15) {
			return 1511;
		} else if (firemakingLevel < 30) {
			return 1521;
		} else if (firemakingLevel < 45) {
			return 1519;
		} else {
			return 1517;
		}
	}

	@Override
	public boolean validate() {

		determineLogs();
		return !Inventory.contains(logs);
	}


	@Override
	public int execute() {
		MessageUtils.addMessage("Starting BANKING EXECUTION ");
		MessageUtils.addMessage("Current log " + logs);


		if (Bank.isOpen()) {
			if (!Inventory.contains(logs)) {
				MessageUtils.addMessage("Withdrawing logs ");
				Time.sleep(250,347);
				Bank.withdraw(logs, 27, Bank.WithdrawMode.ITEM);
				return 1000;
			}
		}
		Player local = Players.getLocal();
		TileObject booth = TileObjects.getFirstAt(BANK_TILE, x -> x.hasAction("Bank", "Collect"));
		if (!Bank.isOpen() && booth.distanceTo(local) > 20) {
			if (!Movement.isRunEnabled()) {
				MessageUtils.addMessage("Enabling RUN ");
				Movement.toggleRun();
				return 1000;
			}
		}
		if (Movement.isWalking()) {
			MessageUtils.addMessage("Walking ");
			Time.sleep(1200,1578);
			return 600;
		}

		if (booth != null) {
			if (!Players.getLocal().getWorldLocation().equals(NEAR_BANK_tILE) &&
							!Movement.isWalking() &&
							!Inventory.contains(logs) &&
							!Reachable.isInteractable(booth)) {

				MessageUtils.addMessage("Walking  ");
				while (Players.getLocal().distanceTo(NEAR_BANK_tILE) > 3) {
					Movement.walkTo(NEAR_BANK_tILE);
					Time.sleep(2780,3574);
				}
				return 2000;
			}

		}
		if (Reachable.isInteractable(booth) && !net.unethicalite.api.items.Bank.isOpen() && !Inventory.contains(logs)) {
			MessageUtils.addMessage("Opening bank  ");
			booth.interact("Bank");
			while (Movement.isWalking()) {
				Time.sleep(2780,3574);
			}
			return 1000;
		}


		return 1000;
	}
}
