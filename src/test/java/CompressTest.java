import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.github.luben.zstd.Zstd;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class CompressTest {

    private ServerMock server;
    private ItemStack[] items = new ItemStack[54];

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock();
    }

    @AfterEach
    public void teardown() {
        MockBukkit.unmock();
    }

    @Test
    public void compress_test() {
        for (int loop = 0; loop < 54; loop++) {
            items[loop] = new ItemStack(Material.STONE);
        }
        var compressed = toCompressed(items);
        System.out.println("compressed: " + Arrays.toString(compressed));
        System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        var decompressed = toDecompressed(compressed);
        System.out.println("decompressed: " + Arrays.toString(decompressed));
    }

    private byte[] toCompressed(ItemStack[] itemStacks) {
        try (var byteArrayOutputStream = new ByteArrayOutputStream();
             var bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream)
        ) {
            bukkitObjectOutputStream.writeInt(itemStacks.length);
            try {
                for (ItemStack itemStack : itemStacks) {
                    if (itemStack == null) {
                        var air = new ItemStack(Material.AIR);
                        bukkitObjectOutputStream.writeObject(air);
                    } else {
                        bukkitObjectOutputStream.writeObject(itemStack);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Zstd.compress(byteArray);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private ItemStack[] toDecompressed(byte[] byteArray) {
        try {
            var byteArrayInputStream = new ByteArrayInputStream(byteArray);
            var bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);

            int length = bukkitObjectInputStream.readInt();
            ItemStack[] newItemStacks = new ItemStack[length];
            for (int loop = 0; loop < length; loop++) {
                newItemStacks[loop] = (ItemStack) bukkitObjectInputStream.readObject();
            }
            return newItemStacks;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ItemStack[0];
    }
}
