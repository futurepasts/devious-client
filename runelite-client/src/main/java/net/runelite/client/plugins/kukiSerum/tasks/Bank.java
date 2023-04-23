package net.runelite.client.plugins.kukiSerum.tasks;

import net.runelite.api.Constants;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;

public class Bank implements ScriptTask {


	@Override
	public boolean validate() {
		return !Inventory.contains(95) && !Inventory.contains(592);
	}

	@Override
	public int execute() {
		if (Inventory.contains(3410) && !net.unethicalite.api.items.Bank.isOpen()) {
			MessageUtils.addMessage("Opening bank ");
			TileObjects.getNearest(3194).interact("Bank");
			sleepUntil(() -> net.unethicalite.api.items.Bank.isOpen(), 30000);
			return -1;
		}
		if (Inventory.contains(3410) && net.unethicalite.api.items.Bank.isOpen()) {
			MessageUtils.addMessage("Depositing serum");
			net.unethicalite.api.items.Bank.depositAll(3410);
			return -1;
		}
		if (Inventory.isEmpty() && net.unethicalite.api.items.Bank.isOpen()) {
			net.unethicalite.api.items.Bank.withdraw(95,4, net.unethicalite.api.items.Bank.WithdrawMode.ITEM);
			sleep(Constants.GAME_TICK_LENGTH);
			net.unethicalite.api.items.Bank.withdraw(592,4, net.unethicalite.api.items.Bank.WithdrawMode.ITEM);
			net.unethicalite.api.items.Bank.close();
			sleep(Constants.GAME_TICK_LENGTH);
			MessageUtils.addMessage("Closing bank ");
			return -1;
		}
		return 1000;
	}
}