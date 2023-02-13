package com.biglobby.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.User;
import com.biglobby.repository.UserRepository;
import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.entity.UserSchema;
import com.biglobby.server.chat.repository.ChatRoomSchemaRepository;
import com.biglobby.server.chat.repository.UserSchemaRepository;
import com.biglobby.utils.AES256.AES256;
import com.biglobby.utils.jwt.JwtTokenUtil;

@RestController
@CrossOrigin("*")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserSchemaRepository userSchemaRepo;

    @Autowired
    private ChatRoomSchemaRepository roomSchemaRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authentication/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data) {
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        return loginMethod(username, password);
    }

    @PostMapping("/authentication/encrypt")
    public ResponseEntity<Map<String, String>> enCryptUserData(@RequestBody Map<String, String> data) {
        Map<String, String> res = new HashMap<>();
        String username = data.get("username").trim();
        String password = data.get("password").trim();
        String userInfo = username + ";" + password;
        String encrypted = AES256.encrypt(userInfo);
        if (encrypted != null) {
            res.put("_biglobby_udata_", encrypted);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/authentication/validate")
    public ResponseEntity<Map<String, Object>> validateUserData(@RequestBody Map<String, String> encryptedData) {
        String encryptedUserInfo = encryptedData.get("_biglobby_udata_");
        String decrypted = AES256.decrypt(encryptedUserInfo);
        try {
            String[] udata = decrypted.split(";");
            String username = udata[0].trim();
            String password = udata[1].trim();
            return loginMethod(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private ResponseEntity<Map<String, Object>> loginMethod(String username, String password) {

        try {
            System.out.println(username + " - " + password);
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authenticate
                    .getPrincipal();
            User userReturn = userRepo.findByUsername(username).orElse(null);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            Optional<UserSchema> userSchema = userSchemaRepo.findByUsername(username);
            Map<String, Object> res = new HashMap<String, Object>();
            res.put("user", userReturn);
            if (userSchema.isPresent()) {
                List<ChatRoomSchema> rooms = roomSchemaRepo.getByUsername(username);
                res.put("rooms", rooms);
            }
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateToken(user))
                    .body(res);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
