package net.runelite.client.Kcooker.tasks;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import net.unethicalite.api.plugins.Task;
import net.runelite.client.Kcooker.CookerPlugin;

@RequiredArgsConstructor
public abstract class CookerTask implements Task
{
	@Delegate
	private final CookerPlugin context;

	protected int taskCooldown;
}
