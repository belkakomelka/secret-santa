package com.secret.santa.controller;

import com.secret.santa.dto.GroupDto;
import com.secret.santa.dto.filtredDto.changeGroupParams.GroupChangeParamsDto;
import com.secret.santa.service.GroupService;
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
}