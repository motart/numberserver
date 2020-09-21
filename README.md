# New Relic Coding Assessment
## Number Server 
### Assumptions:

- Main method as provided throws an exception, my assumption is that a SocketException (extends IOException) is considered a clean/expected behavior that is being handled by the caller.
- Accepting input Is different from a client being able to connect. If 7 clients are connected (via telnet for ex) only five of them are able to submit input. Additional connected clients are never able to send input even after closing some or all of the initial 5 clients.
- Concurrent writing/race condition to the cache is highly probable, hence the choice of a HashTable as a thread-safe data structure.

### Added dependencies:
- Added 'org.apache.commons:commons-lang3:3.6' as a dependency to enable the use of Apache’s StringUtils.
- Added ‘com.google.guava', name: 'guava', version: '23.5-jre'
- Added "org.mockito:mockito-core:1.+"
- Added Unit as a test platform 

### Requirements:
- Java 8+
- IntelliJ Idea preferably
- Gradle

### How to run and test this code
1. Run the server by executing NumberServer class
    1. This starts the Number server
1. Run the test clients by executing the MainTest class in the test folder
    1. This starts 5 client connections that send 2 millions lines of 9-digit numbers to the server and exits.
1. Run the unit test in ClientTest and Maintest
    1. This tests each method in the main classes.
    
`Depending on your local environment, see the following for instructions on how to build and run this code.`


### Steps to run
#### In command line
- From a terminal, and while in the root of this repo, run './gradlew <task>' where ...
Build tasks
-----------
assemble - Assembles the outputs of this project.
build - Assembles and tests this project.
buildDependents - Assembles and tests this project and all projects that depend on it.
buildNeeded - Assembles and tests this project and all projects it depends on.
classes - Assembles main classes.
clean - Deletes the build directory.
jar - Assembles a jar archive containing the main classes.
testClasses - Assembles test classes.

Build Setup tasks
-----------------
init - Initializes a new Gradle build.
wrapper - Generates Gradle wrapper files.

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code.

Help tasks
----------
buildEnvironment - Displays all buildscript dependencies declared in root project 'Zip Code Ranges'.
components - Displays the components produced by root project 'Zip Code Ranges'. [incubating]
dependencies - Displays all dependencies declared in root project 'Zip Code Ranges'.
dependencyInsight - Displays the insight into a specific dependency in root project 'Zip Code Ranges'.
dependentComponents - Displays the dependent components of components in root project 'Zip Code Ranges'. [incubating]
help - Displays a help message.
model - Displays the configuration model of root project 'Zip Code Ranges'. [incubating]
outgoingVariants - Displays the outgoing variants of root project 'Zip Code Ranges'.
projects - Displays the sub-projects of root project 'Zip Code Ranges'.
properties - Displays the properties of root project 'Zip Code Ranges'.
tasks - Displays the tasks runnable from root project 'Zip Code Ranges'.

Verification tasks
------------------
check - Runs all checks.
test - Runs the unit tests.

Other tasks
-----------
compileJava - Compiles main Java source.
compileTestJava - Compiles test Java source.
prepareKotlinBuildScriptModel
processResources - Processes main resources.
processTestResources - Processes test resources.

Rules
-----
Pattern: clean<TaskName>: Cleans the output files of a task.
Pattern: build<ConfigurationName>: Assembles the artifacts of a configuration.
Pattern: upload<ConfigurationName>: Assembles and uploads the artifacts belonging to a configuration.


#### In IntelliJ
- See here https://www.jetbrains.com/help/idea/getting-started-with-gradle.html#run_gradle

#### In Eclipse
- See here https://www.eclipse.org/community/eclipse_newsletter/2018/february/buildship.php





