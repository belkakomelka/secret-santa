package com.secret.santa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.config.ObjectMapperConfig;
import com.secret.santa.database.dao.GroupEntity;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.dto.Group;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddGroupService {
    private final GroupRepository groupRepository;
    private final ObjectMapper objectMapping;

    public ResponseEntity<String> addGroup(Group group){
        try {
            log.info("Принят запрос для сохранения новой группы" + objectMapping.writeValueAsString(group));
            GroupEntity groupEntity = GroupEntity.builder()
                    .name(group.getName())
                    .description(group.getDescription())
                    .build();

            groupRepository.save(groupEntity);
            return ResponseEntity.ok(objectMapping.writeValueAsString(group));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
