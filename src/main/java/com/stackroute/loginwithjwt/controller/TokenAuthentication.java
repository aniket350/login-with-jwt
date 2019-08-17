package com.stackroute.loginwithjwt.controller;

import java.util.Objects;

import com.stackroute.loginwithjwt.config.JWTOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.stackroute.loginwithjwt.service.JWTUserDetail;
import com.stackroute.loginwithjwt.config.JWTOperations;
import com.stackroute.loginwithjwt.model.JWTResponse;
import com.stackroute.loginwithjwt.model.JWTRequest;

@RestController
@CrossOrigin

public class TokenAuthentication {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTOperations jwtOperations;
    @Autowired
    private JWTUserDetail userDetailsService;
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtOperations.generateToken(userDetails);
        return ResponseEntity.ok(new JWTResponse(token));
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}