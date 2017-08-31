package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.PlayerObject;
import me.drmarky.hideandseek.Utilities.Team;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tag {

    private final GenerateWinner generateWinner;

    public Tag(GenerateWinner generateWinner) {
        this.generateWinner = generateWinner;
    }

    public void tag(PlotPlayer hider, PlotPlayer seeker, Plot plot) {

        if (Data.directory.get(hider) != null && Data.directory.get(seeker) != null) {
            if (Data.directory.get(hider).plot.equals(Data.directory.get(seeker).plot)) {
                if (Data.directory.get(hider).team.equals(Team.HIDER)) {
                    if (Data.directory.get(seeker).team.equals(Team.SEEKER)) {
                        if (!(Data.frozen.contains(seeker.getUUID()))) {
                            Player hiderPlayer = Bukkit.getPlayer(hider.getUUID());

                            hider.teleport(hider.getCurrentPlot().getHome());
                            hiderPlayer.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (byte) 14));
                            Utils.quickenPlayer(hiderPlayer, Data.gameTime.get(plot));
                            Data.directory.put(hider, new PlayerObject(Team.SEEKER, plot));
                            Utils.sendListMessage(Utils.getPlayers(plot), ChatColor.GOLD + seeker.getName() + ChatColor.GRAY + " has found " + ChatColor.GOLD + hider.getName() + ChatColor.GRAY + ". " + ChatColor.GOLD + Utils.getHiders(plot).size() + ChatColor.GRAY + " hiders remain.");

                            generateWinner.generateWinner(plot);
                        }
                    }
                }
            }
        }
    }

}
