package com.yc.bilibili.service;

import com.yc.bilibili.dao.FollowingGroupDao;
import com.yc.bilibili.dao.UserFollowingDao;
import com.yc.bilibili.daomin.FollowingGroup;
import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserFollowing;
import com.yc.bilibili.daomin.constant.UserConstant;
import com.yc.bilibili.daomin.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;


    public void addUserFollowing(UserFollowing userFollowing){
        Long groupId = userFollowing.getGroupId();
        if(groupId == null){
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        }else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if(followingGroup ==null){
                throw new ConditionException("关注分组不存在！");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User user =  userService.getUserById(followingId);
        if(user == null){
            throw new ConditionException("关注的用户不存在！");
        }

    }
}
