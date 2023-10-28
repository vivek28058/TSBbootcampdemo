package coll.app.boiler.util;

import org.springframework.stereotype.Component;

@Component
public class RemoveValueZero {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            int value = (Integer) obj;
            return value != 0;
        }
        return true;
    }
}
