package coll.app.boiler.sidecars;

import coll.app.boiler.exception.CustomException;
import coll.app.boiler.util.HeadersUtil;
import coll.app.boiler.util.PayloadUtil;
import lombok.extern.slf4j.Slf4j;
import coll.app.boiler.model.request.kafka.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
public class KafkaSidecar {
    private final WebClient webClient;
    @Value("${api.producer.uri}")
    private String producerUri;

    @Value("${api.max-attempt}")
    private Integer maxAttempt;

    @Value("${api.delay-millis}")
    private Integer delayMillis;

    KafkaSidecar(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<Void> sendPayload(Payload payload){
        log.info("Payload Request: {}",payload);
        HttpHeaders headers = HeadersUtil.defaultHeader();
        var payloadRequest= PayloadUtil.sendPayloadRequest(payload);

        webClient
                .post()
                .uri(producerUri)
                .headers(head -> head.addAll(headers))
                .bodyValue(payloadRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> {
                            return response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.error("Error status code {}, response body: {}",response.statusCode(), errorBody);
                                CustomException customException = new CustomException("451","Failed to sending payload");
                                return Mono.error(customException);
                            });
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> {
                            return response.bodyToMono(String.class).flatMap(errorBody -> {
                                log.error("Error status code {}, response body: {}",response.statusCode(), errorBody);
                                CustomException customException = new CustomException("451","Failed to sending payload");
                                return Mono.error(customException);
                            });
                        })
                .bodyToMono(String.class)
                .map(response -> {
                    log.info("Payload Response: {}",response);
                    return response;
                })
                .retryWhen(Retry.fixedDelay(maxAttempt, Duration.ofMillis(delayMillis)))
                .onErrorResume(error -> {
                    log.error("Error while sending payload max attempt done: {}", error.getMessage());
                    throw new CustomException("451","Failed to sending payload");
                });

        return Mono.empty();
    }
}
