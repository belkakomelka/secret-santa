package com.secret.santa.database.repository;

import com.secret.santa.database.dao.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
