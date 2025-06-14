package com.lab.securing_project_tracker.dto.developer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/* this is dto is used for inserting or updating */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperDto {
    private String name;
    private String email;
    private Set<Long> skillIds; // IDs of existing skills
}
