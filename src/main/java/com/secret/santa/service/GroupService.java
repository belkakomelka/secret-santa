package com.secret.santa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.database.dao.Group;
import com.secret.santa.database.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final ObjectMapper objectMapping;

    public ResponseEntity<String> addGroup(com.secret.santa.dto.Group group){
        try {
            log.info("Принят запрос для сохранения новой группы " + objectMapping.writeValueAsString(group));
            Group groupEntity = Group.builder()
                    .name(group.getName())
                    .build();

            groupRepository.save(groupEntity);
            return ResponseEntity.ok(groupEntity.getId().toString());
        } catch (JsonProcessingException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public ResponseEntity<String> allGroupsList(){
//
//    }

}
