package pl.pijok.skyblock;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

public class Language {

    private static Logger logger = Logger.getLogger(Language.class.getName());
    private static HashMap<String, String> lang;

    public static void load(SkyBlock plugin){
        File file = new File(plugin.getDataFolder() + File.separator + "lang.json");

        lang = new HashMap<>();
        if(!file.exists()){
            syncConfig();
            JsonUtils.saveObject(file, lang);
        }
        else{
            lang = (HashMap<String, String>) JsonUtils.loadObject(file, HashMap.class);
        }

        syncConfig();
        JsonUtils.saveObject(file, lang);
        logger.info("Loaded " + lang.size() + " texts");
    }

    private static void syncConfig(){
        HashMap<String, String> tempLang = new HashMap<>();
        tempLang.put("prefix", "&7[&e&lSkyblock&7]&r");
        tempLang.put("pluginReload", "&a&lPrzeladowywanie...");
        tempLang.put("pluginReloaded", "&a&lPrzeladowano plugin!");
        tempLang.put("islandCreated", "&a&lUtworzono wyspe!");
        tempLang.put("alreadyHasIsland", "&cPosiadasz juz wyspe!");
        tempLang.put("noIsland", "&cNie posiadasz wyspy!");
        tempLang.put("islandTeleport", "&a&lTeleportowanie!");
        tempLang.put("noPermission", "&c&lNie masz dostepu do tej komendy!");
        tempLang.put("notOwner", "&cNie jestes wlascicielem wyspy!");
        tempLang.put("islandDeleted", "&cWyspa zostala usunieta");
        tempLang.put("playerOffline", "&cGracza nie ma na serwerze!");
        tempLang.put("declineInvite", "&cOdrzucono zaproszenie na wyspe");
        tempLang.put("playerDeclinedInvite", "&c{PLAYER} odrzucil twoje zaproszenie na wyspe");
        tempLang.put("acceptInvite", "&aZaakceptowano zaproszenie na wyspe");
        tempLang.put("playerAcceptedInvite", "&a{PLAYER} zaakceptowal twoje zaproszenie na wyspe");
        tempLang.put("maxPlayersReached", "&cWyspa ma juz maksymalna liczbe graczy!");
        tempLang.put("islandInvite", "&aOtrzymales zaproszenie na wyspe od &e{PLAYER}");
        tempLang.put("playerReceivedInvite", "&aGracz {PLAYER} otrzymal twoje zaproszenie");
        tempLang.put("playerAlreadyHasIsland", "&cTen gracz ma juz wyspe");
        tempLang.put("cantDoThis", "&cYou can't do this here!");
        tempLang.put("blockLimitReached", "&cYou cant place another block of this type!");
        tempLang.put("notOnIsland", "&cNie jesteś na żadnej wyspie!");
        tempLang.put("islandChatEnabled", "&a&lWlaczono czat wyspy!");
        tempLang.put("islandChatDisabled", "&c&lWylaczono czat wyspy!");

        for(String tempKey : tempLang.keySet()){
            if(!lang.containsKey(tempKey)){
                lang.put(tempKey, tempLang.get(tempKey));
            }
        }
    }

    public static String getText(String id){
        return lang.getOrDefault(id, "text_not_found");
    }

}
