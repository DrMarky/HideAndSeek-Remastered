package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (Data.directory.containsKey(PlotPlayer.get(e.getWhoClicked().getName()))) {
            e.setCancelled(true);
        }
    }

}
