package com.masdefect.service.impls;

import com.masdefect.domain.dto.json.StarImportJSONDto;
import com.masdefect.domain.entities.SolarSystem;
import com.masdefect.domain.entities.Star;
import com.masdefect.repository.SolarSystemRepository;
import com.masdefect.repository.StarRepository;
import com.masdefect.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarServiceImpl implements StarService {

    private SolarSystemRepository solarSystemRepository;
    private StarRepository starRepository;

    @Autowired
    public StarServiceImpl(SolarSystemRepository solarSystemRepository, StarRepository starRepository) {
        this.solarSystemRepository = solarSystemRepository;
        this.starRepository = starRepository;
    }

    @Override
    public void create(StarImportJSONDto starImportJSONDto) {
        if (starImportJSONDto.getName() == null || starImportJSONDto.getSolarSystem() == null) {
            throw new RuntimeException();
        }

        SolarSystem system = this.solarSystemRepository.findByName(starImportJSONDto.getSolarSystem());

        Star star = new Star();
        star.setName(starImportJSONDto.getName());
        star.setSolarSystem(system);

        this.starRepository.save(star);
    }
}
