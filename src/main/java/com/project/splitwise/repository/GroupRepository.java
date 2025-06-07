package com.project.splitwise.repository;

import com.project.splitwise.entity.Group;
import com.project.splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
