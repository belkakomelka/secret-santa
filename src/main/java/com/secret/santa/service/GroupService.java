package com.secret.santa.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.database.dao.Group;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.dto.GroupDto;
import com.secret.santa.dto.filtredDto.GroupDtoParticipantFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final ObjectMapper objectMapping;

    public ResponseEntity<String> addGroup(GroupDto groupDto){
        try {
            log.info("Принят запрос для сохранения новой группы " + objectMapping.writeValueAsString(groupDto));
            Group groupEntity = Group.builder()
                    .name(groupDto.getName())
                    .description(groupDto.getDescription())
                    .build();

            groupRepository.save(groupEntity);
            log.info("Группа сохранена в базу с идентификатором " + groupEntity.getId().toString());
            return ResponseEntity.ok(groupEntity.getId().toString());
        } catch (JsonProcessingException | RuntimeException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> allGroupsList() {
        try {
            log.info("Получен запрос для выгрузки информации о всех группах");
            List<Group> allGroupsDb = groupRepository.findAll();
            List<GroupDtoParticipantFilter> allGroupsRs = allGroupsDb.stream()
                    .map(group -> GroupDtoParticipantFilter.builder()
                            .id(group.getId())
                            .name(group.getName())
                            .description(group.getDescription())
                            .build())
                    .collect(Collectors.toList());

            log.info("Выгрузка информации обо всех группах" + objectMapping.writeValueAsString(allGroupsRs));
            return ResponseEntity.ok(objectMapping.writeValueAsString(allGroupsRs));

        } catch (JsonProcessingException | RuntimeException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
