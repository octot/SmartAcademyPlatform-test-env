package com.authentication.Authenitication.Utiity;

import java.util.function.Consumer;

public class UtilityFunctions {

    public static <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
