package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.ScoreMapper;
import org.linlinjava.litemall.db.domain.Score;
import org.linlinjava.litemall.db.domain.ScoreExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;

    /**
     * 添加评分，只保存用户id
     *
     * @param userId
     * @param score
     */
    public Integer addScore(Integer userId, Integer score) {
        Score s = new Score();
        s.setUserId(userId);
        s.setScore(score);
        s.setAddTime(LocalDateTime.now());

        return scoreMapper.insertSelective(s);

    }

    public List<Score> findByUidAndInMonth(Integer userId) {
        LocalDate now = LocalDate.now();
        LocalDate firstday = LocalDate.of(now.getYear(), now.getMonth(), 1);//当月首日
        LocalDateTime firstdayTime = LocalDateTime.of(firstday, LocalTime.of(0, 0, 0));
        ScoreExample example = new ScoreExample();
        System.out.println(firstdayTime);
        example.or().andUserIdEqualTo(userId).andAddTimeGreaterThanOrEqualTo(firstdayTime).andDeletedEqualTo(false);
        List<Score> scoreList = scoreMapper.selectByExampleSelective(example);
        return scoreList;
    }

    public List<Score> findByUserId(Integer userId) {
        ScoreExample example = new ScoreExample();
        example.or().andUserIdEqualTo(userId).andDeletedEqualTo(false);
        example.setOrderByClause("id desc");
        PageHelper.startPage(1,10);
        List<Score> scores = scoreMapper.selectByExample(example);
        return scores;
    }

    public List<Score> findByMonth(int year, int month) {
        ScoreExample example = new ScoreExample();
        LocalDateTime firstday = LocalDateTime.of(year, month, 1, 0, 0).with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastday = LocalDateTime.of(year, month, 1, 0, 0).with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        example.or().andAddTimeBetween(firstday, lastday).andDeletedEqualTo(false);
        return scoreMapper.selectByExample(example);

    }
}
