package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.domain.BcUserVo;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BcUserVoService {
    @Autowired
    private LitemallBcUserService bcUserService;
    @Autowired
    private LitemallUserService userService;

    public BcUserVo getBcUserVoByUserId(Integer userId){
        LitemallUser user = userService.findById(userId);
        LitemallBcUser bcUser = bcUserService.findById(user.getBcUserId());
        BcUserVo bcUserVo = new BcUserVo();
        bcUserVo.setStatus(bcUser.getStatus());
        bcUserVo.setAdmin(bcUser.getIsAdmin());
        bcUserVo.setAvatar(user.getAvatar());
        bcUserVo.setNickname(user.getNickname());
        bcUserVo.setId(user.getId());
        bcUserVo.setBcId(bcUser.getId());
        bcUserVo.setName(bcUser.getName());
        bcUserVo.setMobile(bcUser.getMobile());
        return bcUserVo;

    }
}
