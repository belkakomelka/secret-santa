package com.secret.santa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.santa.database.dao.Group;
import com.secret.santa.database.dao.Participant;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.database.repository.ParticipantRepository;
import com.secret.santa.dto.filtredDto.Tossing.ParticipantDtoNoRecipientFilter;
import com.secret.santa.dto.filtredDto.Tossing.ParticipantTossingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossingService {
    private final GroupRepository groupRepository;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapping;

    public ResponseEntity<String> tossingParticipants(String id) {
        try {
            log.info("Принят запрос для жеребьевки в группе");
            Optional<Group> groupOptional = groupRepository.findById(id);
            if (groupOptional.isPresent()) {
                Group groupDb = groupOptional.get();
                if (participantRepository.countByGroupId(groupDb.getId()) >= 3) {
                    List<Participant> participantList = participantRepository.getByGroupId(groupDb.getId());
                    List<Pair> pairs = tossingAlgo(participantList);
                    List<ParticipantTossingDto> participantTossing = new ArrayList<>();

                    for (int i = 0; i < pairs.size()-1; i++){
                        ParticipantTossingDto participantTossingDto = ParticipantTossingDto.builder()
                                .id(participantList.get(i).getId())
                                .name(participantList.get(i).getName())
                                .wish(participantList.get(i).getWish())
                                .participantDto(buildRecipient((Long) pairs.get(i).b))
                                .build();
                        participantTossing.add(participantTossingDto);
                    }
                    participantTossing.add(ParticipantTossingDto.builder()
                            .id(participantList.get(pairs.size()-1).getId())
                            .name(participantList.get(pairs.size()-1).getName())
                            .wish(participantList.get(pairs.size()-1).getWish())
                            .participantDto(buildRecipient((Long) pairs.get(pairs.size()-1).b))
                            .build());

                    savingRecipient(participantTossing);
                    return new ResponseEntity<>(objectMapping.writeValueAsString(participantTossing), HttpStatus.OK);

                } else {
                    log.error("Участников меньше трех");
                    return new ResponseEntity(HttpStatus.CONFLICT);
                }
            } else {
                log.error("Группа с данным идентификатором отсутствует");
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }catch (RuntimeException | JsonProcessingException e) {
            log.error("Внутрення ошибка сервиса " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private List<Pair> tossingAlgo(List<Participant> participantList){
        List<Long> participants = new ArrayList<>();
        for (Participant participant: participantList) {
            participants.add(participant.getId());
        }
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < participants.size()-1; i++) {
            pairs.add(new Pair(participants.get(i), participants.get(i + 1)));
        }
        pairs.add(new Pair(participants.size() - 1, participants.get(0)));
        return pairs;
    }

    private ParticipantDtoNoRecipientFilter buildRecipient(Long id){
        Optional<Participant> recepientOptional = participantRepository.findById(String.valueOf(id));
        if (recepientOptional.isPresent()){
            Participant recipient = recepientOptional.get();
            return ParticipantDtoNoRecipientFilter.builder()
                    .name(recipient.getName())
                    .wish(recipient.getWish())
                    .id(recipient.getId())
                    .build();
        } else {
            throw new RuntimeException();
        }

    }

    void savingRecipient(List<ParticipantTossingDto> participantList){
        for (ParticipantTossingDto participant: participantList){
            Optional<Participant> participantDbOptional = participantRepository.findById(String.valueOf(participant.getId()));
            if (participantDbOptional.isPresent()){
                Participant participantDb = participantDbOptional.get();
                participantDb.setRecipient(Participant.builder()
                        .name(participant.getParticipantDto().getName())
                        .wish(participant.getParticipantDto().getWish())
                        .id(participant.getParticipantDto().getId())
                        .build());

                participantRepository.save(participantDb);
            }
        }
    }
}

