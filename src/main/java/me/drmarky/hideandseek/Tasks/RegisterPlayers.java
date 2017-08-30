package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.PlayerObject;
import me.drmarky.hideandseek.Utilities.Team;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class RegisterPlayers {

    public void registerPlayers(Plot plot) {

        ArrayList<PlotPlayer> players = (ArrayList<PlotPlayer>) plot.getPlayersInPlot();

        for (PlotPlayer plotPlayer : players) {
            Player player = Bukkit.getPlayer(plotPlayer.getUUID());

            // CHECK that the player has sufficient permissions
            if (!(plotPlayer.hasPermission("plots.hideandseek.play") || player.isOp())) {
                players.remove(plotPlayer);
            }
        }

        // Register seeker
        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(players.size());
        PlotPlayer seeker = players.get(i);
        Data.directory.put(seeker, new PlayerObject(Team.SEEKER, plot));
        Utils.sendSpacedMessage(seeker, "You have been chosen as the first " + ChatColor.RED + "seeker" + ChatColor.GRAY + ". Right click the " + ChatColor.BLUE + "hiders " + ChatColor.GRAY + "to turn them into seekers.");
        players.remove(seeker);

        // Register hiders
        for (PlotPlayer hider : players) {
            Data.directory.put(hider, new PlayerObject(Team.HIDER, plot));
            Utils.sendSpacedMessage(hider, "You have been added to the " + ChatColor.BLUE + "hider " + ChatColor.GRAY + "team. Hide from the " + ChatColor.RED + "seekers " + ChatColor.GRAY + "to avoid being tagged. If you are tagged you will become a " + ChatColor.RED + "seeker" + ChatColor.GRAY + ".");
        }

    }



}
