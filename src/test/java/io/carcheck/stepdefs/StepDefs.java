package io.carcheck.stepdefs;

import io.carcheck.filehandler.FileParser;
import io.carcheck.modal.Car;
import io.carcheck.pages.CarDetailsPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepDefs implements En {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    CarDetailsPage carDetailsPage;

    List<String> inputFiles, outputFiles;
    List<String> allRegNos = new ArrayList<>();
    List<Car> cars = new ArrayList<>();

    public StepDefs() {

        Given("^User read the input files$", (DataTable files) -> {
            inputFiles = files.asList();
        });

        When("^User extracted vehicle registration numbers based on patterns$", () -> {
            inputFiles.forEach(filePath -> {
                FileParser fileParser = beanFactory.getBean(FileParser.class, filePath);
                List<String> regNos = fileParser.getRegNos();
                if (!regNos.isEmpty()) {
                    allRegNos.addAll(regNos);
                }
            });
        });
        Then("^User compares results of registration numbers from (.*) with output files$", (String url, DataTable files) -> {
            outputFiles = files.asList();
            outputFiles.forEach(filePath -> {
                FileParser fileParser = beanFactory.getBean(FileParser.class, filePath);
                List<String> vehicleDetails = fileParser.getLines();
                if (!vehicleDetails.isEmpty()) {
                    vehicleDetails.remove(0);
                    vehicleDetails.forEach(vehicle -> {
                        List<String> details = Arrays.asList(vehicle.split(","));
                        Car car = new Car(details.get(0), details.get(1), details.get(2),
                                details.get(3), details.get(4));
                        cars.add(car);
                    });
                }
            });

            SoftAssert softAssert = new SoftAssert();
            allRegNos.forEach(regNo -> {
                String registrationNo = regNo.replaceAll("\\s", "");
                carDetailsPage.openURL(url + "free-car-check/?vrm=" + registrationNo);
                Car actualCarDetails = carDetailsPage.getCarDetails();
                Car expectedCarDetails = cars.stream().filter(car -> car.getRegNo().equals(registrationNo)).findFirst().orElse(null);
                if (actualCarDetails == null) {
                    softAssert.assertTrue(true, "Car registration no " + registrationNo + " not found at " + url);
                } else if (expectedCarDetails == null) {
                    softAssert.assertTrue(false, "Car registration no " + registrationNo + " not found in output file");
                } else {
                    //Assert Make
                    softAssert.assertEquals(actualCarDetails.getMake(), expectedCarDetails.getMake(), "Make didn't matched for the car number " + registrationNo);
                    //Assert Model
                    softAssert.assertEquals(actualCarDetails.getModel(), expectedCarDetails.getModel(), "Model didn't matched for the car number " + registrationNo);
                    //Assert Color
                    softAssert.assertEquals(actualCarDetails.getColor(), expectedCarDetails.getColor(), "Color didn't matched for the car number " + registrationNo);
                    //Assert Year
                    softAssert.assertEquals(actualCarDetails.getYear(), expectedCarDetails.getYear(), "Year didn't matched for the car number " + registrationNo);
                }
            });
            softAssert.assertAll();
        });
    }
}