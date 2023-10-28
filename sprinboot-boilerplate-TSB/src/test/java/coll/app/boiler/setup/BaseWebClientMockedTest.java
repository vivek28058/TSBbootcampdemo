package coll.app.boiler.setup;

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
import coll.app.boiler.service.impl.AuthServiceImpl;
import coll.app.boiler.service.impl.ConsentServiceImpl;
import coll.app.boiler.service.impl.DefinitionsServiceImpl;
import coll.app.boiler.service.impl.UserInfoServiceImpl;
import coll.app.boiler.util.JsonUtil;
import coll.app.boiler.util.PayloadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;


//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
public class BaseWebClientMockedTest {

    @Mock
    protected WebClient.RequestBodyUriSpec requestBodyUriMock;

    @Mock
    protected WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    protected WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    protected WebClient.RequestBodySpec requestBodyMock;
    @Mock
    protected WebClient.ResponseSpec responseMock;
    @Mock
    protected WebClient webClientMock;

    @Mock
    protected CustomWeClientResponseSpec responseSpecMock;

    @InjectMocks
    protected  UserInfoServiceImpl userInfoServiceImpl;

    protected UserInfoRequest userInfoRequest;
    protected UserInfoResponse userInfoResponse;
    protected UserInfoError userInfoError;
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

    protected CustomException customException;

    protected AuthService authService;

    protected AuthServiceImpl authServiceImpl;

    protected ConsentServiceImpl consentServiceImpl;

    protected ConsentService consentService;
    protected DefinitionsServiceImpl definitionsServiceImpl;
    protected DefinitionsService definitionsService;
    protected TokenResponse tokenResponse;
    protected TokenRequest tokenRequest;
    protected CustomException customExceptionToken;
    protected CustomException customExceptionUserInfo;
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

    @Value("${api.user-info.uri}")
    protected String userInfoUri;

    @Value("${api.token.uri}")
    protected String tokenUrl;

    @Value("${api.consent.uri}")
    protected String consentsUrl;

    @Value("${api.definition-consent.uri}")
    protected String definitionConsentUrl;

    @Value("${api.definition.uri}")
    protected String definitionUrl;


    @BeforeEach
    public void setUp(){

        userInfoServiceImpl = new UserInfoServiceImpl(webClientMock);

        ReflectionTestUtils.setField(userInfoServiceImpl,"userInfoUri",userInfoUri);
        ReflectionTestUtils.setField(userInfoServiceImpl,"maxAttempt",3);
        ReflectionTestUtils.setField(userInfoServiceImpl,"delayMillis",1000);

        ReflectionTestUtils.setField(PayloadUtil.class,"tokenClientId","clientId");
        ReflectionTestUtils.setField(PayloadUtil.class,"tokenClientSecret","secret");


        authServiceImpl = new AuthServiceImpl(webClientMock);
        ReflectionTestUtils.setField(authServiceImpl,"tokenUrl",tokenUrl);
        ReflectionTestUtils.setField(authServiceImpl,"maxAttempt",3);
        ReflectionTestUtils.setField(authServiceImpl,"delayMillis",1000);

        authService = new AuthServiceImpl(webClientMock);
        ReflectionTestUtils.setField(authService,"tokenUrl",tokenUrl);
        ReflectionTestUtils.setField(authService,"maxAttempt",3);
        ReflectionTestUtils.setField(authService,"delayMillis",1000);

        consentService = new ConsentServiceImpl(webClientMock,authService);
        ReflectionTestUtils.setField(consentService,"consentsUrl",consentsUrl);
        ReflectionTestUtils.setField(consentService,"maxAttempt",3);
        ReflectionTestUtils.setField(consentService,"delayMillis",1000);

        definitionsService = new DefinitionsServiceImpl(webClientMock,authService);
        ReflectionTestUtils.setField(definitionsService,"definitionConsentUrl",definitionConsentUrl);
        ReflectionTestUtils.setField(definitionsService,"definitionUrl",definitionUrl);
        ReflectionTestUtils.setField(definitionsService,"maxAttempt",3);
        ReflectionTestUtils.setField(definitionsService,"delayMillis",1000);

        userInfoRequest =   JsonUtil.toObjectFromFile(UserInfoRequest.class, ConsentType.NONE);
        userInfoResponse =  JsonUtil.toObjectFromFile(UserInfoResponse.class, ConsentType.NONE);
        userInfoError =   JsonUtil.toObjectFromFile(UserInfoError.class, ConsentType.NONE);

        var READ_consents = JsonUtil.toObjectFromFile(ConsentResponse.class, ConsentType.CONSENT_READ_consents);
        var READ_a_consent = JsonUtil.toObjectFromFile(ConsentResponse.class,ConsentType.CONSENT_READ_a_consent);
        var CREATE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_CREATE_a_consent);
        var REPLACE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_REPLACE_a_consent);
        var UPDATE_a_consent = JsonUtil.toObjectFromFile(ConsentResponsePostPutPatch.class,ConsentType.CONSENT_UPDATE_a_consent);

        READ_consents_response = ObjectDtoMapper.dtoMapper(READ_consents,ConsentResponseDto.class);
        READ_a_consent_response =  ObjectDtoMapper.dtoMapper(READ_a_consent,ConsentResponseDto.class);
        CREATE_a_consent_response =  ObjectDtoMapper.dtoMapper(CREATE_a_consent,ConsentResponsePostPutPatchDto.class);
        REPLACE_a_consent_response =  ObjectDtoMapper.dtoMapper(REPLACE_a_consent,ConsentResponsePostPutPatchDto.class);
        UPDATE_a_consent_response = ObjectDtoMapper.dtoMapper(UPDATE_a_consent,ConsentResponsePostPutPatchDto.class);
        consent_request =  JsonUtil.toObjectFromFile(ConsentRequest.class,ConsentType.NONE);

        var READ_definitions = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_definitions);
        var READ_a_definition = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_a_definition);
        var READ_localizations = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_localizations);
        var READ_a_localization = JsonUtil.toObjectFromFile(DefinitionsResponse.class, ConsentType.DEFINITIONS_READ_a_localization);

        READ_definitions_response =  ObjectDtoMapper.dtoMapper(READ_definitions,DefinitionsResponseDto.class);
        READ_a_definition_response =  ObjectDtoMapper.dtoMapper(READ_a_definition,DefinitionsResponseDto.class);
        READ_localizations_response =  ObjectDtoMapper.dtoMapper(READ_localizations,DefinitionsResponseDto.class);
        READ_a_localization_response =  ObjectDtoMapper.dtoMapper(READ_a_localization,DefinitionsResponseDto.class);

        tokenResponse = TokenResponse.builder()
                .token("Az90SAOJklae")
                .tokenType("Bearer")
                .build();

        tokenRequest = TokenRequest.builder()
                .clientId("clientId")
                .clientSecret("secret")
                .build();

        customExceptionToken = new CustomException("451","Failed to fetch Token");

        customExceptionUserInfo = new CustomException("451","Failed to fetch user information");

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
