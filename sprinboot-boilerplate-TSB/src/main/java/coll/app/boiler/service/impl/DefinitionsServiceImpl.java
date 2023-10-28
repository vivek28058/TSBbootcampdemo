package coll.app.boiler.service.impl;

import coll.app.boiler.dto.mapper.ObjectDtoMapper;
import coll.app.boiler.dto.response.definition.DefinitionsResponseDto;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.response.definitions.DefinitionsResponse;
import coll.app.boiler.service.AuthService;
import coll.app.boiler.service.DefinitionsService;
import coll.app.boiler.util.HeadersUtil;
import coll.app.boiler.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@Slf4j
public class DefinitionsServiceImpl implements DefinitionsService {

    @Value("${api.definition-consent.uri}")
    private String definitionConsentUrl;

    @Value("${api.definition.uri}")
    private String definitionUrl;

    @Value("${api.max-attempt}")
    private Integer maxAttempt;
    @Value("${api.delay-millis}")
    private Integer delayMillis;
    private final AuthService authService;
    private final WebClient webClient;

    public DefinitionsServiceImpl(WebClient webClient,AuthService authService){
        this.authService = authService;
        this.webClient = webClient;
    }
    @Override
    public Mono<DefinitionsResponseDto> getDefinitions(String filter) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(definitionConsentUrl)
                            .queryParam("filter",filter);
                    log.info("Definitions RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error definitions status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch definitions");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Definitions Response: {}",response);
                                var res = JsonUtil.toObject(response, DefinitionsResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,DefinitionsResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching definitions max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch definitions");
                            });
                });
    }

    @Override
    public Mono<DefinitionsResponseDto> getDefinitionById(String id, String expand) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(definitionUrl)
                            .path(id)
                            .queryParam("expand",expand);
                    log.info("DefinitionById RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error definitionById status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch definitionById");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("DefinitionById Response: {}",response);
                                var res = JsonUtil.toObject(response, DefinitionsResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,DefinitionsResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching definitionById max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch definitionById");
                            });
                });
    }

    @Override
    public Mono<DefinitionsResponseDto> getLocalizations(String id, String filter) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(definitionUrl)
                            .path(id)
                            .path("localizations")
                            .queryParam("filter",filter);
                    log.info("Localizations RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error localizations status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch localizations");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Localizations Response: {}",response);
                                var res = JsonUtil.toObject(response, DefinitionsResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,DefinitionsResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching localizations max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch localizations");
                            });
                });
    }

    @Override
    public Mono<DefinitionsResponseDto> getLocalizationById(String id, String locale, String expand) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(definitionUrl)
                            .path(id)
                            .path("localizations")
                            .path(locale)
                            .queryParam("expand",expand);
                    log.info("LocalizationById RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error localizationById status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch localizationById");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("LocalizationById Response: {}",response);
                                var res = JsonUtil.toObject(response, DefinitionsResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,DefinitionsResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching localizationById max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch localizationById");
                            });
                });
    }
}
