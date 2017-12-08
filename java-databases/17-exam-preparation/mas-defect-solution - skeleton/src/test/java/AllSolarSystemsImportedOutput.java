//solar systems output
import com.masdefect.MainApplication;
import com.masdefect.config.Config;
import com.masdefect.controller.SolarSystemController;
import com.masdefect.io.interfaces.FileIO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class AllSolarSystemsImportedOutput {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SolarSystemController solarSystemController;

    @Autowired
    private FileIO fileParser;

    private static final String EXPECTED_INSERTION_STRING = "Successfully imported Solar System Kepler-Epsilon.\n" +
            "Successfully imported Solar System Alpha-Nebula.\n" +
            "Successfully imported Solar System Beta-Cluster.\n" +
            "Successfully imported Solar System Voyager-Sentry.\n" +
            "Successfully imported Solar System Zeta-Cluster.\n";

    @Test
    public void testAllSolarSystemsImported() throws IOException {
        String output = this.solarSystemController.importDataFromJSON(this.fileParser.read(Config.SOLAR_SYSTEM_IMPORT_JSON));
        output = output.replaceAll("\\s+", "#");
        Assert.assertEquals("Insertion output is not the same!",
                output, EXPECTED_INSERTION_STRING.replaceAll("\\s+", "#"));
    }
}