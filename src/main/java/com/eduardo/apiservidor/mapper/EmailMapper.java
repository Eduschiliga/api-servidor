package com.eduardo.apiservidor.mapper;

import com.eduardo.apiservidor.entity.email.Email;
import com.eduardo.apiservidor.model.dto.email.EmailCriacaoDTO;
import com.eduardo.apiservidor.model.dto.email.EmailDTO;
import com.eduardo.apiservidor.model.dto.email.rascunho.RascunhoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    EmailDTO entityToEmailDto(Email entity);

    List<EmailDTO> entityListToEmailListDto(List<Email> entity);

    @Mapping(source = "emailId", target = "rascunhoId")
    RascunhoDTO entityToRascunhoDto(Email entity);

    List<RascunhoDTO> entityToRascunhoDtoList(List<Email> entity);

    Email criacaoDtoToEmailEntity(EmailCriacaoDTO dto);

    EmailCriacaoDTO emailToEmailCriacaoDto(Email dto);
}
