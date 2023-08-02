package kr.hqservice.mail.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MailBoxItemHolder {

    public static int[] BACKGROUND_SLOTS = { 46, 47, 48, 49, 50, 51, 52 };

    public static ItemStack BACKGROUND = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
        .setDisplayName("§f")
        .build();

    public static ItemStack BEFORE_PAGE_BUTTON = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
        .setDisplayName("§c이전 페이지")
        .build();

    public static ItemStack NEXT_PAGE_BUTTON = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
        .setDisplayName("§a다음 페이지")
        .build();
}
