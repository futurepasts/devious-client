package net.runelite.client.kukiBoner.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class UnnoteBones implements ScriptTask
{
	private static final WorldPoint BANK_TILE = new WorldPoint(3382, 3270, 0);
	Widget unnote = Widgets.get(WidgetInfo.DIALOG_OPTION_OPTION1);

	@Override
	public boolean validate()
	{
		return true;
	}



	@Override
	public int execute()
	{
		if (!Inventory.contains(536) && net.unethicalite.api.entities.NPCs.getNearest(1614) != null) {
			net.unethicalite.api.items.Inventory.getFirst(537).useOn(NPCs.getNearest(1614));
			sleepUntil(() -> Dialog.isOpen(), 30000);
			MessageUtils.addMessage("Unnote UI is open ");
			Dialog.invokeDialog(DialogOption.CHAT_OPTION_THREE);
			MessageUtils.addMessage("Unnoting... ");
			return 1000;
		}
		if (Inventory.contains(536) && Inventory.isFull() && TileObjects.getNearest(29091) !=null) {
			TileObjects.getNearest(29091).interact("Visit-Last");
			sleepUntil(() -> TileObjects.getNearest(29091) == null, 30000);
			MessageUtils.addMessage("We're in house near Altar ");
			return 1000;
		}
		if (Inventory.contains(536) && TileObjects.getNearest(4525) !=null && TileObjects.getNearest("Altar").distanceTo(Players.getLocal().getWorldLocation()) > 1) {
			Inventory.getFirst(536).useOn(TileObjects.getNearest("Altar"));
			sleepUntil(() -> Players.getLocal().isAnimating(), 1550);
			return 3500;
		}
		if (Inventory.contains(536) && TileObjects.getNearest(4525) !=null && TileObjects.getNearest("Altar").distanceTo(Players.getLocal().getWorldLocation()) == 1) {
			Inventory.getFirst(536).useOn(TileObjects.getNearest("Altar"));
			sleepUntil(() -> !Players.getLocal().isAnimating(), 155);
			return 255;
		}
		if (!Inventory.contains(536) && TileObjects.getNearest(4525) !=null) {
			TileObjects.getNearest(4525).interact("Enter");
			sleepUntil(() -> TileObjects.getNearest(29091) != null, 25500);
			return 1000;
		}
		return 1000;
	}
}
