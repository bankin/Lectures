package com.masdefect.service.impls;

import com.masdefect.domain.dto.json.PlanetExportJSONDto;
import com.masdefect.domain.dto.json.PlanetImportJSONDto;
import com.masdefect.domain.entities.Planet;
import com.masdefect.domain.entities.SolarSystem;
import com.masdefect.domain.entities.Star;
import com.masdefect.parser.interfaces.ModelParser;
import com.masdefect.repository.PlanetRepository;
import com.masdefect.repository.SolarSystemRepository;
import com.masdefect.repository.StarRepository;
import com.masdefect.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {

    private StarRepository starRepository;
    private SolarSystemRepository solarSystemRepository;
    private PlanetRepository planetRepository;
    private ModelParser modelMapper;

    @Autowired
    public PlanetServiceImpl(StarRepository starRepository, SolarSystemRepository solarSystemRepository1, PlanetRepository planetRepository, ModelParser modelMapper) {
        this.starRepository = starRepository;
        this.solarSystemRepository = solarSystemRepository1;
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(PlanetImportJSONDto planetImportJSONDto) {
        SolarSystem system = this.solarSystemRepository.findByName(planetImportJSONDto.getSolarSystem());
        Star sun = this.starRepository.findByName(planetImportJSONDto.getSun());

        Planet planet = new Planet();
        planet.setName(planetImportJSONDto.getName());
        planet.setSolarSystem(system);
        planet.setSun(sun);

        this.planetRepository.save(planet);
    }

    @Override
    public List<PlanetExportJSONDto> findAllPlanetsWithoutPeopleTeleportedFromThem() {
        List<Planet> planets = this.planetRepository.findAllByAnomaliesOriginIsNull();

        PlanetExportJSONDto[] convert = this.modelMapper.convert(planets, PlanetExportJSONDto[].class);

        return Arrays.asList(convert);
    }
}
