package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.AdminUser;
import com.example.myrestfulservice.bean.AdminUserV2;
import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.dao.UserDaoService;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    private final UserDaoService userService;

    //@GetMapping("/v1/users/{id}")
    @GetMapping(value = "/users/{id}", params = "version=1")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = userService.findOne(id);
        AdminUser adminUser = new AdminUser();
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
        }
        // json 필터 적용
        // User 객체와 다르게 AdminUser는 response에 ssn 필드 보여주도록 필터 추가
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name"
                , "joinDate", "ssn");
        // 필터 -> AdminUser에 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    //@GetMapping("/v2/users/{id}")
    @GetMapping(value = "/users/{id}", params = "version=2")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
        User user = userService.findOne(id);
        AdminUserV2 adminUser = new AdminUserV2();
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP");
        }
        // json 필터 적용
        // User 객체와 다르게 AdminUser는 response에 ssn 필드 보여주도록 필터 추가
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name"
                , "joinDate", "grade");
        // 필터 -> AdminUser에 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users = userService.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        for (User user : users) {
            AdminUser adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);
            adminUsers.add(adminUser);
        }

        // json 필터 적용
        // User 객체와 다르게 AdminUser는 response에 ssn 필드 보여주도록 필터 추가
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name"
                , "joinDate", "ssn");
        // 필터 -> AdminUser에 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);
        return mapping;
    }
}
