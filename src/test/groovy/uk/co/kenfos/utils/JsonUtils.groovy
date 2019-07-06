package uk.co.kenfos.utils

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtils {
    private static final objectMapper = new ObjectMapper()

    static JsonParser jsonParser(String json) {
        return new JsonFactory(objectMapper).createParser(json)
    }
}
