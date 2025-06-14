package com.lab.securing_project_tracker.service;

import com.lab.securing_project_tracker.dto.skill.SkillDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.model.SkillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

public interface SkillService {
    SkillEntity create(SkillDto skillDto);
    Optional<SkillEntity> findSkillByName(String name);
    Optional<SkillEntity> findSkillById(Long id);
    Set<SkillEntity> findAllById(Set<Long> ids);
    Page<SkillResponseDto> findAll(Pageable pageable);


}
