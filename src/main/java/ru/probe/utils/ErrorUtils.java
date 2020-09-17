package ru.probe.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ErrorUtils {
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collect = Collectors.toMap(
                fieldError -> fieldError.getField() + "ERROR",
                FieldError::getDefaultMessage
        );

        return bindingResult.getFieldErrors().stream().collect(collect);
    }
}
