package coll.app.boiler.dto.mapper;

import coll.app.boiler.exception.CustomException;
import coll.app.boiler.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ObjectDtoMapper {

    public static <T> T dtoMapper(Object object,Class<T> type){
        String json = JsonUtil.toJson(object);

        try {
            return JsonUtil.toObject(json,type);
        } catch (Throwable throwable) {
            log.info("Error in ObjectDtoMapper class in method dtoMapper: {}",throwable.getMessage());
            throw new CustomException("451","Something went wrong");
        }

    }
}
