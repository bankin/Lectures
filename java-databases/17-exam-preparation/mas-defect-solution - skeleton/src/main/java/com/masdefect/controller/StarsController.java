package com.masdefect.controller;

import com.masdefect.domain.dto.json.StarImportJSONDto;
import com.masdefect.parser.JSONParser;
import com.masdefect.parser.ModelParserImpl;
import com.masdefect.repository.SolarSystemRepository;
import com.masdefect.repository.StarRepository;
import com.masdefect.service.StarService;
import com.masdefect.service.impls.StarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.masdefect.config.Config.DEFAULT_ERROR_MSG;
import static com.masdefect.config.Config.DEFAULT_SUCCESS_MSG;

@Controller
public class StarsController {

    private JSONParser jsonParser;
    private StarServiceImpl starService;

    @Autowired
    public StarsController(JSONParser jsonParser, StarServiceImpl starService) {
        this.jsonParser = jsonParser;
        this.starService = starService;
    }

    public String importDataFromJSON(String fileContents){
        StarImportJSONDto[] starDtos;
        try {
            starDtos = this.jsonParser.read(StarImportJSONDto[].class, fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (StarImportJSONDto dto : starDtos) {
            String output = String.format(DEFAULT_SUCCESS_MSG, "Star", dto.getName());
            try {
                this.starService.create(dto);
            }
            catch (Exception e) {
                output = DEFAULT_ERROR_MSG;
            }

            builder.append(output + '\n');
        }

        return builder.toString();
    }
}
