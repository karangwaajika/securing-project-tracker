package com.lab.securing_project_tracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "skill")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include         // hashCode/equals use ID only
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<DeveloperEntity> developers = new HashSet<>(); // no duplicate

}
