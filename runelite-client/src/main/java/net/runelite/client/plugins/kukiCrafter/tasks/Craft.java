package net.runelite.client.plugins.kukiCrafter.tasks;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.utils.MessageUtils;
import net.unethicalite.api.widgets.Production;
import net.unethicalite.api.widgets.Widgets;

import static net.unethicalite.api.commons.Time.sleep;
import static net.unethicalite.api.commons.Time.sleepUntil;


public class Craft implements ScriptTask {

	private static final WorldPoint SECOND_START_TILE = new WorldPoint(3383, 3266, 0);


	@Getter(AccessLevel.PACKAGE)
	static boolean StartFir;


	Widget levelup = Widgets.get(WidgetInfo.LEVEL_UP);

	public boolean sleeping = false;

	protected boolean taskCooldown;

	@Override
	public boolean validate() {
		return Inventory.contains(1741) && !net.unethicalite.api.items.Bank.isOpen() && !Players.getLocal().isAnimating();
	}


	@Override
	public int execute() {

		if (Inventory.contains(1741) && !Bank.isOpen() && !Players.getLocal().isAnimating() && !Production.isOpen()) {
			Inventory.getFirst("Needle").useOn(Inventory.getFirst(1741));
			MessageUtils.addMessage("Interacting with leather ");
			return 1000;
		}
		if (Production.isOpen() && Skills.getLevel(Skill.CRAFTING) <7 && !Players.getLocal().isAnimating() && Players.getLocal().getAnimation() != 124) {
			MessageUtils.addMessage("Crafting gloves ");
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 14).interact(0);
			sleepUntil(() -> !Inventory.contains(ItemID.LEATHER) || Widgets.isVisible(levelup), 30000);
			return 2500;
		}
		if (Production.isOpen() && Skills.getLevel(Skill.CRAFTING) >=7 && Skills.getLevel(Skill.CRAFTING) <11 && Players.getLocal().getAnimation() != 124) {
			MessageUtils.addMessage("Crafting boots ");
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 15).interact(0);
			sleepUntil(() -> !Inventory.contains(ItemID.LEATHER) || Widgets.isVisible(levelup), 30000);
			return 2500;
		}
		if (Production.isOpen() && Skills.getLevel(Skill.CRAFTING) >=11 && Skills.getLevel(Skill.CRAFTING) <14 && Players.getLocal().getAnimation() != 124) {
			MessageUtils.addMessage("Crafting coif ");
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 16).interact(0);
			sleepUntil(() -> !Inventory.contains(ItemID.LEATHER) || Widgets.isVisible(levelup), 30000);
			return 2500;
		}
		if (Production.isOpen() && Skills.getLevel(Skill.CRAFTING) >=14 && Skills.getLevel(Skill.CRAFTING) <18 && Players.getLocal().getAnimation() != 124) {
			MessageUtils.addMessage("Crafting armour ");
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 17).interact(0);
			sleepUntil(() -> !Inventory.contains(ItemID.LEATHER) || Widgets.isVisible(levelup), 30000);
			return 2500;
		}
		if (Production.isOpen() && Skills.getLevel(Skill.CRAFTING) >=18 && Players.getLocal().getAnimation() != 124) {
			MessageUtils.addMessage("Crafting chaps ");
			Widgets.get(WidgetID.MULTISKILL_MENU_GROUP_ID, 18).interact(0);
			sleepUntil(() -> !Inventory.contains(ItemID.LEATHER) || Widgets.isVisible(levelup), 30000);
			return 2500;
		}
			return -1;
		}




}