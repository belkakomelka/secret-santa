package com.secret.santa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupDto {
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private List<ParticipantDto> participantDtoList;

}
