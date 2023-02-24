package pl.pijok.skyblock.skyblockPlayer;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import pl.pijok.skyblock.Controllers;
import pl.pijok.skyblock.JsonUtils;
import pl.pijok.skyblock.SkyBlock;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class SkyBlockPlayerController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final SkyBlock plugin;
    private final HashMap<UUID, SkyBlockPlayer> skyBlockPlayers;

    public SkyBlockPlayerController(SkyBlock plugin){
        this.plugin = plugin;
        this.skyBlockPlayers = new HashMap<>();
    }

    public void loadPlayer(Player player){
        logger.info("Loading player " + player.getName());
        File file = new File(plugin.getDataFolder() + File.separator + "skyblockPlayers" + File.separator + player.getName() + ".json");
        if(!file.exists()){
            skyBlockPlayers.put(player.getUniqueId(), new SkyBlockPlayer(UUID.randomUUID(), null));
            return;
        }

        SkyBlockPlayer skyBlockPlayer = (SkyBlockPlayer) JsonUtils.loadObject(file, SkyBlockPlayer.class);
        skyBlockPlayers.put(player.getUniqueId(), skyBlockPlayer);
    }

    public void savePlayer(Player player){
        logger.info("Saving player " + player.getName());

        File file = new File(plugin.getDataFolder() + File.separator + "skyblockPlayers" + File.separator + player.getName() + ".json");
        JsonUtils.saveObject(file,  getPlayer(player.getUniqueId()));
        skyBlockPlayers.remove(player.getUniqueId());
    }

    public void verifyPlayer(Player player){
        logger.info("Verifying player " + player.getName());

        SkyBlockPlayer skyBlockPlayer = skyBlockPlayers.get(player.getUniqueId());

        if(skyBlockPlayer.hasIslandOrIsMember()){
            if(Controllers.getIslandController().islandDeleted(skyBlockPlayer.getIslandID())){
                skyBlockPlayer.setIslandID(null);
            }
        }
    }

    public HashMap<UUID, SkyBlockPlayer> getSkyBlockPlayers() {
        return skyBlockPlayers;
    }

    public SkyBlockPlayer getPlayer(Player player){
        return skyBlockPlayers.get(player.getUniqueId());
    }

    public SkyBlockPlayer getPlayer(UUID uuid){
        return skyBlockPlayers.getOrDefault(uuid, null);
    }
}
