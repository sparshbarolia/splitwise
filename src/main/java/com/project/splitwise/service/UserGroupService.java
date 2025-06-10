package com.project.splitwise.service;

import com.project.splitwise.entity.UserGroup;
import com.project.splitwise.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    public UserGroup saveUserGroup(UserGroup inputuserGroup){
        return userGroupRepository.save(inputuserGroup);
    }
}
