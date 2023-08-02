package kr.hqservice.mail.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MailBoxDTO {

    private final UUID owner;

    private final List<ItemStack> contents;
}
