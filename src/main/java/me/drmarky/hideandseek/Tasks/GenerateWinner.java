package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.PlotGameMode;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class GenerateWinner {
    
    public void generateWinner(Plot plot) {
        
        if (Utils.getHiders(plot).size() == 0 || Utils.getSeekers(plot).size() == 0) {

            if (Utils.getHiders(plot).size() == 0) {
                Utils.sendListMessage(Utils.getPlayers(plot), "The " + ChatColor.RED + "seeker " + ChatColor.GRAY + "team has won!");
            } else if (Utils.getSeekers(plot).size() == 0) {
                Utils.sendListMessage(Utils.getPlayers(plot), "The " + ChatColor.BLUE + "hider " + ChatColor.GRAY + "team has won!");
            }

            BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

            if (Data.releaseSeekerMap.containsKey(plot)) {
                scheduler.cancelTask(Data.releaseSeekerMap.get(plot));
                Data.releaseSeekerMap.remove(plot);
            }

            if (Data.warnReleaseSeekerMap.containsKey(plot)) {
                scheduler.cancelTask(Data.warnReleaseSeekerMap.get(plot));
                Data.warnReleaseSeekerMap.remove(plot);
            }

            if (Data.endGameMap.containsKey(plot)) {
                scheduler.cancelTask(Data.endGameMap.get(plot));
                Data.endGameMap.remove(plot);
            }

            if (Data.warnEndGameMap.containsKey(plot)) {
                scheduler.cancelTask(Data.warnEndGameMap.get(plot));
                Data.warnEndGameMap.remove(plot);
            }

            for (PlotPlayer plotPlayer : Utils.getPlayers(plot)) {

                Player player = Bukkit.getPlayer(plotPlayer.getUUID());

                player.setAllowFlight(true);
                Utils.revertTempHelmet(player);
                Utils.clearEffects(player);

                if (Data.frozen.contains(plotPlayer.getUUID())) {
                    Data.frozen.remove(plotPlayer.getUUID());
                }

                Data.directory.remove(plotPlayer);
                plotPlayer.setGameMode(PlotGameMode.CREATIVE);
            }

            Data.plotsInPlay.remove(plot);
        }
    }

}
