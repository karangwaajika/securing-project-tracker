package com.lab.securing_project_tracker.controller;


import com.lab.securing_project_tracker.dto.skill.SkillDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.mapper.SkillMapper;
import com.lab.securing_project_tracker.model.SkillEntity;
import com.lab.securing_project_tracker.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/skills")
@Tag(name = "Skill Controller", description = "Manage all the Skill's urls")
public class SkillController {
    SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping(name = "add_skill", path = "/add")
    @Operation(summary = "Create skill",
            description = "This request inserts a skill to the database and returns " +
                          "the inserted skill ")
    public ResponseEntity<SkillResponseDto> addSkill(@RequestBody SkillDto skillDto){
        SkillEntity savedSkill = this.skillService.create(skillDto);
        SkillResponseDto savedSkillDto = SkillMapper.toResponseDto(savedSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSkillDto);
    }

    @GetMapping(name = "view_skills", path = "/view")
    @Operation(summary = "View Skills",
            description = "This method applies pagination for efficient retrieval " +
                          "of skills list")
    public Page<SkillResponseDto> viewProjects(Pageable pageable){

        return this.skillService.findAll(pageable);
    }

}
