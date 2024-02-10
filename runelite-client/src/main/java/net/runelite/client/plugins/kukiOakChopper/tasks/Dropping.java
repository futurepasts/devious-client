package net.runelite.client.plugins.kukiOakChopper.tasks;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Widgets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Dropping implements ScriptTask {


	private static final List<Integer> TREE_ID = Arrays.asList(1521,1519);

	@Override
	public boolean validate() {

		return Inventory.isFull();
	}


	@Override
	public int execute() {
		MessageUtils.addMessage("Starting DROPING EXECUTION ");

		List<Item> fishItems = getFishItemsInInventory();
		Collections.shuffle(fishItems);
		for (Item fishToDrop : fishItems) {
			if (TREE_ID != null) {
				fishToDrop.drop();
				Time.sleepUntil(() -> !Inventory.contains(fishToDrop.getId()), 250);
			} else {
				break;
			}
		}
		return 1000;
	}

	private boolean inventoryContainsAnyOf(List<Integer> itemIds) {
		return Inventory.getAll().stream().anyMatch(item -> itemIds.contains(item.getId()));
	}

	private List<Item> getFishItemsInInventory() {
		return Inventory.getAll().stream()
				.filter(item -> TREE_ID.contains(item.getId()))
				.collect(Collectors.toList());
	}

}
