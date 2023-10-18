package com.intellivix.controller;

import com.intellivix.config.security.CustomUserDetailsService;
import com.intellivix.enums.StatusEnum;
import com.intellivix.model.admin;
import com.intellivix.model.message;
import com.intellivix.model.request.AuthenticationRequest;
import com.intellivix.model.response.AuthenticationResponse;
import com.intellivix.service.adminService;
import com.intellivix.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.*;

@RestController
@RequestMapping(value = "/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    private final adminService adminService;

    private final CustomUserDetailsService customUserDetailsService;

    message msg = new message();
    HttpHeaders headers = new HttpHeaders();

    @PostMapping("/login")
    public ResponseEntity<message> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getId(),
                            authenticationRequest.getPassword()
                    )
            );
            admin admin = adminService.adminInfo(authenticationRequest.getId());
            Map<String, Object> resp = new HashMap<>();
            resp.put("admin",admin);
            resp.put("token",jwtTokenUtil.generateToken(authenticationRequest.getId()));
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(resp);
            return new ResponseEntity<>(msg, headers, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


    }

    @GetMapping("/list")
    public ResponseEntity<message> readAdmin(@RequestParam(required = false) String searchValue, @RequestParam(required = false) int page) {
       // message msg = new message();
       // HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        if (searchValue != null) {
            Map<String, Object> admin = adminService.readAdmin(searchValue, page, 10);

            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(admin);
            return new ResponseEntity<>(msg, headers, HttpStatus.OK);
        }
        Map<String, Object>  admins = adminService.readAdmins(page, 10);


        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(admins);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signupAdmin(@RequestBody admin admin) throws Exception {
        try {
            adminService.signAdmin(admin);
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(admin.getId() + "  가입 완료");

            return new ResponseEntity<>(msg, headers, HttpStatus.OK);

        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity modifyAdmin(@RequestBody admin admin) throws Exception {
        try {
            adminService.updateId(admin.getNo(), admin.getGrade(), admin.getPassword());
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData("수정 완료");

            return new ResponseEntity<>(msg, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("");
        }
    }

    @GetMapping("/adminInfo")
    public ResponseEntity adminInfo(@RequestParam Long no) throws Exception {
        try {
            admin admin = adminService.detailAdmin(no);
            msg.setStatus(StatusEnum.OK);
            msg.setMessage("성공 코드");
            msg.setData(admin);

            return new ResponseEntity<>(msg, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("not found user");
        }
    }

    @GetMapping("/dupChk")
    public ResponseEntity dupCheck(@RequestParam String id) throws Exception {

        Long dup = adminService.dupCheck(id);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(dup);

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<message> deleteNotice (@RequestBody List<Long> no) throws Exception {
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        adminService.deleteAdmin(no);

        msg.setStatus(StatusEnum.OK);
        msg.setMessage("성공 코드");
        msg.setData(no+" 삭제 성공");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }



}
