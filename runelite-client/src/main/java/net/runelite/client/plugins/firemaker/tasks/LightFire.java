package net.runelite.client.plugins.firemaker.tasks;

import net.runelite.api.Point;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.entities.Players;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Skills;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.client.Static;
import net.unethicalite.client.managers.RegionManager;

import java.util.concurrent.atomic.AtomicInteger;

public class LightFire implements ScriptTask {

	private static final WorldPoint START_TILE = new WorldPoint(3393, 3265, 0);
	private static final WorldPoint SECOND_START_TILE = new WorldPoint(3383, 3266, 0);
	private static final int[] logs = {1511, 1519, 1521, 1517};

	@Override
	public boolean validate() {
		return Inventory.contains(logs);
	}

	@Override
	public int execute() {
		if (!atStartTile() && !Players.getLocal().isMoving()) {
			WorldPoint targetTile = getClosestStartTile();
			Movement.walkTo(targetTile);
			return 1000;
		}

		if (Players.getLocal().isIdle()) {
			AtomicInteger startingXP = new AtomicInteger(Skills.getExperience(Skill.FIREMAKING));
			boolean hasLogs = Inventory.contains(logs);
			while (hasLogs) {
				Inventory.getFirst("Tinderbox").useOn(Inventory.getFirst(logs));
				Time.sleepUntil(() -> Skills.getExperience(Skill.FIREMAKING) > startingXP.get(), 10000);
				startingXP.set(Skills.getExperience(Skill.FIREMAKING));
				hasLogs = Inventory.contains(logs);
			}
			return 1000;
		}

		return 1000;
	}

	private boolean atStartTile() {
		WorldPoint playerLocation = Players.getLocal().getWorldLocation();
		return playerLocation.equals(START_TILE) || playerLocation.equals(SECOND_START_TILE);
	}

	private WorldPoint getClosestStartTile() {
		TileObject nearestFire = TileObjects.getNearest("Fire");
		if (nearestFire != null) {
			int fireY = nearestFire.getWorldLocation().getWorldY();
			if (fireY == 3266) {
				return START_TILE;
			} else if (fireY == 3265) {
				return SECOND_START_TILE;
			}
		}

		WorldPoint playerLocation = Players.getLocal().getWorldLocation();
		int distanceToStartTile = playerLocation.distanceTo(START_TILE);
		int distanceToSecondStartTile = playerLocation.distanceTo(SECOND_START_TILE);

		return distanceToStartTile < distanceToSecondStartTile ? START_TILE : SECOND_START_TILE;
	}
}