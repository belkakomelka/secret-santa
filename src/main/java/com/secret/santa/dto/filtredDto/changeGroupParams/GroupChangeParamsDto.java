package com.secret.santa.dto.filtredDto.changeGroupParams;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupChangeParamsDto {
    @NotBlank
    private String name;

    private String description;
}
