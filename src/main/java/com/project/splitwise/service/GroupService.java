package com.project.splitwise.service;

import com.project.splitwise.entity.Group;
import com.project.splitwise.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group saveGroup(Group inputGroup){
        return groupRepository.save(inputGroup);
    }

    public Optional<Group> findByGroupName(String inputGroupName){
        return groupRepository.findByGroupName(inputGroupName);
    }
}
