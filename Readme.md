# Demo Framework

A Java-based test automation framework built with **Java 21**,
**Maven**, **Selenium WebDriver**, and **TestNG**.

## Project Structure

``` text
demo-framework/
├── .idea/                         # IntelliJ IDEA project settings
├── .mvn/                          # Maven Wrapper support files
├── config/
│   └── config.properties          # Framework configuration
├── logs/                           # Execution logs
├── reports/                        # Test reports
├── screenshots/                    # Captured screenshots
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base_page/          # Shared/base page functionality
│   │   │   ├── constants/           # Project constants (folder name as currently used)
│   │   │   ├── org/example/        # Page classes, e.g. CheckBoxPage and TextBoxPage
│   │   │   └── utils/              # Reusable utilities
│   │   └── resources/              # Main resources
│   └── test/                       # Test classes and test resources
├── target/                         # Maven build output (generated)
├── test_suites/
│   └── TextBoxPageTest.xml         # TestNG suite file
├── pom.xml                         # Maven dependencies and build configuration
└── README.md
```


## Prerequisites

Install and configure the following:

-   **JDK 21** --- ensure `java` and `javac` are available from the
    command line.
-   **Apache Maven** --- or use the Maven Wrapper (`mvnw` / `mvnw.cmd`)
    if it is included and configured in the project.
-   **IntelliJ IDEA** or another Java IDE (optional).
-   A supported browser for the tests. Selenium Manager can manage
    browser drivers for many standard Selenium setups, depending on your
    Selenium version and environment.

Verify Java and Maven from Command Prompt or PowerShell:

``` bat
java -version
mvn -version
```

The Java version should report 21. Maven should use the intended JDK 21
installation.

## Configuration

Review `config/config.properties` before running tests. Set the
application URL, browser, credentials, timeouts, or other values
required by your framework, according to the property names your code
actually reads.

Do not commit real passwords, access tokens, or other secrets to source
control.

## Run the TestNG Suite from IntelliJ IDEA

1.  Open the project in IntelliJ IDEA and wait for Maven dependencies to
    finish importing.
2.  In the Project panel, expand `test_suites`.
3.  Right-click `TextBoxPageTest.xml`.
4.  Select **Run '...TextBoxPageTest.xml'**.

If the **Run** option is unavailable, check that TestNG is present as a
project dependency and that the XML is a valid TestNG suite file.

## Run Tests from the Command Line

Open Command Prompt or PowerShell in the project root --- the directory
containing `pom.xml`.

### Option 1: Run the configured Maven test phase

``` bat
mvn clean test
```

This compiles the project and runs tests selected by the Maven Surefire
configuration in `pom.xml`. If the POM already specifies
`TextBoxPageTest.xml`, this command will run that suite.

### Option 2: Explicitly select the TestNG suite XML

``` bat
mvn clean test "-Dsurefire.suiteXmlFiles=test_suites/TextBoxPageTest.xml"
```

This asks Maven Surefire to use the specified TestNG suite. It requires
the project to have TestNG and a compatible Maven Surefire Plugin
configured in `pom.xml`. If the POM hard-codes a different suite XML in
the plugin configuration, update the POM or use a configuration that
allows the system property to override it.

## Common Maven Commands

  -----------------------------------------------------------------------
Command                             Purpose
  ----------------------------------- -----------------------------------
`mvn clean`                         Removes generated build output,
usually `target/`.

`mvn test`                          Compiles and runs tests selected by
the Maven test configuration.

`mvn clean test`                    Cleans the previous build, then
runs tests.

`mvn -DskipTests package`           Builds/packages the project while
skipping test execution.
  -----------------------------------------------------------------------

## Where to Find Results

-   **Test reports:** usually under `target/surefire-reports/` for Maven
    Surefire / TestNG output.
-   **Framework reports:** `reports/`, if your framework writes reports
    there.
-   **Logs:** `logs/`, if logging is configured to write files there.
-   **Screenshots:** `screenshots/`, if the framework saves screenshots
    there.

The exact output location depends on the logging and reporting
configuration in your code.

## Troubleshooting

-   **`mvn` is not recognized:** install Maven or use `mvnw.cmd` if
    available, and verify `PATH`.
-   **Wrong Java version:** check `java -version` and `mvn -version`;
    Maven may be using a different JDK from IntelliJ.
-   **No tests run:** confirm that the TestNG suite path is correct and
    that the Maven Surefire Plugin is configured to discover or run the
    intended tests.
-   **Browser/driver startup fails:** check the installed browser,
    Selenium version, network access if Selenium Manager needs to
    download a driver, and any framework-specific driver configuration.
-   **Configuration values are missing:** verify
    `config/config.properties` and confirm the code loads it from the
    expected path.

## Technology Stack

-   Java 21
-   Maven
-   Selenium WebDriver
-   TestNG
-   IntelliJ IDEA (development environment)

------------------------------------------------------------------------

**Note:** The commands above assume `pom.xml` includes the necessary
Selenium, TestNG, and Maven Surefire dependencies/plugins. If a command
does not select the expected suite, inspect the Surefire configuration
in `pom.xml`.
