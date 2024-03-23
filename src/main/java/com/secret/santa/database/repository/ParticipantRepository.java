package com.secret.santa.database.repository;

import com.secret.santa.database.dao.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
}
