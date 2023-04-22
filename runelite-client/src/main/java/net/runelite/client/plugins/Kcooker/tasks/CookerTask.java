package net.runelite.client.plugins.Kcooker.tasks;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import net.unethicalite.api.plugins.Task;
import net.runelite.client.plugins.Kcooker.CookerPlugin;

@RequiredArgsConstructor
public abstract class CookerTask implements Task
{
	@Delegate
	private final CookerPlugin context;

	protected int taskCooldown;
}
