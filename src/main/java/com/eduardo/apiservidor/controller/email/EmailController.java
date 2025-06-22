package com.eduardo.apiservidor.controller.email;

import com.eduardo.apiservidor.model.dto.email.CriacaoEmailDTO;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.email.EmailListaDTO;
import com.eduardo.apiservidor.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/emails")
public class EmailController {
    private final EmailService emailService;

    @PostMapping()
    public ResponseEntity<CriacaoEmailDTO> handleCriarEmail(@RequestBody EmailCriacaoDTO emailCriacaoDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.enviarEmail(emailCriacaoDTO, token));
    }

    @PostMapping("{rascunhoId}")
    public ResponseEntity<CriacaoEmailDTO> handleCriarEmailAPartirDeRascunho(@PathVariable Long rascunhoId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.enviarRascunho(rascunhoId, token));
    }

    @GetMapping("{emailId}")
    public ResponseEntity<CriacaoEmailDTO> handleBuscarPorId(@PathVariable Long emailId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.buscarEmailPorIdEMarcarComoLido(emailId, token));
    }

    @GetMapping("")
    public ResponseEntity<EmailListaDTO> handleBuscarTodos(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.emailTodosEmails(token));
    }
}
