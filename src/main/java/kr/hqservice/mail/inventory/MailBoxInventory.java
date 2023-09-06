package kr.hqservice.mail.inventory;

import kr.hqservice.mail.HQMailBox;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static kr.hqservice.mail.registry.SettingRegistry.prefix;

public class MailBoxInventory extends MailBoxInventoryHolder {

    private int page = 1;

    public MailBoxInventory(int page) {
        super("메일함 : " + page + "페이지", 6, true);
        this.page = page;
    }

    @Override
    public void init(Player player) {
        setContents();
        setBackground();
    }

    private void setBackground() {
        getInventory().setItem(45, MailBoxItemHolder.BEFORE_PAGE_BUTTON);
        for (int slot : MailBoxItemHolder.BACKGROUND_SLOTS) {
            getInventory().setItem(slot, MailBoxItemHolder.BACKGROUND);
        }
        getInventory().setItem(53, MailBoxItemHolder.NEXT_PAGE_BUTTON);
    }

    private void setContents() {

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        var player = (Player) event.getWhoClicked();
        var server = player.getServer();
        var slot = event.getRawSlot();

        // 아이템
        if (slot <= 44) {
            return;
        }
        switch (slot) {
            case 45 -> {
                if (page == 1) {
                    player.sendMessage(prefix + " 이전 페이지가 존재하지 않습니다.");
                    return;
                }
                var instance = HQMailBox.getInstance();
                server.getScheduler().runTaskLater(instance, () -> new MailBoxInventory(page - 1).openInventory(player), 1);
            }
            case 53 -> {

            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }
}
