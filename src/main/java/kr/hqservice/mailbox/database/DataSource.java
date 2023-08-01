package kr.hqservice.mailbox.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import kr.hqservice.mailbox.data.DatabaseSetting;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private final HikariDataSource hikariDataSource;

    public DataSource(DatabaseSetting databaseSetting) {
        var hikariConfig = new HikariConfig();

        var jdbcUrl = "jdbc:mysql://" + databaseSetting.getHost() + ":" + databaseSetting.getPort() + "/" + databaseSetting.getDatabase();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(databaseSetting.getUser());
        hikariConfig.setPassword(databaseSetting.getPassword());
        hikariConfig.setMaximumPoolSize(databaseSetting.getMaxPoolSize());
        hikariConfig.setMinimumIdle(databaseSetting.getMinPoolSize());

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    public void closeConnection() {
        hikariDataSource.close();
    }
}
