package net.runelite.client.plugins.xRunecrafting;

import lombok.Getter;
import net.runelite.api.*;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import javax.inject.Inject;
import java.util.function.Predicate;

@Getter
public class xRunecraftingUtils {
    static xRunecraftingConfig config;

    @Inject
    public xRunecraftingUtils(xRunecraftingConfig config) {
        this.config = config;
    }

    public static int Tiara = 5525;
    public static int AirTalisman = 1438;
    public static int DuelRing8 = 2552, DuelRing7 = 2554, DuelRing6 = 2556, DuelRing5 = 2558, DuelRing4 = 2560, DuelRing3 = 2562, DuelRing2 = 2564, DuelRing1 = 2566;
    public static int AirTiara = 5527, FireTiara = 5537;

    public static int PreviousHeadItem = 0, PreviousRingItem = 0;
    static final Predicate<Item> DuelRing = i -> i.getName().contains("Ring of dueling(8)")
            || i.getName().contains("Ring of dueling(7)") || i.getName().contains("Ring of dueling(6)") || i.getName().contains("Ring of dueling(5)")
            || i.getName().contains("Ring of dueling(4)") || i.getName().contains("Ring of dueling(3)") || i.getName().contains("Ring of dueling(2)");

    public static boolean ConfigIsFireAltarSelected() {
        if (config.xrunecraftingsettings().getName() == "FireAltar") {
            return true;
        }
        return false;
    }
    public static boolean ConfigIsAirAltarSelected() {
        if (config.xrunecraftingsettings().getName() == "AirAltar") {
            return true;
        }
        return false;
    }

    public static boolean DuelRingsTeleportLeft() {
        config.xrunecraftingsettings().getName();
        EquipmentInventorySlot ringSlot = EquipmentInventorySlot.RING;
        Item duelingRing = Equipment.fromSlot(ringSlot);
        if (ringSlot == null) { return false; }
        if (duelingRing == null) { return false; }

        if (duelingRing.getId() == DuelRing8) {
            return true;
        } else if (duelingRing.getId() == DuelRing7) {
            return true;
        } else if (duelingRing.getId() == DuelRing6) {
            return true;
        } else if (duelingRing.getId() == DuelRing5) {
            return true;
        } else if (duelingRing.getId() == DuelRing4) {
            return true;
        } else if (duelingRing.getId() == DuelRing3) {
            return true;
        } else if (duelingRing.getId() == DuelRing2) {
            return true;
        } else if (duelingRing.getId() == DuelRing1) {
            return false;
        }
        return false;
    }

    public static void DuelRingItem_Interact(String Action) {
        EquipmentInventorySlot ringSlot = EquipmentInventorySlot.RING;
        Item duelingRing = Equipment.fromSlot(ringSlot);
        if (ringSlot == null) { return; }
        if (duelingRing == null) { return; }
        duelingRing.interact(Action);
    }

    public static boolean DoWeHaveARingEquipped() {
        if (ConfigIsAirAltarSelected()) {
            return true;
        } //We dont need a ring for Air runes...
        EquipmentInventorySlot ringSlot = EquipmentInventorySlot.RING;
        Item duelingRing = Equipment.fromSlot(ringSlot);
        if (ringSlot == null) { return false; }
        if (duelingRing == null) { return false; }
        if (duelingRing.getId() == DuelRing8) {
            return true;
        } else if (duelingRing.getId() == DuelRing7) {
            return true;
        } else if (duelingRing.getId() == DuelRing6) {
            return true;
        } else if (duelingRing.getId() == DuelRing5) {
            return true;
        } else if (duelingRing.getId() == DuelRing4) {
            return true;
        } else if (duelingRing.getId() == DuelRing3) {
            return true;
        } else if (duelingRing.getId() == DuelRing2) {
            return true;
        } else if (duelingRing.getId() == DuelRing1) {
            return false;
        } else {
            if (!Inventory.isFull()) {
                PreviousRingItem = duelingRing.getId();
                duelingRing.interact("Remove");
                return false;
            }
        }
        return false;
    }
}





