package me.drmarky.hideandseek.Utilities;

import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.PlotGameMode;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Utils {

    // Sends a message that has a blank line before and after it as well as a PlotSquared prefix.
    public static void sendSpacedMessage(PlotPlayer plotPlayer, String string) {
        plotPlayer.sendMessage("\n" + C.PREFIX + string + "\n ");
    }

    // Sends a message to a list of players.
    public static void sendListMessage(ArrayList<PlotPlayer> list, String string) {
        for (PlotPlayer plotPlayer : list) {
            plotPlayer.sendMessage("\n" + C.PREFIX + string + "\n ");
        }
    }

    // Gets the players in-game in a specified plot.
    public static ArrayList<PlotPlayer> getPlayers(Plot plot) {

        ArrayList<PlotPlayer> players = new ArrayList<>();

        Set<Map.Entry<PlotPlayer, PlayerObject>> entries = Data.directory.entrySet();
        Iterator<Map.Entry<PlotPlayer, PlayerObject>> itr = entries.iterator();

        while (itr.hasNext()) {
            PlotPlayer plotPlayer = itr.next().getKey();
            PlayerObject playerObject = Data.directory.get(plotPlayer);

            if (playerObject.plot.equals(plot)) {
                players.add(plotPlayer);
            }
        }
        return players;
    }

    // Gets the seekers in a specified plot.
    public static ArrayList<PlotPlayer> getSeekers(Plot plot) {

        ArrayList<PlotPlayer> seekers = new ArrayList<>();

        Set<Map.Entry<PlotPlayer, PlayerObject>> entries = Data.directory.entrySet();
        Iterator<Map.Entry<PlotPlayer, PlayerObject>> itr = entries.iterator();

        while (itr.hasNext()) {
            PlotPlayer plotPlayer = itr.next().getKey();
            PlayerObject playerObject = Data.directory.get(plotPlayer);

            if (playerObject.plot.equals(plot) && playerObject.team.equals(Team.SEEKER)) {
                seekers.add(plotPlayer);
            }
        }
        return seekers;
    }

    // Gets the hiders in a specified plot.
    public static ArrayList<PlotPlayer> getHiders(Plot plot) {

        ArrayList<PlotPlayer> hiders = new ArrayList<>();

        Set<Map.Entry<PlotPlayer, PlayerObject>> entries = Data.directory.entrySet();
        Iterator<Map.Entry<PlotPlayer, PlayerObject>> itr = entries.iterator();

        while (itr.hasNext()) {
            PlotPlayer plotPlayer = itr.next().getKey();
            PlayerObject playerObject = Data.directory.get(plotPlayer);

            if (playerObject.plot == plot && playerObject.team == Team.HIDER) {
                hiders.add(plotPlayer);
            }
        }
        return hiders;
    }

    // Sets the player a new helmet and stores their current one
    public static void setTempHelmet(Player player, ItemStack helmet) {
        if (player.getInventory().getHelmet() != null) {
            Data.hat.put(player, player.getInventory().getHelmet());
        }
        player.getInventory().setHelmet(helmet);
    }

    // Gives back the player their original helmet
    public static void revertTempHelmet(Player player) {
        if (Data.hat.get(player) != null) {
            player.getInventory().setHelmet(Data.hat.get(player));
            Data.hat.remove(player);
        } else {
            player.getInventory().setHelmet(null);
        }
    }

    // Clears a players potion effects
    public static void clearEffects(Player player) {
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }

    // Blinds the player for a specified amount of seconds (duration)
    public static void blindPlayer(Player player, int duration) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration * 20, 100, true, false, Color.BLACK));
    }

    // Gives the player speed for a specified amount of minutes (duration)
    public static void quickenPlayer(Player player, int duration) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 1200, 0, true, false, Color.BLACK));
    }

    // Reverses all the settings applied to the player when they joined the game
    public static void removePlayer(PlotPlayer plotPlayer) {
        Player player = Bukkit.getPlayer(plotPlayer.getUUID());

        player.setAllowFlight(true);
        Utils.revertTempHelmet(player);
        Utils.revertElytra(player);
        Utils.clearEffects(player);

        if (Data.frozen.contains(plotPlayer.getUUID())) {
            Data.frozen.remove(plotPlayer.getUUID());
        }

        Data.directory.remove(plotPlayer);
        plotPlayer.setGameMode(PlotGameMode.CREATIVE);
    }

    // Takes the players elytra so that it may layer be returned
    public static void removeElytra(Player player) {
        if (player.getInventory().getChestplate() != null) {
            if (player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
                Data.elytra.put(player, player.getInventory().getChestplate());
                player.getInventory().setChestplate(null);
            }
        }
    }

    // Gives the player back their elytra
    public static void revertElytra(Player player) {
        if (Data.elytra.containsKey(player)) {
            player.getInventory().setChestplate(Data.elytra.get(player));
            Data.elytra.remove(player);
        }
    }

}
