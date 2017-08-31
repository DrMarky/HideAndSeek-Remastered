package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Team;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.ChatColor;

public class ListPlayers {

    public void listPlayers(Plot plot, PlotPlayer player) {
        String msg = "Players: ";

        for (PlotPlayer plotPlayer : Utils.getPlayers(plot)) {
            if (Data.directory.get(plotPlayer).team.equals(Team.SEEKER)) {
                msg = msg + (ChatColor.RED + plotPlayer.getName() + " ");
            } else if (Data.directory.get(plotPlayer).team.equals(Team.HIDER)) {
                msg = msg + (ChatColor.BLUE + plotPlayer.getName() + " ");
            }
        }

        Utils.sendSpacedMessage(player, msg);
    }
}
