package me.drmarky.hideandseek;

import com.intellectualcrafters.plot.commands.CommandCategory;
import com.intellectualcrafters.plot.commands.MainCommand;
import com.intellectualcrafters.plot.commands.RequiredType;
import com.intellectualcrafters.plot.commands.SubCommand;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.general.commands.CommandDeclaration;
import me.drmarky.hideandseek.Tasks.RegisterPlayers;
import me.drmarky.hideandseek.Tasks.StartGame;
import me.drmarky.hideandseek.Tasks.StopGame;
import me.drmarky.hideandseek.Utilities.Data;
import me.drmarky.hideandseek.Utilities.Utils;
import org.bukkit.ChatColor;

@CommandDeclaration(
        command = "hideandseek",
        permission = "plots.hideandseek.start",
        aliases = {"hidenseek"},
        description = "Starts a hide and seek game on the plot",
        usage = "/plot hideandseek <minutes> or /plot hideandseek stop",
        category = CommandCategory.APPEARANCE,
        requiredType = RequiredType.PLAYER)

public class HideAndSeekCommand extends SubCommand {

    private final RegisterPlayers registerPlayers;
    private final StartGame startGame;
    private final StopGame stopGame;

    public HideAndSeekCommand(RegisterPlayers registerPlayers, StartGame startGame, StopGame stopGame) {
        MainCommand.getInstance().addCommand(this);
        this.registerPlayers = registerPlayers;
        this.startGame = startGame;
        this.stopGame = stopGame;
    }

    @Override
    public boolean onCommand(PlotPlayer plotPlayer, String[] args) {

        Plot plot = plotPlayer.getCurrentPlot();

        // CHECK that the player is the plot owner or is added on the plot.
        if (!(plot.isOwner(plotPlayer.getUUID()) || plot.isAdded(plotPlayer.getUUID()))) {
            Utils.sendSpacedMessage(plotPlayer, "You must be the plot owner or an added user on a claimed plot in order to start or stop a game on it.");
            return true;
        }

        // CHECK that they enter one argument.
        if (args.length != 1) {
            Utils.sendSpacedMessage(plotPlayer, "Please specify how many minutes you would like the match to last or enter " + ChatColor.GOLD + "/p hideandseek stop " + ChatColor.GRAY + "to end a game that has already started.");
            return true;
        }

        // CHECK to see whether they want to start a game or want to stop a game.
        int mins = 0;
        if (args[0].matches("[0-9]+")) { // If true, they have entered a valid number of minutes to start the game.

            mins = Integer.valueOf(args[0]);
            // CHECK  that the player has sufficient permissions.
            if (!(plotPlayer.hasPermission("plots.hideandseek.start"))) {
                Utils.sendSpacedMessage(plotPlayer, "You do not have sufficient permissions to start a game of hide and seek.");
                return true;
            }

            // CHECK that there enough players in the plot.
            if (plot.getPlayersInPlot().size() < 2) {
                Utils.sendSpacedMessage(plotPlayer, "There must be at least two players inside the plot to start a game of hide and seek.");
                return true;
            }

            if (Data.plotsInPlay.contains(plot)) {
                Utils.sendSpacedMessage(plotPlayer, "A game has already started in this plot. Please wait until it ends or stop it using" + ChatColor.GOLD + " /plots hideandseek stop" + ChatColor.GRAY + ".");
                return true;
            }

            /*
            THIS IS WHERE THE START GAME PROCEDURE IS!
            */

            Data.gameTime.put(plot, mins);
            registerPlayers.registerPlayers(plot);
            startGame.startGame(plot);

            return true;

        } else {

            // CHECK that they specified a proper sub-sub-command.
            if (!(args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("cancel"))) {
                Utils.sendSpacedMessage(plotPlayer, "Please specify how many minutes you would like the match to last or enter " + ChatColor.GOLD + "/p hideandseek stop " + ChatColor.GRAY + "to end a game that has already started.");
                return true;
            }

            // CHECK that the player has sufficient permissions.
            if (!(plotPlayer.hasPermission("plots.hideandseek.stop"))) {
                Utils.sendSpacedMessage(plotPlayer, "You do not have sufficient permissions to start a game of hide and seek.");
                return true;
            }

            if (!(Data.plotsInPlay.contains(plot))) {
                Utils.sendSpacedMessage(plotPlayer, "You cannot stop a game that has yet to start.");
                return true;
            }

            /*
            THIS IS WHERE THE STOP GAME PROCEDURE IS!
             */

            stopGame.stopGame(plot, true);
            return true;
        }
    }
}
