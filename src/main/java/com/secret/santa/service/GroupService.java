package com.secret.santa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.database.dao.Group;
import com.secret.santa.database.dao.Participant;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.database.repository.ParticipantRepository;
import com.secret.santa.dto.GroupDto;
import com.secret.santa.dto.filtredDto.allGroupInfo.GroupFiltredRecepientDto;
import com.secret.santa.dto.filtredDto.allGroupInfo.ParticipantDtoRecipientFilter;
import com.secret.santa.dto.filtredDto.allGroupsInfo.GroupDtoParticipantFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
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

    public ResponseEntity<String> getAllGroupInfo(String id){
        try{
            log.info("Получен запрос для выгрузки информации о группе с id " + id);
            Optional<Group> groupDbOptional = groupRepository.findById(id);
            Group groupDb;
            List<Participant> participantListDb;

            if (groupDbOptional.isPresent()){
                groupDb = groupDbOptional.get();
                participantListDb = participantRepository.getByGroupId(groupDb.getId());
            } else{
                log.error("Данная группа не найдена");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<ParticipantDtoRecipientFilter> participantDto = new ArrayList<>();

            if (!participantListDb.isEmpty()){
               participantDto = participantListDb.stream()
                        .map(participant -> ParticipantDtoRecipientFilter.builder()
                                .wish(participant.getWish())
                                .id(participant.getId())
                                .name(participant.getName()).build())
                        .collect(Collectors.toList());
            }

            GroupFiltredRecepientDto groupRs = GroupFiltredRecepientDto.builder()
                    .id(groupDb.getId())
                    .name(groupDb.getName())
                    .description(groupDb.getDescription())
                    .participantDtoList(participantDto) // в случае если тут отсутствуют участники, просто null
                    .build();

            return ResponseEntity.ok(objectMapping.writeValueAsString(groupRs));
        } catch (RuntimeException | JsonProcessingException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
