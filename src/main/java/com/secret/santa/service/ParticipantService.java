package com.secret.santa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.database.dao.Group;
import com.secret.santa.database.dao.Participant;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.database.repository.ParticipantRepository;
import com.secret.santa.dto.ParticipantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantService {
    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapping;

    public ResponseEntity<String> addParticipant(String id, ParticipantDto participantDto){
        try {
            log.info("Принят запрос для сохранения нового участника " + objectMapping.writeValueAsString(participantDto));

            Optional<Group> groupOptional = groupRepository.findById(id);
            Participant participantEntity;
            if (groupOptional.isPresent()){
                participantEntity = Participant.builder()
                        .name(participantDto.getName())
                        .wish(participantDto.getWish())
                        .group(groupOptional.get())
                        .build();
                participantRepository.save(participantEntity);
                log.info("Группа сохранена в базу с идентификатором " + participantEntity.getId().toString());
            } else{
                log.error("Группа с данным идентификатором отсутствует");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(participantEntity.getId().toString(), HttpStatus.OK);
        } catch (JsonProcessingException | RuntimeException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
