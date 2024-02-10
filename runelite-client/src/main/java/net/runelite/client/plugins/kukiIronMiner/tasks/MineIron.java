package net.runelite.client.plugins.kukiIronMiner.tasks;

import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;
import net.unethicalite.client.Static;
import net.unethicalite.client.managers.RegionManager;
import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static net.unethicalite.api.commons.Time.sleepUntil;

public class MineIron implements ScriptTask {



	private static final WorldPoint FIRST_IRON_TILE = new WorldPoint(3402,3168,0);
	private static final WorldPoint SECOND_IRON_TILE = new WorldPoint(3403,3169,0);
	private static final WorldPoint THIRD_IRON_TILE = new WorldPoint(3402,3171,0);

	public static int Breaks = 0;

	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);


	@Override
	public boolean validate() {


		return !Inventory.isFull() && FIRST_IRON_TILE.distanceTo(Players.getLocal()) < 4 || SECOND_IRON_TILE.distanceTo(Players.getLocal()) < 4 || THIRD_IRON_TILE.distanceTo(Players.getLocal()) < 4;
	}

	@Override
	public int execute() {
		if(Widgets.isVisible(levelup)) {
			Keyboard.sendSpace();
			return 500;
		}
		if(RandomUtils.nextInt(0, 54) == 0) {
			Breaks++;
			Time.sleep(2450,34741); // random break
			return 1000;
		}
		TileObject oakTree = TileObjects.getFirstAt(FIRST_IRON_TILE,11364,10820);
		if (oakTree != null) {
			MessageUtils.addMessage("Mining first ore");
			oakTree.interact("Mine");
			Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
			Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
			return 1000;
		} else {
			TileObject secondOakTree = TileObjects.getFirstAt(SECOND_IRON_TILE,11365,10820);
			MessageUtils.addMessage("Mining second ore");
			if (secondOakTree != null) {
				secondOakTree.interact("Mine");
				Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
				Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
				return 1000;
			} else {
				TileObject thirdOre = TileObjects.getFirstAt(THIRD_IRON_TILE,11365,10820);
				MessageUtils.addMessage("Mining third  ore");
				if (thirdOre != null) {
					thirdOre.interact("Mine");
					Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
					Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
					return 1000;

			} else {
				MessageUtils.addMessage("Found no tree's");
			}

		}
		return 1000;
	}

}
}


