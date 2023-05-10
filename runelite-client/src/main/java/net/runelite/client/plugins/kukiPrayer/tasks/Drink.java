package net.runelite.client.plugins.kukiPrayer.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Item;
import net.runelite.api.Prayer;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Prayers;

import java.util.Random;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Drink implements ScriptTask {


	@Getter(AccessLevel.PACKAGE)
	static boolean StartFir;

	Random random = new Random();

	int randomNumber = random.nextInt(28 - 15 + 1) + 15;


	public boolean sleeping = false;

	protected boolean taskCooldown;

	@Override
	public boolean validate() {
		return Prayers.getPoints() < randomNumber;
	}


	@Override
	public int execute() {

		Item prayerPot = Inventory.getFirst(x -> x.hasAction("Drink")
				&& (x.getName().contains("Prayer potion")));
		if (prayerPot != null)
		{
			prayerPot.interact("Drink");
			return -1;
		}
		return -1;
		}




}


