# Assignment 2 - *Project Name*

## Instructions
### Setup Environment
| Task        | Guide |
| ----------- | ----------- |
|  Install Java 8     | We require [java-8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  |
| Install Maven   | Windows: [tutorial](https://www.google.com/search?q=install+maven+on+windows&oq=install+maven+on+windows&aqs=chrome..69i57j0l5.3082j0j4&sourceid=chrome&ie=UTF-8), Mac: `brew install maven`, Ubuntu: `sudo apt-get install maven`        |
| Setup Intellij | [Tutorial](https://www.jetbrains.com/help/idea/installation-guide.html?section=Windows) |

Make sure that your JAVA_HOME environment variable is setup by verifying echo %JAVA_HOME%. It should give the path of JDK like C: \Program Files\Java\jdk1.8.0_211. This should be Java 8. Any other version would fail. In mac/linux you can set this variable by saving adding following lines to your `~/.bash_profile` file
```
export JAVA_HOME=jdk8-install-dir
export PATH=$JAVA_HOME/bin:$PATH

```

Each team has been assigned a unique team number in the google sheets here [https://docs.google.com/spreadsheets/d/1k4qi3q-Gz5CZrFTtR_28K1AdZypRc2O4OwrGVuevBjA/edit#gid=1789860372]. Use that team number to create a git repository in EECS40-19 with name eecs40-S19-assignment2-team-[XX].git where XX is your team number
To setup the project, open the command line and enter following git commands 

```
git clone https://github.uci.edu/EECS40-19/assignment2
cd assignment2/
git remote rename origin upstream
git remote add origin https://github.uci.edu/EECS40-19/eecs40-S19-assignment2-team-[XX].git
git push -u origin master
```
Replace [XX] above with your team number. Follow the exact convention as above to create your team's repo. Do not add team name in the repo. 

### Import project in Intellij
To setup project in intellij:

In command prompt, cd into assignment2 folder that is just created as D:\EECS40\assignment2>
  * Open pom.xml file and change `<artifactId>assignment2</artifactId>` tag in line number 7 to the below line. Replace 'XX' with your team number `<artifactId>eecs40-S19-assignment2-team-[XX]</artifactId>` . This is the name your jar file will be given
  * Now save and close this pom.xml
  * Run `mvn clean install -DskipTests` in command line
  * open IntelliJ -> Open -> Choose the directory. Wait for IntelliJ to finish importing and building.
  * You can run the Main program under src/main/java/edu.uci.eecs40 package to test if everything works. For this press Ctrl+E->Select Maven->LifeCycle and then run install. You can also try test.

This is a project managed by Maven build tool. If you are new to maven you can read up over here [https://www.jetbrains.com/help/idea/maven-support.html#maven_import_project_start] or watch this short video on using maven in intellij [https://youtu.be/pt3uB0sd5kY] 

### Project structure
```
clyton@clyton-ThinkPad-T430:~/git/assignment2$ tree
.
├── assignment2.iml
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── edu
│   │   │       └── uci
│   │   │           └── eecs40
│   │   │               └── Main.java
│   │   └── resources
│   └── test
│       ├── java
│       │   └── edu
│       │       └── uci
│       │           └── eecs40
│       │               └── MainTest.java
│       └── resources
│           └── dummySimulatorTest
│               ├── expected_output.redcode
│               ├── program_x.redcode
│               └── program_y.redcode
└── target
    ├── assignment2-1.0-SNAPSHOT.jar
    ├── assignment2-1.0-SNAPSHOT-shaded.jar
```
* All your source files will be under the `src/main/java` directory. You should create source packages under this directory. 
* `Main.java` is the entry point of your program. So make sure you call your helper functions from inside Main.main() method. 
* `src/test/java` contain the test code. It's a good practice to write unit tests for small functionalities that you implement. The code you write in this folder will not be graded by us; its only for your convenience and learning. For this purpose, I have included a sample test code which tests a dummySimulator. You can browse through this code and use it as a template to create your own tests. Junit library is used to create tests
* `src/main/resources` and `src/test/resources` contains the files that your code will use. For the dummySimulatorTest, I have added two input files and an expected output file. Use this directory structure to organize additional tests that you may write
* In order to run all your tests you can use the maven command `mvn test` on command line or run it via intellij maven window.
* the `target/` folder contains all the jar files, class files and documentation. Jar files will be needed to make a submission.

### Submission
We will grade your assignment by running your jar file. The jar file contains all dependencies you've used as well as the code you've written. To create a jar file using maven to execute `mvn install` from command line OR do it from the ide by pressing 'Ctrl+E' > Choosing Maven window > Under Lifecycle, click 'install'. This will generate `eecs40-S19-assignment2-team-XX-1.0-SNAPSHOT-shaded.jar` file in the target folder. Make sure you are able to run this jar in intellij by right clicking it and selecting 'Run'. Now commit and push the jar file to your github repository. Avoid commiting the entire target folder because it is a derived folder and it will add unwanted noise to your git logs. Now upload the `*shaded.jar` file to canvas

**Project Name** Project description.

Team Name:
* **Student Name 1**
- UCInetID
* **Student Name 2**
- UCInetID

## Functionalities
[//]: # (Write [x] to mark off what was accomplished.<br/>)
The following **required** functionality is complete:

* [ ] 
* [ ] 
* [ ] 

[//]: # (* [ ] Got any features?)
The following **additional** features are implemented:<br/>
* [ ] 
