package com.eduardo.apiservidor.controller.email;

import com.eduardo.apiservidor.entity.usuario.Usuario;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.mensagem.CriacaoRascunhoDTO;
import com.eduardo.apiservidor.service.email.RascunhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/rascunhos")
public class RascunhoController {
    private final RascunhoService rascunhoService;

    @PostMapping()
    public ResponseEntity<CriacaoRascunhoDTO> handleCriar(@RequestBody EmailCriacaoDTO emailCriacaoDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(rascunhoService.criar(emailCriacaoDTO, token));
    }
}
