package com.secret.santa.dto.filtredDto;

import com.secret.santa.dto.ParticipantDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupDtoParticipantFilter {
    private Long id;

    @NotBlank
    private String name;

    private String description;
}
