package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallBcUserMapper;
import org.linlinjava.litemall.db.domain.LitemallBcUser;
import org.linlinjava.litemall.db.domain.LitemallBcUserExample;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.LitemallUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class litemallBcUserService {
    @Autowired
    private LitemallBcUserMapper bcUserMapper;

    public LitemallBcUser findById(Integer id){
        return bcUserMapper.selectByPrimaryKey(id);
    }
    public void add(LitemallBcUser bcUser){
        bcUser.setAddtime(LocalDateTime.now());
        bcUserMapper.insertSelective(bcUser);
    }
    /**
     *     通过手机号码和姓名查找报餐的用户
     */
    public LitemallBcUser queryByMbAndUn(String mobile,String username){
        LitemallBcUserExample example = new LitemallBcUserExample();
        example.or().andMobileEqualTo(mobile).andNameEqualTo(username);
        LitemallBcUser litemallBcUser = bcUserMapper.selectOneByExample(example);
        return litemallBcUser;
    }

    /***
     * 根据报餐id找到列表
     * @param idList
     * @return  返回报餐用户列表
     */
    public List<LitemallBcUser> findByIdList(List<Integer> idList){
        LitemallBcUserExample example = new LitemallBcUserExample();
        example.or().andIdIn(idList);
        List<LitemallBcUser> BcUsers = bcUserMapper.selectByExample(example);
        return BcUsers;

    }

    /**
     * 更新报餐用户表
     * @param userId
     * @param bcUser
     */
    public void update(Integer userId,LitemallBcUser bcUser){
        bcUser.setCommitUserId(userId);
        bcUser.setUpdateTime(LocalDateTime.now());
        bcUserMapper.updateByPrimaryKeySelective(bcUser);
    }

    public List<LitemallBcUser> queryByStatus(LitemallBcUser bcUser) {
        LitemallBcUserExample example = new LitemallBcUserExample();
        example.or().andStatusEqualTo(bcUser.getStatus());
        List<LitemallBcUser> litemallBcUsers = bcUserMapper.selectByExample(example);
        return litemallBcUsers;
    }

    public List<LitemallBcUser> queryAll() {
        LitemallBcUserExample example = new LitemallBcUserExample();
        example.or();
        List<LitemallBcUser> litemallBcUsers = bcUserMapper.selectByExample(example);
        return litemallBcUsers;
    }
}
