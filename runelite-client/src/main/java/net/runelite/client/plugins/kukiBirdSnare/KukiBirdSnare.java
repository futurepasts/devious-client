package net.runelite.client.plugins.kukiBirdSnare;



import net.runelite.client.plugins.kukiBirdSnare.tasks.CatchBird;
import net.runelite.client.plugins.kukiBirdSnare.tasks.Drop;
import net.runelite.client.plugins.kukiBirdSnare.tasks.*;
import net.unethicalite.api.plugins.Script;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;


// This annotation is required in order for the client to detect it as a plugin/script.
@PluginDescriptor(name = "Kuki Bird snare", enabledByDefault = false)
@Extension
public class KukiBirdSnare extends Script
{
	private static final ScriptTask[] TASKS = new ScriptTask[]{
			new CatchBird(),
			new Drop()
	};

	private boolean shouldLight = false;

	/**
	 * Gets executed whenever a script starts.
	 * Can be used to for example initialize script settings, or perform tasks before starting the loop logic.
	 *
	 * @param args any script arguments passed to the script, separated by spaces.
	 */

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