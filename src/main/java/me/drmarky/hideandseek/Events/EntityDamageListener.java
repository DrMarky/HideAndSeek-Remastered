package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            PlotPlayer plotPlayer = PlotPlayer.get(e.getEntity().getName());
            if (Data.directory.containsKey(plotPlayer)) {
                e.setCancelled(true);
            }
        }
    }

}
