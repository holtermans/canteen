package org.linlinjava.litemall.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.dao.LitemallTimingMapper;
import org.linlinjava.litemall.db.domain.LitemallTiming;
import org.linlinjava.litemall.db.domain.LitemallTimingExample;
import org.mockito.cglib.core.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.LocalTime;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {

    @Autowired
    private LitemallTimingMapper timingMapper;

    @Test
    public void test() {
        byte a = 1;
        LitemallTiming timing = new LitemallTiming();
        timing.setReminder(a);
        timing.setName("中餐");
        timing.setStartTime(LocalTime.parse("12:00"));
        timing.setEndTime(LocalTime.parse("13:00"));
        timing.setStopTime(LocalTime.now());
        timingMapper.insertSelective(timing);

    }
}
