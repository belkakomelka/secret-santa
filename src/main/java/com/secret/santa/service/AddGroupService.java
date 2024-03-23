package com.secret.santa.service;

import com.secret.santa.database.dao.GroupEntity;
import com.secret.santa.database.repository.GroupRepository;
import com.secret.santa.dto.Group;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddGroupService {
    @Autowired
    GroupRepository groupRepository;
    GroupEntity groupEntity;


    public ResponseEntity<String> addGroup(Group group){
        groupEntity = GroupEntity.builder()
                .name(group.getName())
                .description(group.getDescription())
                .build();

        groupRepository.save(groupEntity);
        return ResponseEntity.ok("Группа сохранена");
    }

}
