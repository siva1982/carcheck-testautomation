package io.carcheck;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"classpath:features/carcheck.feature"},
        glue = {"io.carcheck.stepdefs"},
        plugin = {"io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"})
public class TestRunner extends AbstractTestNGCucumberTests {

}
