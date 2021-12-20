package com.razarac.enemycrud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSONMapper {
    private final ObjectMapper objectMapper;

    public JSONMapper() {
        objectMapper = new ObjectMapper();
    }

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.debug("Serialize failed for " + obj.toString() + " with message " + e.getMessage());

            e.printStackTrace();
            return "";
        } catch (NullPointerException e){
            log.debug("Serialize failed for null object with message " + e.getMessage());
            return "";
        }
    }

}
