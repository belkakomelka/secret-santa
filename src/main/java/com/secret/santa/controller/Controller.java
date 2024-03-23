package com.secret.santa.controller;

import com.secret.santa.dto.GroupDto;
import com.secret.santa.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    @Autowired
    GroupService groupService;

    @PostMapping("/group")
    public ResponseEntity<String> addGroup(@Valid @RequestBody GroupDto groupDto) {

        return groupService.addGroup(groupDto);
    }

    @GetMapping("/groups")
    public ResponseEntity<String> allGroups(){return groupService.allGroupsList();}

}