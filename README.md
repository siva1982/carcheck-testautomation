# Car check test automation

### Pre-requisites
1. Java 8 and above
2. Maven 3
3. Chrome browser

### Features
1. Cucumber BDD so that we can provide as many input & output files
2. Spring dependency injection
3. Allure reporter to show the exact failures with screenshots.
4. Automatically downloads the Chrome browser driver
5. Page factory model
6. Constructed REGEX that satisfies all UK vehicle registration formats
7. Handled failures in case vehicle not found in https://cartaxcheck.co.uk/ or in output files.

### Usage
1. Clone or download the project
2. Execute the command `mvn clean test allure:report` in the project root directory


> Below is the report how it looks like

![Allure report found in](/carcheck-testautomation/target/site/allure-maven-plugin/index.html