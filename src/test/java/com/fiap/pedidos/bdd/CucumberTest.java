package com.fiap.pedidos.bdd;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
        @ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.fiap.pedidos.bdd")

})
public class CucumberTest {

}
