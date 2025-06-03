package com.eduardo.apiservidor.controller;

import com.eduardo.apiservidor.model.dto.mensagem.MensagemSucessoDTO;
import com.eduardo.apiservidor.model.dto.usuario.AtualizarUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.CriacaoUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.RetornoUsuarioDTO;
import com.eduardo.apiservidor.service.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @CrossOrigin(origins = "*")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Cria um usuário")
    public ResponseEntity<MensagemSucessoDTO> handleCriar(@RequestBody CriacaoUsuarioDTO criacaoUsuarioDTO) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(usuarioService.salvar(criacaoUsuarioDTO));
    }

    @GetMapping()
    @CrossOrigin(origins = "*")
    @Operation(summary = "Busca os dados de um usuario")
    public ResponseEntity<RetornoUsuarioDTO> handleBuscarDados(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuarioPeloToken(token));
    }

    @PutMapping()
    @CrossOrigin(origins = "*")
    @Operation(summary = "Atualiza os dados de um usuário")
    public ResponseEntity<RetornoUsuarioDTO> handleAtualizar(@RequestHeader("Authorization") String token, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDto) {
        return ResponseEntity.ok().body(usuarioService.atualizar(token, atualizarUsuarioDto));
    }

    @DeleteMapping()
    @CrossOrigin(origins = "*")
    @Operation(summary = "Deleta um usuário")
    public ResponseEntity<Void> handleDeletar(@RequestHeader("Authorization") String token) {
        usuarioService.deletar(token);
        return ResponseEntity.ok().build();
    }
}
