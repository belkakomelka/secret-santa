package com.secret.santa.controller;

import com.secret.santa.dto.Group;
import com.secret.santa.service.AddGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    @Autowired
    AddGroupService groupService;

    @PostMapping("/group")
    public ResponseEntity<String> addGroup(@Valid @RequestBody Group group) {
        return groupService.addGroup(group);
    }

}