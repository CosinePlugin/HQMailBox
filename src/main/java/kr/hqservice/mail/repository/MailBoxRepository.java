package kr.hqservice.mail.repository;

import kr.hqservice.mail.data.MailBoxDTO;
import kr.hqservice.mail.database.DataSource;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class MailBoxRepository {

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static final String mailBoxTableName = "mail_box";

    private final DataSource dataSource;

    public MailBoxRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable() {
        var crateTableQuery = "CREATE TABLE IF NOT EXISTS " + mailBoxTableName + " (" +
            "owner CHAR(36) NOT NULL PRIMARY KEY, " +
            "contents BLOB NOT NULL" +
            ") DEFAULT CHARSET=utf8mb4";
        try (var connection = dataSource.getConnection()) {
            connection.createStatement().execute(crateTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<MailBoxDTO> getMailBox(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            var getMailBoxQuery = "SELECT * FROM " + mailBoxTableName + " WHERE owner = ?";
            try (var connection = dataSource.getConnection();
                 var prepareStatement = connection.prepareStatement(getMailBoxQuery)
            ) {
                prepareStatement.setString(1, uuid.toString());
                var resultSet = prepareStatement.executeQuery();
                if (resultSet.next()) {
                    var ownerText = resultSet.getString("owner");
                    var owner = UUID.fromString(ownerText);
                    var byteArray = resultSet.getBytes("contents");
                    var contents = toDecompressed(byteArray);
                    return new MailBoxDTO(owner, contents);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }, executorService);
    }

    public void setMailBox(MailBoxDTO mailBoxDTO) {
        var setMailBoxQuery = "INSERT INTO " + mailBoxTableName + " (owner, contents) " +
            "VALUES(?, ?) ON DUPLICATE KEY UPDATE contents = ?";
        try (var connection = dataSource.getConnection();
             var prepareStatement = connection.prepareStatement(setMailBoxQuery)
        ) {
            var ownerText = mailBoxDTO.getOwner().toString();
            var contents = mailBoxDTO.getContents();
            var byteArray = toCompressed(contents);
            prepareStatement.setString(1, ownerText);
            prepareStatement.setBytes(2, byteArray);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private byte[] toCompressed(List<ItemStack> contents) {
        try (var byteArrayOutputStream = new ByteArrayOutputStream();
             var bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream)
        ) {
            var filterContents = contents.stream().filter(Objects::nonNull).toList();
            bukkitObjectOutputStream.writeInt(filterContents.size());
            try {
                for (ItemStack itemStack : filterContents) {
                    bukkitObjectOutputStream.writeObject(itemStack);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            var byteArray = byteArrayOutputStream.toByteArray();
            return toCompressedByteArray(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ItemStack> toDecompressed(byte[] byteArray) {
        var decompressByteArray = toDecompressedByteArray(byteArray);
        try (var byteArrayInputStream = new ByteArrayInputStream(decompressByteArray);
             var bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream)
        ) {
            var length = bukkitObjectInputStream.readInt();
            var contents = new ArrayList<ItemStack>();
            for (int loop = 0; loop < length; loop++) {
                var itemStack = (ItemStack) bukkitObjectInputStream.readObject();
                contents.add(itemStack);
            }
            return contents;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    private byte[] toCompressedByteArray(byte[] byteArray) {
        var deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(byteArray);
        deflater.finish();
        var out = new ByteArrayOutputStream(byteArray.length);
        var buffer = new byte[1024];
        while (!deflater.finished()) {
            var count = deflater.deflate(buffer);
            out.write(buffer, 0, count);
        }
        out.close();
        return out.toByteArray();
    }

    @SneakyThrows
    private byte[] toDecompressedByteArray(byte[] byteArray) {
        var inflater = new Inflater();
        inflater.setInput(byteArray);
        var out = new ByteArrayOutputStream(byteArray.length);
        var buffer = new byte[1024];
        while (!inflater.finished()) {
            var count = inflater.inflate(buffer);
            out.write(buffer, 0, count);
        }
        out.close();
        return out.toByteArray();
    }
}
