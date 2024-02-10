package net.runelite.client.plugins.kukiOakChopper.tasks;

import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;
import org.apache.commons.lang3.RandomUtils;

public class ChopWillow implements ScriptTask {



	private static final WorldPoint FIRST_WILLOW_TILE = new WorldPoint(2962,3197,0);
	private static final WorldPoint SECOND_WILLOW_TILE = new WorldPoint(2964,3195,0);

	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);


	@Override
	public boolean validate() {


		return !Inventory.isFull() && FIRST_WILLOW_TILE.distanceTo(Players.getLocal()) < 4 || SECOND_WILLOW_TILE.distanceTo(Players.getLocal()) < 4;
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
		TileObject oakTree = TileObjects.getFirstSurrounding(FIRST_WILLOW_TILE,3,10831);
		if (oakTree != null) {
			MessageUtils.addMessage("Choppind down first tree");
			oakTree.interact("Chop down");
			Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
			Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
			return 1000;
		} else {
			TileObject secondOakTree = TileObjects.getFirstSurrounding(SECOND_WILLOW_TILE,3,10829);
			if (secondOakTree != null) {
				MessageUtils.addMessage("Chopping down second tree");
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



