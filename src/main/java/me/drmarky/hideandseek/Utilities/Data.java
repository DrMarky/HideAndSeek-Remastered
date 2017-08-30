package me.drmarky.hideandseek.Utilities;

import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Data {

    // Stores all of the players playing Hide and Seek and their team and plot.
    public static HashMap<PlotPlayer, PlayerObject> directory = new HashMap<>();

    // Stores all of the plots currently hosting a Hide and Seek Game.
    public static ArrayList<Plot> plotsInPlay = new ArrayList<>();

    // Stores all of the seekers who are frozen.
    public static ArrayList<UUID> frozen = new ArrayList<>();

    // Stores how long the game on each plot will run for
    public static HashMap<Plot, Integer> gameTime = new HashMap<>();

    // Stores the players original helmet
    public static HashMap<Player, ItemStack> hat = new HashMap<>();

    // Stores the delayed tasks for each plot
    public static HashMap<Plot, Integer> releaseSeekerMap = new HashMap<>();
    public static HashMap<Plot, Integer> warnReleaseSeekerMap = new HashMap<>();
    public static HashMap<Plot, Integer> endGameMap = new HashMap<>();
    public static HashMap<Plot, Integer> warnEndGameMap = new HashMap<>();
}
