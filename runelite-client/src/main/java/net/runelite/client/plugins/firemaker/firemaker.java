package net.runelite.client.plugins.firemaker;

import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.firemaker.tasks.*;
import net.unethicalite.api.plugins.Script;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;




// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "Kuki firemaker", enabledByDefault = false)
@Extension
public class firemaker extends Script


{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new Banking(),
			new LightFire(),
	};

	private boolean isRunning = false;
	private boolean shouldLight = false;

	@Inject
	private Client client;

	@Inject
	private EventBus eventBus;
	@Inject
	private firemakerConfig config;

	@Provides
	private firemakerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(firemakerConfig.class);
	}

	@Override
	public void onStart(String... args)
	{
		isRunning = true;
	}

	@Override
	protected void shutDown()
	{
	}


	@Override
	protected int loop()
	{
		if (!isRunning) {
			return 0;
		}
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
