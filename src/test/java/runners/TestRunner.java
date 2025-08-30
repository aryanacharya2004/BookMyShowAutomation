package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"features"}, // List feature files in desired order
        glue = {"stepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:reports/cucumber.html",
                "json:reports/cucumber.json"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
