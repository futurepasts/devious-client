package net.runelite.client.plugins.kukiCannon.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.utils.MessageUtils;



public class Logout implements ScriptTask {


	@Override
	public boolean validate() {

		return Skills.getLevel(Skill.RANGED) == 50;
	}


	@Override
	public int execute() {
		if (TileObjects.getNearest("Dwarf multicannon") != null) {
			TileObjects.getNearest("Dwarf multicannon").interact("Pick-up");
			MessageUtils.addMessage("Picking cannon ");
			return 1000;
		}
		return 250;
	}
}




