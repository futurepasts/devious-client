package net.runelite.client.plugins.kukiIronMiner;

import net.runelite.api.Client;
import net.runelite.api.Skill;
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

import static net.runelite.client.plugins.kukiIronMiner.tasks.MineIron.Breaks;

public class KukiIronOverlay extends Overlay implements MouseListener
{
    private final Client client;

    private final ironMiner plugin;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    public KukiIronOverlay(Client client, ironMiner plugin)
    {
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Point mouse = client.getMouseCanvasPosition().getAwtPoint();

        Graphics2D g2d = (Graphics2D) graphics.create();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect((int) mouse.getX(), (int) mouse.getY(), 15, 15);
        g2d.dispose();

        panelComponent.getChildren().clear();
        String leftText = "Kuki IronMiner";
        String rightText = "Current mining  level: " + Skills.getLevel(Skill.MINING);
        String bottomText = "Did AFK breaks: " + Breaks;

        panelComponent.getChildren().add(LineComponent.builder()
                .left(leftText)
                .right(rightText)
                .left(bottomText)
                .rightColor(Color.GREEN)
                .build());

        // Calculate the width required for the text
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int leftTextWidth = fontMetrics.stringWidth(leftText);
        int rightTextWidth = fontMetrics.stringWidth(rightText);
        int bottomTextWidth = fontMetrics.stringWidth(bottomText);


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
