package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class StopGame {

    public void stopGame(Plot plot, Boolean announce) {

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

            if (announce) {
                Utils.sendSpacedMessage(plotPlayer, "The game you were playing has been canceled.");
            }

            Utils.removePlayer(plotPlayer);
        }

        Data.plotsInPlay.remove(plot);
    }

}