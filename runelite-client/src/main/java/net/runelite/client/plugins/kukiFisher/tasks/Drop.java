package net.runelite.client.plugins.kukiFisher.tasks;

import net.runelite.api.Item;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.items.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Drop implements ScriptTask {

    private static final List<Integer> FISH_IDS = Arrays.asList(335, 331);

    @Override
    public boolean validate() {
        return Inventory.isFull() && inventoryContainsAnyOf(FISH_IDS);
    }

    @Override
    public int execute() {
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