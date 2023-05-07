package net.runelite.client.plugins.kukiBirdSnare.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Constants;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Items;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class CatchBird implements ScriptTask {

	private static final WorldPoint LAY_BIRD_SNARE = new WorldPoint(2609, 2928, 0);


	@Getter(AccessLevel.PACKAGE)

	public boolean sleeping = false;


	@Override
	public boolean validate() {
		return !Inventory.isFull();
	}


	@Override
	public int execute() {

		if (TileObjects.getNearest(LAY_BIRD_SNARE,9345) == null && Inventory.contains(10006) && TileObjects.getNearest(LAY_BIRD_SNARE,9344) == null && TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare") == null && !Players.getLocal().getWorldLocation().equals(LAY_BIRD_SNARE))
		{
			Movement.walkTo(LAY_BIRD_SNARE);
			sleepUntil(() -> Players.getLocal().getWorldLocation().equals(LAY_BIRD_SNARE), 30000);
			MessageUtils.addMessage("I'm at location ");
			return 1000;
		}
		if (TileObjects.getNearest(LAY_BIRD_SNARE,9345) == null && Inventory.contains(10006) && TileObjects.getNearest(LAY_BIRD_SNARE,9344) == null && TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare") == null && Players.getLocal().getWorldLocation().equals(LAY_BIRD_SNARE))
		{
			Inventory.getFirst("Bird snare").interact("Lay");
			sleepUntil(() -> Players.getLocal().getAnimation() == 5208, 30000);
			MessageUtils.addMessage("Laid a bird snare ");
			return 1000;
		}
		if (TileObjects.getNearest(LAY_BIRD_SNARE,9345) == null && !Inventory.contains(10006) && TileObjects.getNearest(LAY_BIRD_SNARE,9344) != null && TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare") == null)
		{
			TileObjects.getNearest(LAY_BIRD_SNARE,9344).interact("Dismantle");
			sleepUntil(() -> Inventory.contains(10006), 30000);
			MessageUtils.addMessage("Dismantled trap ");
			return 1000;
		}
		if (TileObjects.getNearest(LAY_BIRD_SNARE,9345) == null && !Inventory.contains(10006) && TileObjects.getNearest(LAY_BIRD_SNARE,9344) == null && TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare") != null && Players.getLocal().getAnimation() != 5208)
		{
			TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare").interact("Take");
			sleepUntil(() -> Inventory.contains(10006), 30000);
			MessageUtils.addMessage("Picked dropped bird snare ");
			return 1000;
		}
		if (TileObjects.getNearest(LAY_BIRD_SNARE,9345) == null && !Inventory.contains(10006) && TileObjects.getNearest(LAY_BIRD_SNARE,9344) == null && TileItems.getNearest(LAY_BIRD_SNARE,"Bird snare") == null && TileObjects.getNearest(LAY_BIRD_SNARE,9373) != null)
		{
			TileObjects.getNearest(LAY_BIRD_SNARE,"Bird snare").interact("Check");
			sleepUntil(() -> Inventory.contains(10006), 30000);
			MessageUtils.addMessage("Catched a bird ");
			return 1000;
		}
		return -1;
		}






}