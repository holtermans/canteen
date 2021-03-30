package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.LitemallDailyMenu;

import java.util.List;

public interface CustomDailyMenuMapper {
    int insertList(List<LitemallDailyMenu> records);
}

