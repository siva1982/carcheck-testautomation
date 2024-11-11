package io.carcheck.stepdefs;

import io.carcheck.config.Config;
import io.cucumber.java8.En;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = Config.class)
@CucumberContextConfiguration
public class SpringConfiguration implements En {


}

