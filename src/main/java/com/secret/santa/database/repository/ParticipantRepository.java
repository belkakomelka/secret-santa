package com.secret.santa.database.repository;

import com.secret.santa.database.dao.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, String> {
}
