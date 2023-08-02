package kr.hqservice.mail.registry;

import kr.hqservice.mail.data.DatabaseSetting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingRegistry {

    public static String prefix;

    private DatabaseSetting databaseSetting;

    private int maxTime;
}
