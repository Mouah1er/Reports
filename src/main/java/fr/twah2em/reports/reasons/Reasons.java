package fr.twah2em.reports.reasons;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reasons {

    public static List<Reason> getChatReasons() {
        return Arrays.asList(
                new Reason("§eFlood", Material.BOOK),
                new Reason("§eInsults", Material.ARROW),
                new Reason("§cHateful language", Material.SKELETON_SKULL),
                new Reason("§eSpam", Material.BOOKSHELF));
    }

    public static List<Reason> getCheatReasons() {
        return Arrays.asList(
                new Reason("§cAimBot", Material.CROSSBOW),
                new Reason("§cBow Aimbot", Material.BOW),
                new Reason("§cKillAura", Material.FIRE_CHARGE),
                new Reason("§cReach", Material.DIAMOND_SWORD)
        );
    }

    public static List<Reason> getAll() {
        final List<Reason> returnList = new ArrayList<>();

        returnList.addAll(getChatReasons());
        returnList.addAll(getCheatReasons());

        return returnList;
    }
}
