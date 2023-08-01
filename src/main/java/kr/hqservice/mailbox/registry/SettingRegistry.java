package kr.hqservice.mailbox.registry;

import kr.hqservice.mailbox.data.DatabaseSetting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SettingRegistry {

    private DatabaseSetting databaseSetting;

    private int maxTime;
}
