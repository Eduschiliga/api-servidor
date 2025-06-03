package com.eduardo.apiservidor.controller;

import com.eduardo.apiservidor.model.dto.jwt.TokenDTO;
import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.model.request.LoginRequest;
import com.eduardo.apiservidor.service.auth.AuthService;
import com.eduardo.apiservidor.service.jwt.ListaPretaTokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
  private final AuthService authService;
  private final ListaPretaTokenService listaPretaTokenService;

  @PostMapping("/login")
  @CrossOrigin(origins = "*")
  @Operation(summary = "Realiza o login")
  public ResponseEntity<TokenDTO> handleLogin(@RequestBody LoginRequest loginRequest) {
    TokenDTO tokenDTO = new TokenDTO();
    tokenDTO.setToken(authService.authLogin(loginRequest));

    return ResponseEntity.ok().body(tokenDTO);
  }

  @PostMapping("/logout")
  @CrossOrigin(origins = "*")
  @Operation(summary = "Realiza a invalidacao de um token")
  public ResponseEntity<MensagemSucessoDTO> handleLogout(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok().body(listaPretaTokenService.adicionarNaListaPreta(token));
  }
}
