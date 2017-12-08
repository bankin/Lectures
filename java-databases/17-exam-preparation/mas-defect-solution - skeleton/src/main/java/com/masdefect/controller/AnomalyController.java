package com.masdefect.controller;

import com.masdefect.domain.dto.json.AnomalyExportJSONDto;
import com.masdefect.domain.dto.json.AnomalyImportJSONDto;
import com.masdefect.domain.dto.xml.AnomaliesXMLDto;
import com.masdefect.domain.dto.xml.AnomalyXMLDto;
import com.masdefect.parser.JSONParser;
import com.masdefect.parser.XMLParser;
import com.masdefect.service.AnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static com.masdefect.config.Config.ANOMALIES_SUCCESS_MSG;
import static com.masdefect.config.Config.DEFAULT_ERROR_MSG;

@Controller
public class AnomalyController {

    private JSONParser jsonParser;
    private AnomalyService anomalyService;
    private XMLParser xmlParser;

    @Autowired
    public AnomalyController(JSONParser jsonParser, AnomalyService anomalyService, XMLParser xmlParser) {
        this.jsonParser = jsonParser;
        this.anomalyService = anomalyService;
        this.xmlParser = xmlParser;
    }

    public String importDataFromJSON(String fileContents) {
        AnomalyImportJSONDto[] anomalyImportJSONDtos;
        try {
            anomalyImportJSONDtos = this.jsonParser.read(AnomalyImportJSONDto[].class, fileContents);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (AnomalyImportJSONDto dto : anomalyImportJSONDtos) {
            String output = ANOMALIES_SUCCESS_MSG;
            try {
                this.anomalyService.create(dto);
            }
            catch (Exception e) {
                output = DEFAULT_ERROR_MSG;
            }

            builder.append(output + '\n');
        }

        return builder.toString();
    }

    public String importDataFromXML(String fileContents) {
        AnomaliesXMLDto anomalyXMLDtos;
        try {
            anomalyXMLDtos = this.xmlParser.read(AnomaliesXMLDto.class, fileContents);
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
            return null;
        }

        for (AnomalyXMLDto dto : anomalyXMLDtos.getAnomalyXMLDtos()) {
            try {
                this.anomalyService.create(dto);
            }
            catch (Exception ignored) {
            }
        }

        return "";
    }

    public String findAnomalyWithMostVictims() {
        AnomalyExportJSONDto mostAffectingAnomalies = this.anomalyService.findMostAffectingAnomalies();

        String json = null;
        try {
            json = this.jsonParser.write(mostAffectingAnomalies, "not-used");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

    public String exportAnomaliesOrdered() {
        return null;
    }
}
