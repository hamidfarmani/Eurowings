package com.eurowings.newsletter;


import com.eurowings.newsletter.controller.UserController;
import com.eurowings.newsletter.model.User;
import com.eurowings.newsletter.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    public void createUser_BasicTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User actual = new User();
        actual.setId(1L);
        when(userService.saveUser(any(User.class))).thenReturn(actual);
        ResponseEntity<Object> responseEntity = userController.createUser(new User());

        assertEquals(responseEntity.getStatusCodeValue(),201);
        assertEquals(responseEntity.getHeaders().getLocation().getPath(),"/1");
    }


    @Test
    public void retrieveUsers_BasicTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());

        when(userService.retrieveAllUsers()).thenReturn(list);
        int users = userController.retrieveAllUsers().size();

        assertEquals(users,2);
    }
}
