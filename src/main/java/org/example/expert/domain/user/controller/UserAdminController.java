package org.example.expert.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.request.UserRoleChangeRequest;
import org.example.expert.domain.user.service.UserService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.example.expert.domain.common.annotation.AdminLog;

@RestController
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @AdminLog
    @PatchMapping("/admin/users/{userId}")
    public void changeUserRole(@PathVariable long userId, @RequestBody UserRoleChangeRequest userRoleChangeRequest) {
        userService.changeUserRole(userId, userRoleChangeRequest);
    }
}
