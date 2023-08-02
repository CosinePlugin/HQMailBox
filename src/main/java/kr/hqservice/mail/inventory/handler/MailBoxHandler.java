package kr.hqservice.mail.inventory.handler;

import kr.hqservice.mail.inventory.MailBoxInventoryHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class MailBoxHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        var view = event.getView();
        var holder = getInventoryHolder(view);
        if (holder != null) {
            event.setCancelled(holder.isCancelled);
            onClick(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        var view = event.getView();
        var holder = getInventoryHolder(view);
        if (holder != null) {
            onClose(event);
        }
    }

    public MailBoxInventoryHolder getInventoryHolder(InventoryView view) {
        var holder = view.getTopInventory().getHolder();
        if (holder == null) return null;
        if (holder instanceof MailBoxInventoryHolder) {
            return (MailBoxInventoryHolder) holder;
        } else {
            return null;
        }
    }
}
