package com.masdefect.service.impls;

import com.masdefect.domain.dto.json.AnomalyExportJSONDto;
import com.masdefect.domain.dto.json.AnomalyImportJSONDto;
import com.masdefect.domain.dto.json.AnomalyVictimsJSONDto;
import com.masdefect.domain.dto.json.PlanetExportJSONDto;
import com.masdefect.domain.dto.xml.AnomaliesXMLDto;
import com.masdefect.domain.dto.xml.AnomalyXMLDto;
import com.masdefect.domain.entities.Anomaly;
import com.masdefect.domain.entities.Person;
import com.masdefect.domain.entities.Planet;
import com.masdefect.parser.interfaces.ModelParser;
import com.masdefect.repository.AnomalyRepository;
import com.masdefect.repository.PersonRepository;
import com.masdefect.repository.PlanetRepository;
import com.masdefect.service.AnomalyService;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnomalyServiceImpl implements AnomalyService {
    private PlanetRepository planetRepository;
    private AnomalyRepository anomalyRepository;
    private PersonRepository personRepository;
    private ModelParser modelParser;

    @Autowired
    public AnomalyServiceImpl(PlanetRepository planetRepository, AnomalyRepository anomalyRepository, PersonRepository personRepository, ModelParser modelParser) {
        this.planetRepository = planetRepository;
        this.anomalyRepository = anomalyRepository;
        this.personRepository = personRepository;
        this.modelParser = modelParser;
    }

    @Override
    public void create(AnomalyImportJSONDto anomalyImpotJSONDto) {
        Planet origin = this.planetRepository.findByName(anomalyImpotJSONDto.getOriginPlanet());
        Planet teleport = this.planetRepository.findByName(anomalyImpotJSONDto.getTeleportPlanet());

        Anomaly anomaly = new Anomaly();
        anomaly.setOriginPlanet(origin);
        anomaly.setTeleportPlanet(teleport);

        this.anomalyRepository.save(anomaly);
    }

    @Override
    @Transactional
    public void create(AnomalyVictimsJSONDto anomalyVictimsDto) {
        Anomaly one = this.anomalyRepository.findOne(anomalyVictimsDto.getId());
        Person person = this.personRepository.findByName(anomalyVictimsDto.getPerson());

        Set<Person> victims = one.getVictims();
        victims.add(person);
        one.setVictims(victims);

        this.anomalyRepository.save(one);
    }

    @Override
    public void create(AnomalyXMLDto anomalyImportXMLDto) {
        Planet origin = this.planetRepository.findByName(anomalyImportXMLDto.getOriginPlanet());
        Planet teleport = this.planetRepository.findByName(anomalyImportXMLDto.getTeleportPlanet());

        Anomaly anomaly = new Anomaly();
        anomaly.setOriginPlanet(origin);
        anomaly.setTeleportPlanet(teleport);

        HashSet<Person> victims = new HashSet<>();

        List<Person> people = anomalyImportXMLDto.getVictimXMLDtos()
                .stream()
                .map(v -> this.personRepository.findByName(v.getName()))
                .collect(Collectors.toList());

        victims.addAll(people);
        anomaly.setVictims(victims);

        this.anomalyRepository.save(anomaly);
    }

    @Override
    @Transactional
    public AnomalyExportJSONDto findMostAffectingAnomalies() {
        List<Anomaly> mostAffecting = this.anomalyRepository.findMostAffecting(new PageRequest(0, 1));

        PropertyMap<Anomaly, AnomalyExportJSONDto> map = new PropertyMap<Anomaly, AnomalyExportJSONDto>() {
            @Override
            protected void configure() {
                map().setVictimsCount((long) source.getVictims().size());
            }
        };

        AnomalyExportJSONDto convert =
                this.modelParser.convert(mostAffecting.get(0), AnomalyExportJSONDto.class, map);

        return convert;
    }

    @Override
    public AnomaliesXMLDto finaAllAnomalies() {
        return null;
    }
}
