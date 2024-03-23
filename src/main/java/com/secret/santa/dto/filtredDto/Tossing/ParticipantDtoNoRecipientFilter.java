package com.secret.santa.dto.filtredDto.Tossing;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantDtoNoRecipientFilter {
    private Long id;

    @NotBlank
    private String name;

    private String wish;
}
