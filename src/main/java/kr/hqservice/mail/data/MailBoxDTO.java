package kr.hqservice.mail.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MailBoxDTO {

    private final UUID owner;

    private final List<ItemStack> contents;
}
