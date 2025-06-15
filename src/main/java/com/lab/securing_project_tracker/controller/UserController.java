package com.lab.securing_project_tracker.controller;


import com.lab.securing_project_tracker.dto.authentication.UserResponseDto;
import com.lab.securing_project_tracker.dto.skill.SkillDto;
import com.lab.securing_project_tracker.dto.skill.SkillResponseDto;
import com.lab.securing_project_tracker.mapper.SkillMapper;
import com.lab.securing_project_tracker.model.SkillEntity;
import com.lab.securing_project_tracker.service.SkillService;
import com.lab.securing_project_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@Tag(name = "User Controller", description = "Manage all the User's urls")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(name = "view_users", path = "/view")
    @Operation(summary = "View users",
            description = "This method applies pagination for efficient retrieval " +
                          "of users list")
    public Page<UserResponseDto> viewProjects(Pageable pageable){
        return this.userService.findAll(pageable);
    }

}
