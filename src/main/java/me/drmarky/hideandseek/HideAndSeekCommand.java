package me.drmarky.hideandseek;

import com.intellectualcrafters.plot.commands.CommandCategory;
import com.intellectualcrafters.plot.commands.MainCommand;
import com.intellectualcrafters.plot.commands.RequiredType;
import com.intellectualcrafters.plot.commands.SubCommand;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.general.commands.CommandDeclaration;

@CommandDeclaration(
        command = "hideandseek",
        permission = "plots.hideandseek.start",
        aliases = {"hidenseek"},
        description = "Starts a hide and seek game on the plot",
        usage = "/plot hideandseek <minutes> or /plot hideandseek stop",
        category = CommandCategory.APPEARANCE,
        requiredType = RequiredType.PLAYER)

public class HideAndSeekCommand extends SubCommand {

    public HideAndSeekCommand() {
        MainCommand.getInstance().addCommand(this);
    }

    @Override
    public boolean onCommand(PlotPlayer plotPlayer, String[] args) {
        return true;
    }
}
