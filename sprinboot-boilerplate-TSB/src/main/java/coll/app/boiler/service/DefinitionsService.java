package coll.app.boiler.service;

import coll.app.boiler.dto.response.definition.DefinitionsResponseDto;
import coll.app.boiler.model.response.definitions.DefinitionsResponse;
import reactor.core.publisher.Mono;

public interface DefinitionsService {

    Mono<DefinitionsResponseDto> getDefinitions(String filter);
    Mono<DefinitionsResponseDto> getDefinitionById(String id,String expand);
    Mono<DefinitionsResponseDto> getLocalizations(String id,String filter);
    Mono<DefinitionsResponseDto> getLocalizationById(String id,String locale,String expand);
}
