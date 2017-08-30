package me.drmarky.hideandseek.Tasks;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.PlotGameMode;
import me.drmarky.hideandseek.Main;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class StartGame {

    private final Main main;

    public StartGame(Main main) {
        this.main = main;
    }

    public void startGame(Plot plot) {

        int mins = Data.gameTime.get(plot);
        PlotPlayer seeker = Utils.getSeekers(plot).get(0);

        Utils.sendListMessage(Utils.getPlayers(plot), "The seeker, " + ChatColor.GOLD + seeker.getName() + ChatColor.GRAY + ", will be released in 30 seconds! The game will end in " + ChatColor.GOLD + Data.gameTime.get(plot) + ChatColor.GRAY + " minutes.");

        // SETUP SEEKER
        seeker.teleport(plot.getHome());
        seeker.setGameMode(PlotGameMode.CREATIVE);
        seeker.setFlight(true);
        Data.frozen.add(seeker.getUUID());
        Player seekerPlayer = Bukkit.getPlayer(seeker.getUUID());
        seekerPlayer.setAllowFlight(true);
        Utils.setTempHelmet(seekerPlayer, new ItemStack(Material.WOOL, 1, (byte) 14));
        Utils.clearEffects(seekerPlayer);
        Utils.blindPlayer(seekerPlayer, 30);
        Utils.quickenPlayer(seekerPlayer, mins);

        // SETUP HIDERS
        for (PlotPlayer hider : Utils.getHiders(plot)) {
            System.out.println("HIDER" + hider);
            hider.teleport(plot.getHome());
            hider.setGameMode(PlotGameMode.ADVENTURE);
            hider.setFlight(false);
            Player hiderPlayer = Bukkit.getPlayer(hider.getUUID());
            hiderPlayer.setAllowFlight(false);
            Utils.setTempHelmet(hiderPlayer, new ItemStack(Material.WOOL, 1, (byte) 11));
            Utils.clearEffects(hiderPlayer);
        }

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        int releaseSeeker = scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                Data.frozen.remove(seeker.getUUID());
                seeker.setGameMode(PlotGameMode.ADVENTURE);
                seekerPlayer.setAllowFlight(false);
                seeker.setFlight(false);
                Utils.sendListMessage(Utils.getPlayers(plot), "The seeker has been released!");
            }
        }, 30 * 20);

        Data.releaseSeekerMap.put(plot, releaseSeeker);

        int warnReleaseSeeker = scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                Utils.sendListMessage(Utils.getPlayers(plot), "The seeker will be released in 10 seconds!");
            }
        }, 20 * 20);

        Data.warnReleaseSeekerMap.put(plot, warnReleaseSeeker);

        int endGame = scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                Utils.sendListMessage(Utils.getPlayers(plot), "The hiders have won the game!");

                //TODO: ADD STOP GAME PROCEDURE


            }
        }, mins * 60 * 20);

        Data.endGameMap.put(plot, endGame);

        int warnMins = mins - 1;

        if (warnMins != 0) {
            int warnEndGame = scheduler.scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    Utils.sendListMessage(Utils.getPlayers(plot), "The game will end in one minute!");
                }
            }, warnMins * 60 * 20);

            Data.warnEndGameMap.put(plot, warnEndGame);
        }
    }

}
