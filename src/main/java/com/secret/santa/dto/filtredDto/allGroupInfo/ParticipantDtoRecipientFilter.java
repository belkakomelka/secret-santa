package com.secret.santa.dto.filtredDto.allGroupInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantDtoRecipientFilter {
    private Long id;

    @NotBlank
    private String name;

    private String wish;
}
