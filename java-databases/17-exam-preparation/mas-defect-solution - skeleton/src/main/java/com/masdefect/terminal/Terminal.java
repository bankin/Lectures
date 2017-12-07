package com.masdefect.terminal;

import com.masdefect.controller.SolarSystemController;
import com.masdefect.io.ConsoleIOImpl;
import com.masdefect.io.FIleIOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.masdefect.config.Config.SOLAR_SYSTEM_IMPORT_JSON_LOCAL;

@Component
public class Terminal implements CommandLineRunner {

    private SolarSystemController solarSystemController;
    private ConsoleIOImpl console;
    private FIleIOImpl fileIO;

    @Autowired
    public Terminal(SolarSystemController solarSystemController, ConsoleIOImpl console, FIleIOImpl fileIO) {
        this.solarSystemController = solarSystemController;
        this.console = console;
        this.fileIO = fileIO;
    }

    @Override
    public void run(String... args) throws Exception {
//        String output = fileIO.read(SOLAR_SYSTEM_IMPORT_JSON_LOCAL);

        String output = this.solarSystemController
                .importDataFromJSON(SOLAR_SYSTEM_IMPORT_JSON_LOCAL);
//
        console.write(output);
    }
}
