package com.masdefect.terminal;

import com.masdefect.controller.*;
import com.masdefect.io.ConsoleIOImpl;
import com.masdefect.io.FIleIOImpl;
import com.masdefect.io.interfaces.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.masdefect.config.Config.*;

@Component
public class Terminal implements CommandLineRunner {

    private SolarSystemController solarSystemController;
    private ConsoleIOImpl console;
    private FileIO fileIO;
    private StarsController starsController;
    private PlanetsController planetsController;
    private PersonsController peopleController;
    private AnomalyController anomaliesController;
    private AnomalyVictimsController anomalyVictimsController;

    @Autowired
    public Terminal(SolarSystemController solarSystemController, ConsoleIOImpl console, FIleIOImpl fileIO, StarsController starsController, PlanetsController planetsController, PersonsController peopleContoller, AnomalyController anomaliesController, AnomalyVictimsController anomalyVictimsController) {
        this.solarSystemController = solarSystemController;
        this.console = console;
        this.fileIO = fileIO;
        this.starsController = starsController;
        this.planetsController = planetsController;
        this.peopleController = peopleContoller;
        this.anomaliesController = anomaliesController;
        this.anomalyVictimsController = anomalyVictimsController;
    }

    @Override
    public void run(String... args) throws Exception {
        String fileContents = fileIO.read(SOLAR_SYSTEM_IMPORT_JSON);
        String output = this.solarSystemController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(STARS_IMPORT_JSON);
        output = this.starsController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(PLANETS_IMPORT_JSON);
        output = this.planetsController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(PERSONS_IMPORT_JSON);
        output = this.peopleController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(ANOMALY_IMPORT_JSON);
        output = this.anomaliesController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(ANOMALY_VICTIMS_IMPORT_JSON);
        output = this.anomalyVictimsController.importDataFromJSON(fileContents);
        console.write(output);

        fileContents = fileIO.read(ANOMALIES_IMPORT_XML);
        output = this.anomaliesController.importDataFromXML(fileContents);
        console.write(output);

        console.write(planetsController.planetsWithNoPeopleTeleportedToThem());

        console.write(anomaliesController.findAnomalyWithMostVictims());
    }
}
