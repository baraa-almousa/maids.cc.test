package com.java.springBoot.app.RestController.Basic;

import com.java.springBoot.app.SecurityConfig.JWT.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // محاولة المصادقة باستخدام اسم المستخدم وكلمة المرور
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // تعيين المصادقة في السياق الأمني
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // توليد التوكن باستخدام اسم المستخدم من المصادقة
            String token = jwtUtil.generateToken(authentication.getName()); // استخدام اسم المستخدم مباشرة

            // إرجاع التوكن
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            // في حال كانت البيانات غير صحيحة
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

// كلاس الطلب من العميل (اسم المستخدم وكلمة المرور)
class AuthRequest {
    private String username;
    private String password;

    // Getter and Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

// كلاس الاستجابة مع التوكن
class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}