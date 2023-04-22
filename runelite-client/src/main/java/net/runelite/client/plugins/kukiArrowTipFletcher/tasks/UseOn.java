package net.runelite.client.plugins.kukiArrowTipFletcher.tasks;

import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

public class UseOn implements ScriptTask {

	AtomicInteger startingXP = new AtomicInteger(Skills.getExperience(Skill.FLETCHING));
	long lastXpDrop = System.currentTimeMillis();

	@Override
	public boolean validate() {
		return Inventory.contains(53) && Inventory.contains(40) || Inventory.contains(41) || Inventory.contains(52);
	}

	@Override
	public int execute() {
		if (!Players.getLocal().isAnimating() && hasXpDrop() && !Production.isOpen() && Inventory.contains(52) && Skills.getLevel(Skill.FLETCHING) < 15) {
			Inventory.getFirst("Feather").useOn(Inventory.getFirst(52));
		} else if  (!Players.getLocal().isAnimating() && hasXpDrop() && !Production.isOpen() && Inventory.contains(40)) {
				Inventory.getFirst("Headless arrow").useOn(Inventory.getFirst(40));
			} else if (!Players.getLocal().isAnimating() && hasXpDrop() && !Production.isOpen() && Inventory.contains(41)) {
				Inventory.getFirst("Headless arrow").useOn(Inventory.getFirst(41));
				return 1000;
			}

			if (Production.isOpen()) {
				Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 14).interact(0);
				return 1000;
			}

			return 1000;
		}

	private boolean hasXpDrop() {
		int currentXP = Skills.getExperience(Skill.FLETCHING);
		if (currentXP > startingXP.get()) {
			startingXP.set(currentXP);
			lastXpDrop = System.currentTimeMillis();
			return false;
		}

		if (System.currentTimeMillis() - lastXpDrop < 2000) {  // 2 second delay
			return false;
		}

		return true;
	}
}