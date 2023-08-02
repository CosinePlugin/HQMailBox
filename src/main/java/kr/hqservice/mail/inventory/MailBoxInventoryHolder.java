package kr.hqservice.mail.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class MailBoxInventoryHolder implements InventoryHolder {

    private final Inventory inventory;

    public boolean isCancelled;

    public MailBoxInventoryHolder(String title, int row, boolean isCancelled) {
        inventory = Bukkit.createInventory(this, row * 9, title);
        this.isCancelled = isCancelled;
    }

    public void init(Player player) {}

    public void openInventory(Player player) {
        init(player);
        player.openInventory(inventory);
    }

    public void onClick(InventoryClickEvent event) {}

    public void onClose(InventoryCloseEvent event) {}

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
