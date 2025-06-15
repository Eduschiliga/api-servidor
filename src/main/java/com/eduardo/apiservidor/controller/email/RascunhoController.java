package com.eduardo.apiservidor.controller.email;

import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.CriacaoRascunhoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoListaDTO;
import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.service.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/rascunhos")
public class RascunhoController {
    private final EmailService emailService;

    @PostMapping()
    @CrossOrigin("*")
    @Operation(summary = "Cria um Rascunho para um usuário")
    public ResponseEntity<CriacaoRascunhoDTO> handleCriar(@RequestBody EmailCriacaoDTO emailCriacaoDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.criarRascunho(emailCriacaoDTO, token));
    }

    @PutMapping()
    @CrossOrigin("*")
    @Operation(summary = "Cria um Rascunho de um usuário")
    public ResponseEntity<CriacaoRascunhoDTO> handleAtualizar(@RequestBody RascunhoDTO rascunhoDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.atualizarRascunho(rascunhoDTO, token));
    }

    @GetMapping("{rascunhoId}")
    @CrossOrigin("*")
    @Operation(summary = "Busca um Rascunho de um usuário pelo Id")
    public ResponseEntity<CriacaoRascunhoDTO> handleBuscarPorID(@PathVariable Long rascunhoId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.buscarRascunhoPorId(rascunhoId, token));
    }

    @DeleteMapping("{rascunhoId}")
    @CrossOrigin("*")
    @Operation(summary = "Deleta um rascunho de um usuário pelo Id")
    public ResponseEntity<MensagemSucessoDTO> handleDeletarPorId(@PathVariable Long rascunhoId, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.deletarRascunhoPorId(rascunhoId, token));
    }

    @GetMapping()
    @CrossOrigin("*")
    @Operation(summary = "Busca todos os rascunhos de um usuário")
    public ResponseEntity<RascunhoListaDTO> handleBuscarTodos(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(emailService.buscarTodosRascunhos(token));
    }
}
