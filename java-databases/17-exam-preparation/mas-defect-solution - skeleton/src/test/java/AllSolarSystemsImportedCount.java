//solar systems count
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.masdefect.MainApplication;
import com.masdefect.config.Config;
import com.masdefect.controller.SolarSystemController;
import com.masdefect.io.interfaces.FileIO;
import com.masdefect.repository.SolarSystemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.persistence.EntityManager;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class AllSolarSystemsImportedCount {
    @Autowired
    private SolarSystemRepository solarSystemRepository;

    private static final int EXPECTED_SOLAR_SYSTEMS_COUNT = 5;

    private static final String EXPECTED_INSERTION_STRING = "Successfully imported Solar System Kepler-Epsilon.\n" +
            "Successfully imported Solar System Alpha-Nebula.\n" +
            "Successfully imported Solar System Beta-Cluster.\n" +
            "Successfully imported Solar System Voyager-Sentry.\n" +
            "Successfully imported Solar System Zeta-Cluster.\n";

    @Test
    public void testAllSolarSystemsImportedCount() throws IOException {
        long count = this.solarSystemRepository.findAll().spliterator().getExactSizeIfKnown();
        Assert.assertEquals(EXPECTED_SOLAR_SYSTEMS_COUNT, count);
    }
}