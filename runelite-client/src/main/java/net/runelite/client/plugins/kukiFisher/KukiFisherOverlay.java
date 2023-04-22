package net.runelite.client.plugins.kukiFisher;

import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.plugins.kukiFisher.KukiFisher;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.unethicalite.api.game.Skills;


import javax.inject.Inject;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KukiFisherOverlay extends Overlay implements MouseListener
{
    private final Client client;
    private final KukiFisher plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public KukiFisherOverlay(Client client, KukiFisher plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        panelComponent.getChildren().clear();
        String leftText = "Kuki Fisher";
        String rightText = "Current fishing level: " + Skills.getLevel(Skill.FISHING);

        panelComponent.getChildren().add(LineComponent.builder()
                .left(leftText)
                .right(rightText)
                .rightColor(Color.GREEN)
                .build());

        // Calculate the width required for the text
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int leftTextWidth = fontMetrics.stringWidth(leftText);
        int rightTextWidth = fontMetrics.stringWidth(rightText);

        // Add some padding to the width
        int padding = 20;

        // Set the preferred size of the panelComponent
        panelComponent.setPreferredSize(new Dimension(leftTextWidth + rightTextWidth + padding, panelComponent.getPreferredSize().height));

        return panelComponent.render(graphics);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle click event here
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    private boolean running = false;

    public boolean isRunning()
    {
        return running;
    }

    public void toggleRunning()
    {
        running = !running;
    }
}
