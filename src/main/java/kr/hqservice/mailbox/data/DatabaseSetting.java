package kr.hqservice.mailbox.data;

public class DatabaseSetting {

    public final String host;

    public final int port;

    public final String database;

    public final String user;

    public final String password;

    public final int maxPoolSize;

    public final int minPoolSize;

    public DatabaseSetting(String host, int port, String database, String user, String password, int maxPoolSize, int minPoolSize) {
        this.host = host;
        this.database = database;
        this.port = port;
        this.user = user;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.minPoolSize = minPoolSize;
    }
}
