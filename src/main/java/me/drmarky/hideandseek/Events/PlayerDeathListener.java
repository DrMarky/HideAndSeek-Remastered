package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Tasks.GenerateWinner;
import me.drmarky.hideandseek.Tasks.StopGame;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final GenerateWinner generateWinner;
    private final StopGame stopGame;

    public PlayerDeathListener(GenerateWinner generateWinner, StopGame stopGame) {
        this.generateWinner = generateWinner;
        this.stopGame = stopGame;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {

        Player player = e.getEntity();
        PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

        if (Data.directory.containsKey(plotPlayer)) {
            Plot plot = Data.directory.get(plotPlayer).plot;
            Utils.sendListMessage(Utils.getPlayers(plot), ChatColor.GOLD + plotPlayer.getName() + ChatColor.GRAY + " has left the game.");
            Utils.removePlayer(plotPlayer);
            generateWinner.generateWinner(plot);
        }
    }
}
