package org.linlinjava.litemall.db.dao;

import org.linlinjava.litemall.db.domain.LitemallDailyMenu;
import org.linlinjava.litemall.db.domain.LitemallMealOrder;

import java.util.List;

public interface CustomMealOrderMapper {
    int insertOrders(List<LitemallMealOrder> records);
}

