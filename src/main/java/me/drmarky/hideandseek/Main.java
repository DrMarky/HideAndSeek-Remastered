package me.drmarky.hideandseek;

import me.drmarky.hideandseek.Events.*;
import me.drmarky.hideandseek.Tasks.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        logger.info(pdfFile.getName() + " has been enabled. (V." + pdfFile.getVersion() + ")");

        registerEvents();

        new HideAndSeekCommand(new RegisterPlayers(), new StartGame(this, new StopGame()), new StopGame(), new ListPlayers());
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();

        logger.info(pdfFile.getName() + " has been disabled. (V." + pdfFile.getVersion() + ")");
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerMoveListener(new GenerateWinner(new StopGame()), new StopGame()), this);
        pm.registerEvents(new PlayerQuitListener(new GenerateWinner(new StopGame()), new StopGame()), this);
        pm.registerEvents(new PlayerTeleportListener(new GenerateWinner(new StopGame()), new StopGame()), this);
        pm.registerEvents(new PlayerDeathListener(new GenerateWinner(new StopGame()), new StopGame()), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(new Tag(new GenerateWinner(new StopGame()))), this);
        pm.registerEvents(new EntityDamageEntityListener(new Tag(new GenerateWinner(new StopGame()))), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerGameModeChangeListener(), this);
    }

}
