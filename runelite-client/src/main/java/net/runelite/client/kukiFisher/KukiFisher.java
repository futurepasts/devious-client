package net.runelite.client.kukiFisher;



import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.plugins.kukiFisher.tasks.Drop;
import net.runelite.client.plugins.kukiFisher.tasks.FishingTask;
import net.runelite.client.plugins.kukiFisher.tasks.ScriptTask;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.plugins.Script;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;


// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "Kuki Fisher", enabledByDefault = false)
@Extension
public class KukiFisher extends Script
{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new FishingTask(),
			new Drop()
	};
	@Inject
	private KukiFisherOverlay kukiFisherOverlay;

	@Inject
	private OverlayManager overlayManager;
	private boolean running;

	@Inject
	private Client client;

	@Inject
	private KukiFisherOverlay overlay;


	@Override
	public void onStart(String... args)
	{

		overlayManager.add(kukiFisherOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(kukiFisherOverlay);
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
