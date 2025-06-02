package com.eduardo.apiservidor.controller;

import com.eduardo.apiservidor.model.dto.jwt.TokenDTO;
import com.eduardo.apiservidor.model.request.LoginRequest;
import com.eduardo.apiservidor.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  @Operation(summary = "Realiza o login")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setToken(authService.authLogin(loginRequest));

    return ResponseEntity.ok().body(tokenDTO);
  }
}
