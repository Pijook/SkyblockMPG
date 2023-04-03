package pl.pijok.skyblock.island;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.pijok.skyblock.*;
import pl.pijok.skyblock.skyblockPlayer.SkyBlockPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class IslandController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final SkyBlock plugin;
    private final HashMap<UUID, Island> islands;
    private final HashMap<UUID, Invite> invites;
    private final IslandMapper mapper;

    private List<UUID> deletedIslands;

    private IslandConfig islandConfig;
    private Point lastIslandLocation;

    public IslandController(SkyBlock plugin){
        this.islands = new HashMap<>();
        this.plugin = plugin;
        this.invites = new HashMap<>();
        this.deletedIslands = new ArrayList<>();
        this.mapper = new IslandMapper();
    }

    public boolean canCreateNewIsland(Player owner){
        SkyBlockPlayer skyBlockPlayer = Controllers.getSkyBlockPlayerController().getPlayer(owner);

        if(skyBlockPlayer.hasIslandOrIsMember()){
            return false;
        }
        return true;
    }

    public boolean createNewIsland(Player owner){
        lastIslandLocation.setX(lastIslandLocation.getX() + islandConfig.getIslandsDistance());

        Location center = Point.pointToLocation(lastIslandLocation);

        if(!pasteIsland(center)){
            lastIslandLocation.setX(lastIslandLocation.getX() - islandConfig.getIslandsDistance());
            logger.info("Couldn't paste island!");
            return false;
        }

        Location point1 = new Location(
                center.getWorld(),
                center.getX() - islandConfig.getIslandSize() / 2,
                center.getY(),
                center.getZ() - islandConfig.getIslandSize() / 2);

        Location point2 = new Location(
                center.getWorld(),
                center.getX() + islandConfig.getIslandSize() / 2,
                center.getY(),
                center.getZ() + islandConfig.getIslandSize() / 2);

        Island island = new Island(
                UUID.randomUUID(),
                owner.getUniqueId(),
                new ArrayList<>(),
                center,
                point1,
                point2,
                1,
                new HashMap<>()
        );

        islands.put(island.getIslandID(), island);

        SkyBlockPlayer skyBlockPlayer = Controllers.getSkyBlockPlayerController().getPlayer(owner);
        skyBlockPlayer.setIslandID(island.getIslandID());

        return true;
    }

    public void loadConfig() {
        File file = new File(plugin.getDataFolder() + File.separator + "islandConfig.json");

        if (!file.exists()) {
            logger.info("Couldn't find islands config! Creating default!");

            islandConfig = new IslandConfig(
                    new Point("test", 0, 0, 0),
                    500,
                    100,
                    10,
                    "abc.schem"
            );

            JsonUtils.saveObject(file, islandConfig);
            return;
        }

        this.islandConfig = (IslandConfig) JsonUtils.loadObject(file, IslandConfig.class);
        logger.info("Loaded island config");
        for(Field field : islandConfig.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);
                logger.info(field.getName() + " " + field.get(islandConfig));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDeletedIslands(){
        File file = new File(plugin.getDataFolder() + File.separator + "deletedIslands.json");

        if(!file.exists()){
            return;
        }

        deletedIslands = (List<UUID>) JsonUtils.loadObject(file, List.class);
    }

    public void saveDeletedIslands(){
        File file = new File(plugin.getDataFolder() + File.separator + "deletedIslands.json");
        JsonUtils.saveObject(file, deletedIslands);
    }

    public void saveConfig(){
        logger.info("Saving island config");

        File file = new File(plugin.getDataFolder() + File.separator + "islandConfig.json");

        JsonUtils.saveObject(file, islandConfig);
    }

    public void loadLastIslandLocation(){
        logger.info("Loading last island location");

        File file = new File(plugin.getDataFolder() + File.separator + "lastIslandLocation.json");

        if(!file.exists()){
            lastIslandLocation = new Point("world", 0, 0,0);
            return;
        }

        lastIslandLocation = (Point) JsonUtils.loadObject(file, Point.class);
    }

    public void saveLastIslandLocation(){
        logger.info("Saving last island location");

        File file = new File(plugin.getDataFolder() + File.separator + "lastIslandLocation.json");
        JsonUtils.saveObject(file, lastIslandLocation);
    }

    public void loadIsland(UUID islandID){
        logger.info("Loading island " + islandID);

        File file = new File(plugin.getDataFolder() + File.separator + "islands" + File.separator + islandID.toString() + ".json");

        Island island = mapper.mapDto((IslandDto) JsonUtils.loadObject(file, IslandDto.class));
        islands.put(island.getIslandID(), island);
    }

    public void initAutoIslandSaving(){
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Iterator<UUID> islandIDIterator = islands.keySet().iterator();

            while(islandIDIterator.hasNext()){
                UUID islandID = islandIDIterator.next();

                if(!hasIslandOnlineMembers(islandID)){
                    saveIsland(islandID);
                }
            }
        }, 60, 20*60*5);
    }

    public void saveIsland(UUID islandID){
        logger.info("Saving island " + islandID);

        File file = new File(plugin.getDataFolder() + File.separator + "islands" + File.separator + islandID.toString() + ".json");

        JsonUtils.saveObject(file, mapper.mapToDto(islands.get(islandID)));
        islands.remove(islandID);
    }

    public boolean isIslandLoaded(UUID islandID){
        return islands.containsKey(islandID);
    }

    public boolean hasIslandOnlineMembers(UUID islandID){
        Island island = islands.get(islandID);

        for(UUID playerID : island.getMembers()){
            Player player = Bukkit.getPlayer(playerID);
            if(player != null && player.isOnline()){
                return true;
            }
        }

        Player owner = Bukkit.getPlayer(island.getOwnerID());
        if(owner != null && owner.isOnline()){
            return true;
        }

        return false;
    }

    public boolean isPlayerOnIsland(Player player) {
        int isOnIsland = islands.values().stream().filter(
                island -> island.isOnIsland(player)
        ).toList().size();

        return isOnIsland > 0;
    }

    public boolean isPlayerOnIsland(Player player, UUID islandID) {
        Island island = islands.get(islandID);

        return island.isOnIsland(player);
    }

    public Island getIslandByPlayerLocation(Player player){
        return islands.values().stream().filter(
                island -> island.isOnIsland(player)
        ).findFirst().orElse(null);
    }

    public Island getIsland(UUID islandID){
        return islands.getOrDefault(islandID, null);
    }

    public Island getIsland(Player player){
        SkyBlockPlayer skyBlockPlayer = Controllers.getSkyBlockPlayerController().getPlayer(player);
        return getIsland(skyBlockPlayer.getIslandID());
    }

    public boolean pasteIsland(Location location){
        File file = new File(plugin.getDataFolder() + File.separator + islandConfig.getIslandSchematic());
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try {
            ClipboardReader reader = format.getReader(new FileInputStream(file));
            Clipboard clipboard = reader.read();

            World world = BukkitAdapter.adapt(location.getWorld());

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(
                    BlockVector3.at(location.getX(), location.getY(), location.getZ())
            ).ignoreAirBlocks(true).build();

            Operations.complete(operation);
            editSession.flushSession();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void removeIsland(UUID islandID){
        Island island = islands.get(islandID);

        Location spawn = Point.pointToLocation(GeneralConfig.getGeneralConfig().getSpawn());
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(island.isOnIsland(player)){
                player.teleport(spawn);
            }
        });

        Location point1 = island.getPoint1();
        Location point2 = island.getPoint2();
        for(int x = point1.getBlockX(); x <= point2.getBlockX(); x++){
            for(int z = point1.getBlockZ(); x <= point2.getBlockZ(); z++){
                for(int y = 0; y <= point1.getWorld().getMaxHeight(); y++){
                    Location location = new Location(point1.getWorld(), x,y,z);
                    if(location.getBlock().getType() != Material.AIR){
                        location.getBlock().setType(Material.AIR);
                    }
                }
            }
        }

        deletedIslands.add(islandID);

        Controllers.getSkyBlockPlayerController().getPlayer(island.getOwnerID()).setIslandID(null);
        island.getMembers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if(player == null && !player.isOnline()){
                return;
            }

            Controllers.getSkyBlockPlayerController().getPlayer(uuid).setIslandID(null);
        });
    }

    public boolean islandDeleted(UUID islandID){
        return deletedIslands.contains(islandID);
    }

    public void createNewInvite(Island island, Player target){
        invites.put(target.getUniqueId(), new Invite(
                System.currentTimeMillis(),
                island.getIslandID(),
                target.getUniqueId()
        ));
    }

    public boolean doesPlayerHasInvite(Player target){
        return invites.containsKey(target.getUniqueId());
    }

    public void acceptInvite(Player target){
        Invite invite = invites.get(target.getUniqueId());

        SkyBlockPlayer targetSkyBlockPlayer = Controllers.getSkyBlockPlayerController().getPlayer(target.getUniqueId());

        Island island = islands.get(invite.getIslandID());

        if(island.getMembers().size() >= GeneralConfig.getGeneralConfig().getMaxPlayersPerIsland()){
            ChatUtils.sendMessage(target, Language.getText("maxPlayersReached"));
            return;
        }

        island.getMembers().add(target.getUniqueId());
        targetSkyBlockPlayer.setIslandID(island.getIslandID());

        ChatUtils.sendMessage(target, Language.getText("acceptInvite"));
    }

    public void declineInvite(Player target){
        Invite invite = invites.get(target.getUniqueId());

        Player islandOwner = Bukkit.getPlayer(islands.get(invite.getIslandID()).getOwnerID());

        invites.remove(target.getUniqueId());

        ChatUtils.sendMessage(target, Language.getText("declineInvite"));
        if(islandOwner != null && islandOwner.isOnline()){
            ChatUtils.sendMessage(islandOwner, Language.getText("playerDeclinedInvite")
                    .replace("{PLAYER}", target.getName()));
        }
    }

}
