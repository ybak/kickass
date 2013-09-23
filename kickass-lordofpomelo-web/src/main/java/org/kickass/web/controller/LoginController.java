package org.kickass.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.kickass.lordofpomelo.domain.User;
import org.kickass.lordofpomelo.repository.UserRepository;
import org.kickass.lordofpomelo.util.Token;
import org.kickass.web.vo.HomeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return createJsonResponseEntity(HomeResponse.ERROR_500);
        }
        try {
            User user = userRepository.getUserByName(username);
            if (!user.getPassword().equals(password)) {
                return createJsonResponseEntity(HomeResponse.ERROR_501);
            }
            String token = Token.create(user.getId(), System.currentTimeMillis(), user.getPassword());
            return createJsonResponseEntity(new HomeResponse(200, token, user.getId()).toString());
        } catch (Exception e) {
            return createJsonResponseEntity(HomeResponse.ERROR_500);
        }
    }

    private ResponseEntity<String> createJsonResponseEntity(String response) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(response, headers, HttpStatus.OK);
    }
}