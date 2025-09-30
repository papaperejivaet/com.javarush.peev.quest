package com.quest.controller.request_utils;

import java.util.function.Function;

public class RequestParser
{
        public <T> T parse(String value, Function<String, T> parser) {
            try {
                return parser.apply(value);
            } catch (Exception e) {
                return null;
            }
        }
}
