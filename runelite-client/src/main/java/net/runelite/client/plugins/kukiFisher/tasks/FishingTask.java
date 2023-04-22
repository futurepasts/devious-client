package net.runelite.client.plugins.kukiFisher.tasks;

import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;

public class FishingTask implements ScriptTask {
	private static final WorldPoint[] FISHING_SPOTS = {
			new WorldPoint(2461, 3146, 0),
			new WorldPoint(2461, 3150, 0),
			new WorldPoint(2461, 3151, 0),
			new WorldPoint(2468, 3157, 0)
	};
	private static final int FISHING_SPOT_ID = 1507;

	@Override
	public boolean validate() {
		return !Inventory.isFull();
	}

	@Override
	public int execute() {
		NPC fishingSpot = NPCs.getNearest(npc -> npc.getId() == FISHING_SPOT_ID && isInFishingSpotsForNPC(npc));
		if (fishingSpot != null) {
			int startingXP = Skills.getExperience(Skill.FISHING);
			fishingSpot.interact("Lure");
			Time.sleepUntil(() -> Players.getLocal().isAnimating(), 5500);
			Time.sleepUntil(() -> !Players.getLocal().isAnimating(), 250000);
			return 1000;
		} else {
			WorldPoint closestFishingSpot = getClosestFishingSpot();
			if (closestFishingSpot != null) {
				Movement.walkTo(closestFishingSpot);
				return 1000;
			} else {
				System.out.println("No valid fishing spots found.");
				return -1;
			}
		}
	}

	private boolean isInFishingSpots(WorldPoint location) {
		for (WorldPoint fishingSpot : FISHING_SPOTS) {
			if (fishingSpot.equals(location)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInFishingSpotsForNPC(NPC npc) {
		return isInFishingSpots(npc.getWorldLocation());
	}

	private WorldPoint getClosestFishingSpot() {
		WorldPoint playerLocation = Players.getLocal().getWorldLocation();
		WorldPoint closestSpot = null;
		int shortestDistance = Integer.MAX_VALUE;

		for (WorldPoint fishingSpot : FISHING_SPOTS) {
			int distance = playerLocation.distanceTo(fishingSpot);
			if (distance < shortestDistance) {
				shortestDistance = distance;
				closestSpot = fishingSpot;
			}
		}

		return closestSpot;
	}
}