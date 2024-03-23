package com.secret.santa.database.repository;

import com.secret.santa.database.dao.GroupEntity;
import com.secret.santa.database.dao.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {
}
