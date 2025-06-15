package com.lab.securing_project_tracker.controller;


import com.lab.securing_project_tracker.dto.developer.DeveloperDto;
import com.lab.securing_project_tracker.dto.developer.DeveloperResponseDto;
import com.lab.securing_project_tracker.exception.DeveloperNotFoundException;
import com.lab.securing_project_tracker.mapper.DeveloperMapper;
import com.lab.securing_project_tracker.model.DeveloperEntity;
import com.lab.securing_project_tracker.service.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/developers")
@Tag(name = "Developer Controller", description = "Manage all the Developer's urls")
public class DeveloperController {
    DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @PostMapping(name = "add_developer", path = "/add")
    @Operation(summary = "Create developer",
            description = "This request inserts a developer to the database and returns " +
                          "the inserted developer ")
    public ResponseEntity<DeveloperResponseDto> addDeveloper(@RequestBody DeveloperDto developerDto){
        DeveloperResponseDto savedDeveloperDto = this.developerService.create(developerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeveloperDto);
    }

    @GetMapping(name = "view_developer_by_id", path = "/view/{id}")
    @Operation(summary = "View Developer",
            description = "Search and view only one developer using developer ID")
    public ResponseEntity<DeveloperResponseDto> viewDeveloper(@PathVariable Long id){
        Optional<DeveloperEntity> developer = this.developerService.findDeveloperById(id);

        if(developer.isEmpty()){
            throw new DeveloperNotFoundException(
                    String.format("A developer with the Id '%d' doesn't exist", id));
        }
        DeveloperResponseDto developerResponseDto = DeveloperMapper.toResponseDto(developer.get());
        return ResponseEntity.status(HttpStatus.OK).body(developerResponseDto);
    }

    @GetMapping(name = "view_developers", path = "/view")
    @Operation(summary = "View Developers",
            description = "Search and view only one developer using developer ID")
    public ResponseEntity<List<DeveloperResponseDto>> viewDevelopers(){
        List<DeveloperResponseDto> developers = this.developerService.findAllDevelopers();
        return ResponseEntity.status(HttpStatus.OK).body(developers);
    }
}
