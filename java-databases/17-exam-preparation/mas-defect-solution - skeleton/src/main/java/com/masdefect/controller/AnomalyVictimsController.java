package com.masdefect.controller;

import com.masdefect.domain.dto.json.AnomalyVictimsJSONDto;
import com.masdefect.parser.JSONParser;
import com.masdefect.service.AnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AnomalyVictimsController {

    private JSONParser jsonParser;
    private AnomalyService anomalyService;

    @Autowired
    public AnomalyVictimsController(JSONParser jsonParser, AnomalyService anomalyService) {
        this.jsonParser = jsonParser;
        this.anomalyService = anomalyService;
    }

    public String importDataFromJSON(String fileContents){
        AnomalyVictimsJSONDto[] anomalyVictimsJSONDtos;
        try {
            anomalyVictimsJSONDtos = this.jsonParser.read(AnomalyVictimsJSONDto[].class, fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (AnomalyVictimsJSONDto dto : anomalyVictimsJSONDtos) {
            try {
                this.anomalyService.create(dto);
            }
            catch (Exception ignored) {
            }
        }

        return "";
    }
}
