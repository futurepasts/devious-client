package net.runelite.client.plugins.kukiSuperheat.tasks;

import net.runelite.api.*;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Bank implements ScriptTask {


	private static final List<Integer> TREE_ID = Arrays.asList(440,1619,1623,1621,23442);

	@Override
	public boolean validate() {

		return !Inventory.contains(440);
	}

	public static int BreaksSuper = 0;

	@Override
	public int execute() {
		MessageUtils.addMessage("Opening bank ");

		if (!Inventory.contains("Iron ore") && net.unethicalite.api.items.Bank.isOpen() && !Inventory.contains(2351)) {
			net.unethicalite.api.items.Bank.withdraw(440,27, net.unethicalite.api.items.Bank.WithdrawMode.ITEM);
			net.unethicalite.api.items.Bank.close();
			sleep(Constants.GAME_TICK_LENGTH);
			MessageUtils.addMessage("Withdrew Iron ore ");
			return 1000;
		}
		if(RandomUtils.nextInt(0, 54) == 0) {
			BreaksSuper++;
			Time.sleep(2450,34741); // random break
			return 1000;
		}
		if (!Inventory.contains(440) && !net.unethicalite.api.items.Bank.isOpen()) {
			MessageUtils.addMessage("Opening bank ");
			TileObjects.getNearest(3194).interact("Bank");
			sleepUntil(() -> net.unethicalite.api.items.Bank.isOpen(), 30000);
			return 600;
		}
		if (!Inventory.contains(440) && net.unethicalite.api.items.Bank.isOpen() && Inventory.contains(2351)) {
			net.unethicalite.api.items.Bank.deposit(2351,27);
			return 600;
		}
		return 1000;
	}

}
