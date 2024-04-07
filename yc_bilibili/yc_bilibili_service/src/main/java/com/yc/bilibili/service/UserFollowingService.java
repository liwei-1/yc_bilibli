package com.yc.bilibili.service;

import com.yc.bilibili.dao.UserFollowingDao;
import com.yc.bilibili.daomin.FollowingGroup;
import com.yc.bilibili.daomin.User;
import com.yc.bilibili.daomin.UserFollowing;
import com.yc.bilibili.daomin.UserInfo;
import com.yc.bilibili.daomin.constant.UserConstant;
import com.yc.bilibili.daomin.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFollowingService {

    @Autowired
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class, RuntimeException.class})
    public void addUserFollowing(UserFollowing userFollowing) {
        Long groupId = userFollowing.getGroupId();
        if (groupId == null) {
            FollowingGroup followingGroup = followingGroupService.getByType(UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
            userFollowing.setGroupId(followingGroup.getId());
        } else {
            FollowingGroup followingGroup = followingGroupService.getById(groupId);
            if (followingGroup == null) {
                throw new ConditionException("关注分组不存在！");
            }
        }
        Long followingId = userFollowing.getFollowingId();
        User user = userService.getUserById(followingId);
        if (user == null) {
            throw new ConditionException("关注的用户不存在！");
        }
        //删除关联关系
        userFollowingDao.deleteUserFollowing(userFollowing.getUserId(), userFollowing.getFollowingId());
        userFollowing.setCreateTime(new Date());
        // 添加关联关系
        userFollowingDao.addUserFollowing(userFollowing);
    }
    //获取关注的用户列表
    //根据关注用户的id查询关注用户的基本信息

    public List<FollowingGroup> getUserFollowingList(Long userId){
        //根据用户id查询关注用户
        List<UserFollowing> userFollowingList  = userFollowingDao.getUserFollowingList(userId);
        //获取 关注用户id
        Set<Long> followingIdSet = userFollowingList.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size()>0){
            // 根据关注用户id查询 用户详细信息
            userInfoList = userService.getUserInfoByUserIdList(followingIdSet);
        }

        //循环遍历把用户详细信息 set到用户关注中
        for (UserFollowing userFollowing : userFollowingList) {
            for (UserInfo userInfo : userInfoList) {
                if(userFollowing.getFollowingId().equals(userInfo.getUserId())){
                    userFollowing.setUserInfo(userInfo);
                }
            }
        }
        //将关注用户按关注分组进行分类
        List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
        //全部关注的分组
        FollowingGroup allGroup =  new FollowingGroup();
        allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
        allGroup.setFollowingUserInfoList(userInfoList);
        List<FollowingGroup> result = new ArrayList<>();
        result.add(allGroup);
        for (FollowingGroup group : groupList) {
            List<UserInfo> infoList = new ArrayList<>();
            for(UserFollowing userFollowing:userFollowingList){
                if(group.getId().equals(userFollowing.getGroupId())){
                    infoList.add(userFollowing.getUserInfo());
                }
            }
            group.setFollowingUserInfoList(infoList);
            result.add(group);
        }
        return result;
    }

    public List<UserFollowing> getUserFans(Long userId){
        List<UserFollowing> fansList = userFollowingDao.getUserFans(userId);
        Set<Long> followingIdSet = fansList.stream().map(UserFollowing::getUserId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(followingIdSet.size() > 0){
            userInfoList = userService.getUserInfoByUserIdList(followingIdSet);

        }
        List<UserFollowing> followingList = userFollowingDao.getUserFollowingList(userId);
        for(UserFollowing fan:fansList){
            for(UserInfo userInfo:userInfoList){
                if(fan.getUserId().equals(userInfo.getUserId())){
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for(UserFollowing following:followingList){
                // 判断是否互粉
                if(following.getFollowingId().equals(following.getUserId())){
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fansList;

    }


    public Long addUserFollowingGroup(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
         followingGroupService.addUserFollowingGroup(followingGroup);
        return followingGroup.getId();
    }


    public List<FollowingGroup> getUserFollowingGroups(Long userId) {
        return followingGroupService.getUserFollowingGroups(userId);
    }
}
