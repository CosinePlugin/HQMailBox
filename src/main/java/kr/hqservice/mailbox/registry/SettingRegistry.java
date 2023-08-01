package kr.hqservice.mailbox.registry;

import kr.hqservice.mailbox.data.DatabaseSetting;

public class SettingRegistry {

    private DatabaseSetting databaseSetting;

    private int maxTime;

    public DatabaseSetting getDatabaseSetting() {
        return databaseSetting;
    }

    public void setDatabaseSetting(DatabaseSetting databaseSetting) {
        this.databaseSetting = databaseSetting;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }
}
