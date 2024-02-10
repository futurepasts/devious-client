package net.runelite.client.plugins.kukiOakChopper.tasks;

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

public class ChopOak implements ScriptTask {



	private static final WorldPoint FIRST_OAK_TILE = new WorldPoint(3283,3476,0);
	private static final WorldPoint SECOND_OAK_TILE = new WorldPoint(3277,3480,0);

	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);


	@Override
	public boolean validate() {


		return !Inventory.isFull() && FIRST_OAK_TILE.distanceTo(Players.getLocal()) < 4 || SECOND_OAK_TILE.distanceTo(Players.getLocal()) < 4 && Skills.getLevel(Skill.WOODCUTTING) <30;
	}

	@Override
	public int execute() {
		if(Widgets.isVisible(levelup)) {
			Keyboard.sendSpace();
			return 500;
		}
		if(RandomUtils.nextInt(0, 54) == 0) {
			Time.sleep(2450,5447); // random break
			return 1000;
		}
		TileObject oakTree = TileObjects.getFirstSurrounding(FIRST_OAK_TILE,3,10820);
		if (oakTree != null) {
			MessageUtils.addMessage("Choppind down first tree");
			oakTree.interact("Chop down");
			Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
			Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
			return 1000;
		} else {
			TileObject secondOakTree = TileObjects.getFirstSurrounding(FIRST_OAK_TILE,3,10820);
			MessageUtils.addMessage("Chopping down second tree");
			if (secondOakTree != null) {
				secondOakTree.interact("Chop down");
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



