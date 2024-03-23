package com.secret.santa.database.repository;

import com.secret.santa.database.dao.Group;
import com.secret.santa.database.dao.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
    List<Participant> getByGroupId(Long groupId);
    void deleteAllByGroupId(Long groupId);

    Long countByGroupId(Long groupId);
}
