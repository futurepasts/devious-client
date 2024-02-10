package net.runelite.client.plugins.kukiIronMiner.tasks;

import net.runelite.api.*;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class DropIron implements ScriptTask {


	private static final List<Integer> TREE_ID = Arrays.asList(440,1619,1623,1621,23442);

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
