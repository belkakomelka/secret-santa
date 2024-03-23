package com.secret.santa.dto.filtredDto.allGroupsInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDtoParticipantFilter {
    private Long id;

    @NotBlank
    private String name;

    private String description;
}
