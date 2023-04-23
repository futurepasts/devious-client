package net.runelite.client.plugins.kukiSerum.tasks;

import net.runelite.api.Skill;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import java.util.concurrent.atomic.AtomicInteger;

public class UseOn implements ScriptTask {


	@Override
	public boolean validate() {
		return Inventory.contains(95) && Inventory.contains(592) && !Bank.isOpen();
	}

	@Override
	public int execute() {
		Inventory.getFirst(95).useOn(Inventory.getFirst(592));
		return 500;
	}
}