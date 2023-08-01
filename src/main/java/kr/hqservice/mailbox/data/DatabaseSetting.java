package kr.hqservice.mailbox.data;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DatabaseSetting {

    private final String host;

    private final int port;

    private final String database;

    private final String user;

    private final String password;

    private final int maxPoolSize;

    private final int minPoolSize;
}
