package com.example.demo.common.R;

import com.example.demo.entity.Message;
import com.example.demo.entity.ResultMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class MessageUtils {


    public static String getMessage(String fromName, String message) {

        try {
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setMessage(message);
            if (fromName != null) {
                resultMessage.setFromName(fromName);
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
