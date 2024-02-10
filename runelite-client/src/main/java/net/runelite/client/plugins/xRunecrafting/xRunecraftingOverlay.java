package net.runelite.client.plugins.xRunecrafting;

import com.openosrs.client.ui.overlay.components.table.TableAlignment;
import com.openosrs.client.ui.overlay.components.table.TableComponent;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static net.runelite.client.ui.overlay.OverlayManager.OPTION_CONFIGURE;

public class xRunecraftingOverlay extends Overlay
{
    private final Client client;
    private final xRunecraftingPlugin plugin;
    private final xRunecraftingConfig config;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    private xRunecraftingOverlay(Client client, xRunecraftingPlugin plugin, xRunecraftingConfig config)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;

        this.setPriority(OverlayPriority.HIGHEST);
        this.setPosition(OverlayPosition.BOTTOM_LEFT);
        this.getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "xRunecrafting Overlay"));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin == null)
            return null;

        panelComponent.getChildren().clear();

        TableComponent tableComponent = new TableComponent();
        tableComponent.setColumnAlignments(TableAlignment.LEFT);
        tableComponent.setDefaultColor(Color.ORANGE);

        tableComponent.addRow("X-HOOK xRunecrafting Plugin");
        tableComponent.addRow("Status: " + plugin.status, " State: " + plugin.CurrentTaskStatus);
        long end = System.currentTimeMillis() - plugin.start;

        DateFormat df = new SimpleDateFormat("HH 'H', mm 'M,' ss 'S'");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        tableComponent.addRow("Time running: " + df.format(new Date(end)));

        long RoundsPerHour = (int)(plugin.TotalTrips / ((System.currentTimeMillis() - plugin.start) / 3600000.0D));
        tableComponent.addRow("Trips: " + plugin.TotalTrips, " Trips per hour:" + RoundsPerHour);
        long XPPerHour = (int) (plugin.CurrentXP / ((System.currentTimeMillis() - plugin.start) / 3600000.0D));
        tableComponent.addRow("XP Gained: " + plugin.CurrentXP, "XP Per hr: " + XPPerHour);


        if (!tableComponent.isEmpty()) {
            panelComponent.getChildren().add(tableComponent);
        }

        panelComponent.setPreferredSize(new Dimension(350, 100));
        panelComponent.setBackgroundColor(Color.darkGray);

        return panelComponent.render(graphics);
    }
}