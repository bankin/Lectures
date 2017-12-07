package com.masdefect.controller;

import com.masdefect.domain.dto.json.SolarSystemImportJSONDto;
import com.masdefect.domain.entities.SolarSystem;
import com.masdefect.io.FIleIOImpl;
import com.masdefect.parser.JSONParser;
import com.masdefect.parser.ModelParserImpl;
import com.masdefect.repository.SolarSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.masdefect.config.Config.DEFAULT_ERROR_MSG;
import static com.masdefect.config.Config.DEFAULT_SUCCESS_MSG;

@Controller
public class SolarSystemController {

    private FIleIOImpl fileUtil;
    private JSONParser jsonParser;
    private ModelParserImpl modelParser;
    private SolarSystemRepository solarSystemRepository;

    @Autowired
    public SolarSystemController(FIleIOImpl fileUtil, JSONParser jsonParser, ModelParserImpl modelParser, SolarSystemRepository solarSystemRepository) {
        this.fileUtil = fileUtil;
        this.jsonParser = jsonParser;
        this.modelParser = modelParser;
        this.solarSystemRepository = solarSystemRepository;
    }

    public String importDataFromJSON(String filePath) throws IOException, JAXBException {
        String jsonData = this.fileUtil.read(filePath);

        SolarSystemImportJSONDto[] solarSystemDtos =
                this.jsonParser.read(SolarSystemImportJSONDto[].class, jsonData);

        List<SolarSystem> solarSystems = Stream.of(solarSystemDtos)
                .map(dto -> this.modelParser.convert(dto, SolarSystem.class))
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (SolarSystem s : solarSystems) {
            String output = DEFAULT_ERROR_MSG;
            // if is s valid
                this.solarSystemRepository.save(s);
                output = String.format(DEFAULT_SUCCESS_MSG, "Solar System", s.getName());

            builder.append(output + '\n');
        }

        return builder.toString();
    }
}
