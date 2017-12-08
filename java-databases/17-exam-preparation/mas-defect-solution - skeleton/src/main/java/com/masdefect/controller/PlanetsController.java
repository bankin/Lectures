package com.masdefect.controller;

import com.masdefect.domain.dto.json.PlanetExportJSONDto;
import com.masdefect.domain.dto.json.PlanetImportJSONDto;
import com.masdefect.domain.entities.Planet;
import com.masdefect.parser.JSONParser;
import com.masdefect.parser.ModelParserImpl;
import com.masdefect.repository.PlanetRepository;
import com.masdefect.repository.SolarSystemRepository;
import com.masdefect.repository.StarRepository;
import com.masdefect.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

import static com.masdefect.config.Config.DEFAULT_ERROR_MSG;
import static com.masdefect.config.Config.DEFAULT_SUCCESS_MSG;

@Controller
public class PlanetsController {

    private JSONParser jsonParser;
    private PlanetService planetService;

    @Autowired
    public PlanetsController(JSONParser jsonParser,PlanetService planetService) {
        this.jsonParser = jsonParser;
        this.planetService = planetService;
    }

    public String importDataFromJSON(String fileContents){
        PlanetImportJSONDto[] planetImportJSONDtos;
        try {
            planetImportJSONDtos = this.jsonParser.read(PlanetImportJSONDto[].class, fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (PlanetImportJSONDto dto : planetImportJSONDtos) {
            String output = String.format(DEFAULT_SUCCESS_MSG, "Planet", dto.getName());
            try {
                this.planetService.create(dto);
            }
            catch (Exception e) {
                output = DEFAULT_ERROR_MSG;
            }

            builder.append(output + '\n');
        }

        return builder.toString();

    }

    public String planetsWithNoPeopleTeleportedToThem(){
        List<PlanetExportJSONDto> planets = this.planetService.findAllPlanetsWithoutPeopleTeleportedFromThem();
        String json = null;

        try {
            json = jsonParser.write(planets, "planets.json");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}
