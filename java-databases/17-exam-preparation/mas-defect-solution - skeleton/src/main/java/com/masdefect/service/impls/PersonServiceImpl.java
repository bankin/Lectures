package com.masdefect.service.impls;

import com.masdefect.domain.dto.json.PersonExportJSONDto;
import com.masdefect.domain.dto.json.PersonImportJSONDto;
import com.masdefect.domain.entities.Person;
import com.masdefect.domain.entities.Planet;
import com.masdefect.repository.PersonRepository;
import com.masdefect.repository.PlanetRepository;
import com.masdefect.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private PlanetRepository planetRepository;
    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PlanetRepository planetRepository, PersonRepository personRepository) {
        this.planetRepository = planetRepository;
        this.personRepository = personRepository;
    }

    @Override
    public void create(PersonImportJSONDto personImportJSONDto) {
        Planet planet = this.planetRepository.findByName(personImportJSONDto.getHomePlanet());

        Person person = new Person();
        person.setName(personImportJSONDto.getName());
        person.setHomePlanet(planet);

        this.personRepository.save(person);
    }

    @Override
    public List<PersonExportJSONDto> findInnocentPersons() {
        return null;
    }
}
