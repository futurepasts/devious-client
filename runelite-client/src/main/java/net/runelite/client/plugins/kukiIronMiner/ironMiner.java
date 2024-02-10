package net.runelite.client.plugins.kukiIronMiner;

import net.runelite.api.Client;
import net.runelite.client.plugins.kukiIronMiner.tasks.DropIron;
import net.runelite.client.plugins.kukiIronMiner.tasks.MineIron;
import net.runelite.client.plugins.kukiIronMiner.tasks.ScriptTask;
import net.runelite.client.ui.overlay.OverlayManager;
import net.unethicalite.api.plugins.Script;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

import javax.inject.Inject;



// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "Kuki IronMiner", enabledByDefault = false)
@Extension
public class ironMiner extends Script


{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new DropIron(),
			new MineIron(),
	};




	@Inject
	private net.runelite.client.plugins.kukiIronMiner.KukiIronOverlay KukiIronOverlay;

	@Inject
	private Client client;


	@Inject
	private OverlayManager overlayManager;


	@Override
	public void onStart(String... args)
	{

		overlayManager.add(KukiIronOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(KukiIronOverlay);
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
