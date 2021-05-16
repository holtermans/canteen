package org.linlinjava.litemall.wx.task;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.linlinjava.litemall.db.domain.Config;
import org.linlinjava.litemall.db.service.ConfigService;
import org.linlinjava.litemall.db.service.LitemallSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class Test {
    @Autowired
    private ConfigService configService;
    //    每分钟启动
    @Scheduled(cron = "0 0/1 * * * ?")
    public void timerToNow(){
        Config config = configService.queryByName("checkCode");
        String value = config.getValue();
        String random = RandomStringUtils.randomAlphabetic(8);
        configService.updateByName("checkCode",random);


    }
}
