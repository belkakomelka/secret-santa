package com.secret.santa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Participant {
    private int id;

    @NotBlank
    private String name;

    private String wish;

    private Participant participant;
}
