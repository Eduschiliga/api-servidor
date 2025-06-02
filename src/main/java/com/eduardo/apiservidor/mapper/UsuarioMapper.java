package com.eduardo.apiservidor.mapper;

import com.eduardo.apiservidor.entity.Usuario;
import com.eduardo.apiservidor.model.dto.usuario.CriacaoUsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.UsuarioDTO;
import com.eduardo.apiservidor.model.dto.usuario.UsuarioResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
  Usuario usuarioCriacaoDTOtoUsuario(CriacaoUsuarioDTO criacaoUsuarioDTO);
  UsuarioDTO usuarioToUsuarioDTO(Usuario usuario);
  UsuarioResponseDTO usuarioToUsuarioResponseDTO(Usuario usuario);
}
