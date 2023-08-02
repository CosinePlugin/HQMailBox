package kr.hqservice.mail.service;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MailBoxService {

    private final ExecutorService executors;

    public MailBoxService() {
        executors = Executors.newCachedThreadPool();
        new ItemStack(Material.WOODEN_AXE).getItemMeta().getPersistentDataContainer()
            .set(NamespacedKey.fromString(""), PersistentDataType.STRING, "");
    }

    public void openMailBox(Player player) {

    }
}
