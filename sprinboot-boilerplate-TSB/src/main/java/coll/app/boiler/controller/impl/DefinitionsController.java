package coll.app.boiler.controller.impl;

import coll.app.boiler.controller.ConsentApiVersion;
import coll.app.boiler.dto.response.definition.DefinitionsResponseDto;
import coll.app.boiler.model.response.definitions.DefinitionsResponse;
import coll.app.boiler.service.DefinitionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class DefinitionsController implements ConsentApiVersion {

    private final DefinitionsService definitionsService;

    DefinitionsController(DefinitionsService definitionsService){
     this.definitionsService = definitionsService;
    }

    @GetMapping("/definitions")
    public Mono<DefinitionsResponseDto> getDefinitionsController(
            @RequestParam("filter")
            String filter)
    {
        return definitionsService.getDefinitions(filter);
    }

    @GetMapping("/definitions/{id}")
    public Mono<DefinitionsResponseDto> getDefinitionByIdController(
            @PathVariable String id,
            @RequestParam("expand") String expand)
    {
        return definitionsService.getDefinitionById(id, expand);
    }

    @GetMapping("/definitions/{id}/localizations")
    public Mono<DefinitionsResponseDto> getLocalizationsController(
            @PathVariable String id,
            @RequestParam("filter") String filter)
    {
        return definitionsService.getLocalizations(id, filter);
    }

    @GetMapping("/definitions/{id}/localizations/{locale}")
    public Mono<DefinitionsResponseDto> getLocalizationByIdController(
            @PathVariable String id,
            @PathVariable String locale,
            @RequestParam("expand") String expand)
    {
        return definitionsService.getLocalizationById(id, locale, expand);
    }
}
