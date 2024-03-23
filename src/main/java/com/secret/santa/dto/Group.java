package com.secret.santa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Group {
    private int id;

    @NotBlank
    private String name;

    private String description;

    private List<Participant> participantList;

}
