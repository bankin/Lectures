package com.masdefect.controller;

import com.masdefect.domain.dto.json.PersonImportJSONDto;
import com.masdefect.domain.dto.json.PlanetImportJSONDto;
import com.masdefect.parser.JSONParser;
import com.masdefect.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

import static com.masdefect.config.Config.DEFAULT_ERROR_MSG;
import static com.masdefect.config.Config.DEFAULT_SUCCESS_MSG;

@Controller
public class PersonsController {

    private JSONParser jsonParser;
    private PersonService peopleService;

    @Autowired
    public PersonsController(JSONParser jsonParser, PersonService peopleService) {
        this.jsonParser = jsonParser;
        this.peopleService = peopleService;
    }

    public String importDataFromJSON(String fileContents){
        PersonImportJSONDto[] personImportJSONDtos;
        try {
            personImportJSONDtos = this.jsonParser.read(PersonImportJSONDto[].class, fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (PersonImportJSONDto dto : personImportJSONDtos) {
            String output = String.format(DEFAULT_SUCCESS_MSG, "Person", dto.getName());
            try {
                this.peopleService.create(dto);
            }
            catch (Exception e) {
                output = DEFAULT_ERROR_MSG;
            }

            builder.append(output + '\n');
        }

        return builder.toString();
    }
}
