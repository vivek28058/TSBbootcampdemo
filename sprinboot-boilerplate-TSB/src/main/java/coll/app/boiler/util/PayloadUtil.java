package coll.app.boiler.util;

import coll.app.boiler.model.request.kafka.Payload;
import coll.app.boiler.model.request.token.TokenRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PayloadUtil {

    private static String tokenClientId;
    private static String tokenClientSecret;

    @Autowired
    public void staticValue(
            @Value("${token.client-id}") String tokenClientId,
            @Value("${token.client-secret}") String tokenClientSecret){
        PayloadUtil.tokenClientId = tokenClientId;
        PayloadUtil.tokenClientSecret = tokenClientSecret;

    }

    public static TokenRequest tokenRequest(){
        return TokenRequest.builder()
                .clientId(tokenClientId)
                .clientSecret(tokenClientSecret)
                .build();
    }

    public static Payload sendPayloadRequest(Payload payload){
        return Payload.builder()
                .data(ValidationUtil.checkIfValueIsPresent(payload.getData(),"Payload parameter data"))
                .key(ValidationUtil.checkIfValueIsPresent(payload.getData(),"Payload parameter key"))
                .topic(ValidationUtil.checkIfValueIsPresent(payload.getData(),"Payload parameter topic"))
                .build();
    }


}
