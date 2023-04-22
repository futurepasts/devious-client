package net.runelite.client.plugins.kukiBoner.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.widgets.Widgets;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class OfferBones implements ScriptTask {

	private static final WorldPoint SECOND_START_TILE = new WorldPoint(3383, 3266, 0);


	@Getter(AccessLevel.PACKAGE)
	static boolean StartFir;


	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);

	public boolean sleeping = false;

	public int leatherTicks = 3;
	protected boolean taskCooldown;

	@Override
	public boolean validate() {
		return Inventory.contains(1741) && !net.unethicalite.api.items.Bank.isOpen() && !Players.getLocal().isAnimating();
	}


	@Override
	public int execute() {
			return -1;
		}




}