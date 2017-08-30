package me.drmarky.hideandseek.Events;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.PlotGameMode;
import me.drmarky.hideandseek.Tasks.GenerateWinner;
import me.drmarky.hideandseek.Tasks.StopGame;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final GenerateWinner generateWinner;
    private final StopGame stopGame;

    public PlayerMoveListener(GenerateWinner generateWinner, StopGame stopGame) {
        this.generateWinner = generateWinner;
        this.stopGame = stopGame;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();
        PlotPlayer plotPlayer = PlotPlayer.get(player.getName());

        // CHECK that they're not supposed to be frozen while the hiders hide
        if (Data.frozen.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }

        // CHECK that they left the plot
        if (Data.directory.containsKey(plotPlayer)) {
            Plot plot = Data.directory.get(plotPlayer).plot;
            if (!(plot.getPlayersInPlot().contains(plotPlayer))) {
                Utils.sendListMessage(Utils.getPlayers(plot), ChatColor.GOLD + plotPlayer.getName() + ChatColor.GRAY + " has left the game.");

                player.setAllowFlight(true);
                Utils.revertTempHelmet(player);
                Utils.clearEffects(player);

                if (Data.frozen.contains(plotPlayer.getUUID())) {
                    Data.frozen.remove(plotPlayer.getUUID());
                }

                Data.directory.remove(plotPlayer);
                plotPlayer.setGameMode(PlotGameMode.CREATIVE);
                generateWinner.generateWinner(plot);
                stopGame.stopGame(plot, false);
            }
        }
    }

}
