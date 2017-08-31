package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGameModeChangeListener implements Listener {

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        PlotPlayer plotPlayer = PlotPlayer.get(e.getPlayer().getName());
        if (Data.directory.containsKey(plotPlayer)) {
            if (!(e.getNewGameMode() == GameMode.ADVENTURE)) {
                e.setCancelled(true);
            }
        }
    }
}
