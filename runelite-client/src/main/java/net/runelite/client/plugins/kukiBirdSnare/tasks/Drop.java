package net.runelite.client.plugins.kukiBirdSnare.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.api.widgets.Widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Drop implements ScriptTask
{
	private static final WorldPoint BANK_TILE = new WorldPoint(3382, 3270, 0);

	private static final List<Integer> FISH_IDS = Arrays.asList(10088, 9978,526);

	@Override
	public boolean validate()
	{

		return Inventory.isFull();
	}



	@Override
	public int execute()
	{
		List<Item> fishItems = getFishItemsInInventory();
		Collections.shuffle(fishItems);

		for (Item fishToDrop : fishItems) {
			if (fishToDrop != null) {
				fishToDrop.drop();
				Time.sleepUntil(() -> !Inventory.contains(fishToDrop.getId()), 250);
			} else {
				break;
			}
		}
		return 250;
	}


	private boolean inventoryContainsAnyOf(List<Integer> itemIds) {
		return Inventory.getAll().stream().anyMatch(item -> itemIds.contains(item.getId()));
	}

	private List<Item> getFishItemsInInventory() {
		return Inventory.getAll().stream()
				.filter(item -> FISH_IDS.contains(item.getId()))
				.collect(Collectors.toList());
   }
}


