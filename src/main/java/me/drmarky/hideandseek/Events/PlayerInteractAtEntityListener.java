package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Tasks.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerInteractAtEntityListener implements Listener {

    private final Tag tag;

    public PlayerInteractAtEntityListener(Tag tag) {
        this.tag = tag;
    }

    @EventHandler
    public void onPlayerInteractAtEntity(org.bukkit.event.player.PlayerInteractAtEntityEvent e) {

        if (e.getRightClicked() instanceof Player) {
            tag.tag(PlotPlayer.get(e.getRightClicked().getName()), PlotPlayer.get(e.getPlayer().getName()), PlotPlayer.get(e.getRightClicked().getName()).getCurrentPlot());
        }
    }
}
