package com.mca.nick.service.mapper;

import com.mca.nick.domain.*;
import com.mca.nick.service.dto.ClientConfigurationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientConfiguration} and its DTO {@link ClientConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientConfigurationMapper extends EntityMapper<ClientConfigurationDTO, ClientConfiguration> {



    default ClientConfiguration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setId(id);
        return clientConfiguration;
    }
}
