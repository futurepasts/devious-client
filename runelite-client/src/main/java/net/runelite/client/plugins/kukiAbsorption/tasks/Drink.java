package net.runelite.client.plugins.kukiAbsorption.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetID;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Prayers;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import java.util.Random;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Drink implements ScriptTask {


	@Override
	public boolean validate() {

		return Vars.getBit(Varbits.NMZ_ABSORPTION) < 350 && TileObjects.getNearest(26276).hasAction("Drink");
	}


	@Override
	public int execute() {

		Item prayerPot = Inventory.getFirst(x -> x.hasAction("Drink")
				&& (x.getName().contains("Absorption ")));
		if (prayerPot != null)
		{
			prayerPot.interact("Drink");
			return 1000;
		}
		return -1;
		}




}


