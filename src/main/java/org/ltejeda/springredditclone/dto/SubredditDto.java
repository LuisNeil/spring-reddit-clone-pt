package org.ltejeda.springredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {
    private Long id;
    @NotBlank(message = "Community name is required")
    private String name;
    private Integer numberOfPosts;
    @NotBlank(message = "Description is required")
    private String description;

}
