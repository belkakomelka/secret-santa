package com.secret.santa.controller;

import com.secret.santa.dto.GroupDto;
import com.secret.santa.dto.ParticipantDto;
import com.secret.santa.dto.filtredDto.changeGroupParams.GroupChangeParamsDto;
import com.secret.santa.service.GroupService;
import com.secret.santa.service.ParticipantService;
import com.secret.santa.service.TossingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class Controller {
    @Autowired
    GroupService groupService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    TossingService tossingService;

    @PostMapping("/group")
    public ResponseEntity<String> addGroup(@Valid @RequestBody GroupDto groupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.warn("Ошибка валидации");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return groupService.addGroup(groupDto);
    }

    @GetMapping("/groups")
    public ResponseEntity<String> allGroups(){return groupService.allGroupsList();}

    @GetMapping("/group/{id}")
    public ResponseEntity<String> allGroupInfo(@PathVariable("id") String id) {
        return groupService.getAllGroupInfo(id);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<String> changeGroupParams(@PathVariable("id") String id,
                                                    @Valid @RequestBody GroupChangeParamsDto groupDto,
                                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.warn("Ошибка валидации");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return groupService.changeGroupParams(id, groupDto);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<String> deleteGroup(@PathVariable("id") String id){
        return groupService.deleteGroup(id);
    }

    @PostMapping("/group/{id}/participant")
    public ResponseEntity<String> addParticipant(@PathVariable("id") String id,
                                                 @Valid @RequestBody ParticipantDto participantDto,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.warn("Ошибка валидации");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return participantService.addParticipant(id, participantDto);
    }

    @PostMapping("/group/{id}/toss")
    public ResponseEntity<String> tossing(@PathVariable String id){
        return tossingService.tossingParticipants(id);
    }
}