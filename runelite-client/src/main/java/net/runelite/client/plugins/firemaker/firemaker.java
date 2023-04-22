package net.runelite.client.plugins.firemaker;

import net.runelite.api.Client;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.firemaker.tasks.LightFire;
import net.unethicalite.api.plugins.Script;
import net.runelite.client.plugins.firemaker.tasks.Bank;
import net.runelite.client.plugins.firemaker.tasks.ScriptTask;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;

// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "Kuki firemaker", enabledByDefault = false)
@Extension
public class firemaker extends Script

{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new Bank(),
			new LightFire()
	};

	private boolean shouldLight = false;

	@Inject
	private Client client;

	@Inject
	private EventBus eventBus;


	@Override
	public void onStart(String... args)
	{
	}




	@Override
	protected int loop()
	{
		// Here I use task-based logic. You can also just write the entire script logic
		for (ScriptTask task : TASKS)
		{
			if (task.validate())
			{
				// Perform the task and store the sleep value
				int sleep = task.execute();
				// If this task blocks the next task, return the sleep value and the internal loop will sleep for this amount of time
				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return 1000;
	}
}
