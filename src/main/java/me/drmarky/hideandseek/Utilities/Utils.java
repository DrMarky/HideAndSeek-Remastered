package me.drmarky.hideandseek.Utilities;

import com.intellectualcrafters.plot.config.C;
import com.intellectualcrafters.plot.object.PlotPlayer;

public class Utils {

    public static void sendSpacedMessage(PlotPlayer plotPlayer, String string) {
        plotPlayer.sendMessage("\n" + C.PREFIX + string + "\n ");
    }

}
