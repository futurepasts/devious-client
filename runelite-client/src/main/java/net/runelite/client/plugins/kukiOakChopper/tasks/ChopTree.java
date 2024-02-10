package net.runelite.client.plugins.kukiOakChopper.tasks;

import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;
import org.apache.commons.lang3.RandomUtils;

public class ChopTree implements ScriptTask {


	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);


	@Override
	public boolean validate() {


		return !Inventory.isFull() && Skills.getLevel(Skill.WOODCUTTING) <15;
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
		TileObject tree = TileObjects.getNearest(1278);
		if (tree != null) {
			MessageUtils.addMessage("Choppind down a tree");
			tree.interact("Chop down");
			Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
			Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
			return 1000;

		} else {
			MessageUtils.addMessage("Found no tree's");
			return 1000;
		}
		}
	}



