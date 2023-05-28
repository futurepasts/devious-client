package net.runelite.client.plugins.kukiCannon.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Skill;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.VarbitChanged;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;


public class Reload implements ScriptTask {




	@Override
	public boolean validate() {
		return Skills.getLevel(Skill.RANGED) !=50;
	}


	@Override
	public int execute() {

		int randomNum = (int)(Math.random() * 16) + 15;
		if (TileObjects.getNearest("Dwarf multicannon") != null && Inventory.contains("Cannonball")) {
			TileObjects.getNearest("Dwarf multicannon").interact("Fire");
			Time.sleep((long)(Math.random() * 15000 + 15000));
			MessageUtils.addMessage("Reloading cannon ");
			return 1000;
		}
		if (TileObjects.getNearest("Broken multicannon") != null && Inventory.contains("Cannonball")) {

			TileObjects.getNearest("Broken multicannon").interact("Repair");
			Time.sleep((long)(Math.random() * 15000 + 15000));
			MessageUtils.addMessage("Fixing cannon");
			return 1000;
		}
		return -1;
		}




}