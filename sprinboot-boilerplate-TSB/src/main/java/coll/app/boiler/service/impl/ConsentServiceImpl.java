package coll.app.boiler.service.impl;

import coll.app.boiler.dto.mapper.ObjectDtoMapper;
import coll.app.boiler.dto.response.consent.ConsentResponseDto;
import coll.app.boiler.dto.response.consent.ConsentResponsePostPutPatchDto;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.request.consent.ConsentRequest;
import coll.app.boiler.model.response.consent.ConsentResponse;
import coll.app.boiler.model.response.consent.ConsentResponsePostPutPatch;
import coll.app.boiler.service.AuthService;
import coll.app.boiler.service.ConsentService;
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
public class ConsentServiceImpl implements ConsentService {


    @Value("${api.consent.uri}")
    private String consentsUrl;

    @Value("${api.max-attempt}")
    private Integer maxAttempt;
    @Value("${api.delay-millis}")
    private Integer delayMillis;

    private final WebClient webClient;
    private final AuthService authService;

    public ConsentServiceImpl(WebClient webClient,AuthService authService){
        this.webClient = webClient;
        this.authService = authService;
    }
    @Override
    public Mono<ConsentResponseDto> getConsents(
            String subject,
            String actor,
            String definition,
            String audience,
            String collaborator)
    {

        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                   HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(consentsUrl)
                            .queryParam("subject",subject)
                            .queryParam("actor",actor)
                            .queryParam("definition",definition)
                            .queryParam("audience",audience)
                            .queryParam("collaborator",collaborator);
                    log.info("Consents Request: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error consents status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch consents");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Consents Response: {}",response);
                                var res = JsonUtil.toObject(response, ConsentResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,ConsentResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching consents max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch consents");
                            });
                });

    }

    @Override
    public Mono<ConsentResponseDto> getConsent(String consentRecordId, String expand) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());

                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(consentsUrl)
                            .path(consentRecordId)
                            .queryParam("expand",expand);
                    log.info("ConsentByRecordId RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .get()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error consentByRecordId status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch consentByRecordId");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("ConsentByRecordId Response: {}",response);
                                var res = JsonUtil.toObject(response, ConsentResponse.class);
                                return ObjectDtoMapper.dtoMapper(res,ConsentResponseDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching consentByRecordId max attempt done: {}", error.getMessage());
                                throw new CustomException("451","Failed to fetch consentByRecordId");
                            });
                });

    }

    @Override
    public Mono<ConsentResponsePostPutPatchDto> createConsent(ConsentRequest consentRequest) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    log.info("Create consent Request: {} headers: {}",
                            JsonUtil.toJson(consentRequest),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .post()
                            .uri(consentsUrl)
                            .headers(head -> head.addAll(httpHeaders))
                            .bodyValue(consentRequest)
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error create consent status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch create consent");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Create consent Response: {}",response);
                                var res = JsonUtil.toObject(response, ConsentResponsePostPutPatch.class);
                                return ObjectDtoMapper.dtoMapper(res,ConsentResponsePostPutPatchDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching create consent max attempt done: {}",error.getMessage());
                                throw new CustomException("451","Failed to fetch create consent");
                            });
                });
    }

    @Override
    public Mono<ConsentResponsePostPutPatchDto> updateConsent(String consentRecordId, ConsentRequest consentRequest) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(consentsUrl)
                            .path(consentRecordId);
                    log.info("Update consent RequestBody: {} RequestPath: {} headers: {}",
                            JsonUtil.toJson(consentRequest),
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .put()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .bodyValue(consentRequest)
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error create consent status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch update consent");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Update consent Response: {}",response);
                                var res = JsonUtil.toObject(response, ConsentResponsePostPutPatch.class);
                                return ObjectDtoMapper.dtoMapper(res,ConsentResponsePostPutPatchDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching update consent max attempt done: {}",error.getMessage());
                                throw new CustomException("451","Failed to fetch update consent");
                            });
                });
    }

    @Override
    public Mono<ConsentResponsePostPutPatchDto> pathConsent(String consentRecordId, ConsentRequest consentRequest) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(consentsUrl)
                            .path(consentRecordId);
                    log.info("Patch consent RequestBody: {} RequestPath: {} headers: {}",
                            JsonUtil.toJson(consentRequest),
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .patch()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .bodyValue(consentRequest)
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error patch consent status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch patch consent");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(String.class)
                            .map(response -> {
                                log.info("Patch consent Response: {}",response);
                               var res = JsonUtil.toObject(response, ConsentResponsePostPutPatch.class);
                                return ObjectDtoMapper.dtoMapper(res,ConsentResponsePostPutPatchDto.class);
                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching patch consent max attempt done: {}",error.getMessage());
                                throw new CustomException("451","Failed to fetch patch consent");
                            });
                });
    }

    @Override
    public Mono<Void> deleteConsent(String consentRecordId) {
        return   authService
                .getToken()
                .flatMap(tokenResponse -> {
                    HttpHeaders httpHeaders = HeadersUtil.consentHeader(tokenResponse.token());
                    UriComponentsBuilder componentsBuilder = UriComponentsBuilder
                            .fromHttpUrl(consentsUrl)
                            .path(consentRecordId);
                    log.info("Delete consent  RequestPath: {} headers: {}",
                            componentsBuilder.toUriString(),
                            JsonUtil.toJson(httpHeaders));
                    return  webClient
                            .delete()
                            .uri(componentsBuilder.toUriString())
                            .headers(head -> head.addAll(httpHeaders))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                    response -> response.bodyToMono(String.class).flatMap(errorBody -> {
                                        log.error("Error delete consent status code {}, response body: {}",response.statusCode(), errorBody);
                                        CustomException customException = new CustomException("451","Failed to fetch delete consent");
                                        return Mono.error(customException);
                                    }))
                            .bodyToMono(Void.class)
//                            .map(response -> {
//                                log.info("Delete consent Response: {}",response);
//                                 return response;
//                            })
                            .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                            .onErrorResume(error -> {
                                log.error("Error while fetching delete consent max attempt done: {}",error.getMessage());
                                throw new CustomException("451","Failed to fetch delete consent");
                            });
                });
    }
}
