package net.runelite.client.plugins.kukiSuperheat.tasks;

import lombok.val;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.magic.Magic;
import net.unethicalite.api.magic.Spell;
import net.unethicalite.api.magic.SpellBook;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static net.unethicalite.api.commons.Time.sleepUntil;

public class SuperHeat implements ScriptTask {



	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);


	@Override
	public boolean validate() {


		return Inventory.contains(440) && !Bank.isOpen();
	}

	@Override
	public int execute() {
		Item[] items = Inventory.getAll(440).toArray(new Item[0]);
		if(Widgets.isVisible(levelup)) {
			Keyboard.sendSpace();
			return 600;
		}
		Random rand = new Random();

		while (items.length > 0) {

			int index = rand.nextInt(items.length);

			Item item = items[index];

			if (!Magic.isSpellSelected(SpellBook.Standard.SUPERHEAT_ITEM)) {
				Magic.cast(SpellBook.Standard.SUPERHEAT_ITEM, item);
				Time.sleepUntil(() -> Players.getLocal().getAnimation() == 725, 2500);
				Time.sleep(500,750);
			}

			items = ArrayUtils.remove(items, index);

			return 600; // return inside loop
		}
		return 1000;
	}



}








