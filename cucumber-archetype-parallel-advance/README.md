
# Project Name

## Running Tests

To run tests with specific tags, use the following Maven commands.

### Run Regression Tests

```sh
mvn test -Dcucumber.filter.tags="@Regression"
```

### Run Tests with Environment Variables

To run tests with environment variables, use the following commands. Make sure to set the necessary environment variables before executing the tests.

#### Run Login Tests

```sh
ENVIRONMENTS=QA PASSWORD=Carolina123 PASSWORD_SUPERADMIN=Carolina123 PLATFORM_GOAL_EXECUTION_TEST=LOCAL mvn test -Dcucumber.filter.tags="@Logins"
```

#### Run Login Tests Excluding Specific Tag

```sh
ENVIRONMENTS=QA PASSWORD=Carolina123. PASSWORD_SUPERADMIN=Carolina123. PLATFORM_GOAL_EXECUTION_TEST=LOCAL mvn test -Dcucumber.filter.tags="@Logins and not @RunThisTag"
```

## Generating Allure Reports

After running the tests, you can generate and serve the Allure reports using the following Maven commands.

### Generate Allure Report

```sh
mvn allure:report
```

### Serve Allure Report

```sh
mvn allure:serve
```

## Configuration Files

### `junit-platform.properties`

The `junit-platform.properties` file should contain the following configuration:

```properties
cucumber.plugin=pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm
cucumber.execution.parallel.enabled=false
cucumber.filter.tags=@Logins
```

### `allure.properties`

The `allure.properties` file can be used to configure Allure-specific properties if needed. Here is an example configuration:

```properties
# Example Allure properties configuration
allure.results.directory=target/allure-results
allure.link.issue.pattern=https://my.issue.tracker/issues/%s
allure.link.tms.pattern=https://my.tms.tracker/tests/%s
```

## Parallel Execution Configuration

To disable parallel execution, you can use the following configuration parameter in your test runner or configuration file:

```java
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "false")
```

Ensure that this is set appropriately in your test runner class or in the properties file.

## Environment Variables

The following environment variables are used in the commands:

- `ENVIRONMENTS`: Specifies the environment (e.g., QA, DEV, PROD).
- `PASSWORD`: The password for the user.
- `PASSWORD_SUPERADMIN`: The password for the super admin.
- `PLATFORM_GOAL_EXECUTION_TEST`: The platform goal execution test (e.g., LOCAL, REMOTE).

Make sure to replace the placeholder values with the actual values as per your environment.

## Example Usage

Here is an example of how you would use the commands:

1. Run regression tests:
   ```sh
   mvn test -Dcucumber.filter.tags="@Regression"
   ```

2. Generate an Allure report: this going to generate a report with the name `index.html` in the `target/site/` directory.
   ```sh
   mvn allure:report
   ```

3. Serve an Allure report: this will open the report in the browser. and Press <Ctrl+C> to exit the server.
   ```sh
   mvn allure:serve
   ```

4. Run login tests with environment variables:
   ```sh
   ENVIRONMENTS=QA PASSWORD=Carolina123 PASSWORD_SUPERADMIN=Carolina123 PLATFORM_GOAL_EXECUTION_TEST=LOCAL mvn test -Dcucumber.filter.tags="@Logins"
   ```

5. Run login tests excluding specific tag:
   ```sh
   ENVIRONMENTS=QA PASSWORD=Carolina123 PASSWORD_SUPERADMIN=Carolina123 PLATFORM_GOAL_EXECUTION_TEST=LOCAL mvn test -Dcucumber.filter.tags="@Logins and not @RunThisTag"
   ```

By following the steps and commands outlined in this `README.md`, you can effectively run your tests and generate detailed reports with Allure.
