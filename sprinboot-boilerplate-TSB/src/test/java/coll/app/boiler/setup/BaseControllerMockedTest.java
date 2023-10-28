package coll.app.boiler.setup;

import coll.app.boiler.controller.impl.ConsentController;
import coll.app.boiler.controller.impl.DefinitionsController;
import coll.app.boiler.controller.impl.UserInfoController;
import coll.app.boiler.dto.mapper.ObjectDtoMapper;
import coll.app.boiler.dto.response.consent.ConsentResponseDto;
import coll.app.boiler.dto.response.consent.ConsentResponsePostPutPatchDto;
import coll.app.boiler.dto.response.definition.DefinitionsResponseDto;
import coll.app.boiler.enums.ConsentType;
import coll.app.boiler.exception.CustomException;
import coll.app.boiler.model.error.userInfo.UserInfoError;
import coll.app.boiler.model.request.consent.ConsentRequest;
import coll.app.boiler.model.request.token.TokenRequest;
import coll.app.boiler.model.request.userInfo.UserInfoRequest;
import coll.app.boiler.model.response.consent.ConsentResponse;
import coll.app.boiler.model.response.consent.ConsentResponsePostPutPatch;
import coll.app.boiler.model.response.definitions.DefinitionsResponse;
import coll.app.boiler.model.response.token.TokenResponse;
import coll.app.boiler.model.response.userInfo.UserInfoResponse;
import coll.app.boiler.service.AuthService;
import coll.app.boiler.service.ConsentService;
import coll.app.boiler.service.DefinitionsService;
import coll.app.boiler.service.UserInfoService;
import coll.app.boiler.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({
        UserInfoController.class,
        ConsentController.class,
        DefinitionsController.class
})
public class BaseControllerMockedTest {

    @Autowired
    protected  WebTestClient webTestClient;

    @MockBean
    protected UserInfoService userInfoService;

    @MockBean
    protected ConsentService consentService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected DefinitionsService definitionsService;
    protected UserInfoRequest userInfoRequest;
    protected UserInfoResponse userInfoResponse;
    protected UserInfoError userInfoError;
    protected CustomException customException;
    protected ConsentResponseDto READ_consents_response;
    protected ConsentResponseDto READ_a_consent_response;
    protected ConsentResponsePostPutPatchDto CREATE_a_consent_response;
    protected ConsentResponsePostPutPatchDto REPLACE_a_consent_response;
    protected ConsentResponsePostPutPatchDto UPDATE_a_consent_response;
    protected ConsentRequest consent_request;
    protected DefinitionsResponseDto READ_definitions_response;
    protected DefinitionsResponseDto READ_a_definition_response;
    protected DefinitionsResponseDto READ_localizations_response;
    protected DefinitionsResponseDto READ_a_localization_response;
    protected TokenResponse tokenResponse;
    protected TokenRequest tokenRequest;
    protected CustomException  customExceptionConsents;
    protected CustomException customExceptionConsentByRecordId;
    protected CustomException customExceptionConsentCreate;
    protected CustomException customExceptionConsentUpdate;
    protected CustomException customExceptionConsentPatch;
    protected CustomException customExceptionConsentDelete;
    protected CustomException customExceptionDefinitions;
    protected CustomException customExceptionDefinitionById;
    protected CustomException customExceptionLocalizations;
    protected CustomException customExceptionLocalizationById;



    @BeforeEach
    void setup(){

        userInfoRequest =   JsonUtil.toObjectFromFile(UserInfoRequest.class, ConsentType.NONE);
        userInfoResponse =  JsonUtil.toObjectFromFile(UserInfoResponse.class, ConsentType.NONE);
        userInfoError =   JsonUtil.toObjectFromFile(UserInfoError.class, ConsentType.NONE);

        tokenResponse = TokenResponse.builder()
                .token("Az90SAOJklae")
                .tokenType("Bearer")
                .build();

        tokenRequest = TokenRequest.builder()
                .clientId("clientId")
                .clientSecret("secret")
                .build();

        var READ_consents = JsonUtil.toObjectFromFile(ConsentResponse.class, ConsentType.CONSENT_READ_consents);
        var READ_a_consent = JsonUtil.toObjectFromFile(ConsentResponse.class,ConsentType.CONSENT_READ_a_consent);
        var CREATE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_CREATE_a_consent);
        var REPLACE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_REPLACE_a_consent);
        var UPDATE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_UPDATE_a_consent);

        READ_consents_response = ObjectDtoMapper.dtoMapper(READ_consents, ConsentResponseDto.class);
        READ_a_consent_response =  ObjectDtoMapper.dtoMapper(READ_a_consent,ConsentResponseDto.class);
        CREATE_a_consent_response =  ObjectDtoMapper.dtoMapper(CREATE_a_consent, ConsentResponsePostPutPatchDto.class);
        REPLACE_a_consent_response =  ObjectDtoMapper.dtoMapper(REPLACE_a_consent,ConsentResponsePostPutPatchDto.class);
        UPDATE_a_consent_response = ObjectDtoMapper.dtoMapper(UPDATE_a_consent,ConsentResponsePostPutPatchDto.class);
        consent_request =  JsonUtil.toObjectFromFile(ConsentRequest.class,ConsentType.NONE);

        var READ_definitions = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_definitions);
        var READ_a_definition = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_a_definition);
        var READ_localizations = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_localizations);
        var READ_a_localization = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_a_localization);

        READ_definitions_response =  ObjectDtoMapper.dtoMapper(READ_definitions, DefinitionsResponseDto.class);
        READ_a_definition_response =  ObjectDtoMapper.dtoMapper(READ_a_definition,DefinitionsResponseDto.class);
        READ_localizations_response =  ObjectDtoMapper.dtoMapper(READ_localizations,DefinitionsResponseDto.class);
        READ_a_localization_response =  ObjectDtoMapper.dtoMapper(READ_a_localization,DefinitionsResponseDto.class);

        customException = new CustomException("451","Failed to fetch user information");

        customExceptionConsents = new CustomException("451","Failed to fetch consents");
        customExceptionConsentByRecordId = new CustomException("451","Failed to fetch consentByRecordId");
        customExceptionConsentCreate = new CustomException("451","Failed to fetch create consent");
        customExceptionConsentUpdate = new CustomException("451","Failed to fetch update consent");
        customExceptionConsentPatch = new CustomException("451","Failed to fetch patch consent");
        customExceptionConsentDelete = new CustomException("451","Failed to fetch delete consent");

        customExceptionDefinitions = new CustomException("451","Failed to fetch definitions");
        customExceptionDefinitionById = new CustomException("451","Failed to fetch definitionById");
        customExceptionLocalizations = new CustomException("451","Failed to fetch localizations");
        customExceptionLocalizationById = new CustomException("451","Failed to fetch localizationById");
    }

}
