# devops-project

![workflow](https://github.com/rzerradnapier/devops-project/actions/workflows/main.yml/badge.svg)  

[![LICENSE](https://img.shields.io/github/license/rzerradnapier/devops-project.svg?style=flat-square)](https://github.com/rzerradnapier/devops-project/blob/master/LICENSE)  

[![Releases](https://img.shields.io/github/release/rzerradnapier/devops-project/all.svg?style=flat-square)](https://github.com/rzerradnapier/devops-project/releases)

---

### Develop branch

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/rzerradnapier/devops-project/main.yml?branch=develop)

### Release branch

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/rzerradnapier/devops-project/main.yml?branch=release)

### Master branch

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/rzerradnapier/devops-project/main.yml?branch=master)

---


## 📑 Table of Contents

1. [About the project](#about-the-project)
2. [Team](#team)
3. [IntelliJ Debugger Setup](#intellij-debugger-setup)
    - [Add a Remote JVM Debug Configuration](#add-a-remote-jvm-debug-configuration)
    - [Debug Workflow](#debug-workflow)
    - [Helper Scripts](#helper-scripts)
4. [Test Coverage with Jacoco](#test-coverage-with-jacoco)
5. [Checklist Submission 1](#checklist-submission-1)
6. [Requirements Met](#requirements-met)
---


## 🚀 About the project
This project is a submission for coursework assessment of the DevOps module SET09803 at Napier university
in this assessment we are building a new system that easy access to the population information extracted from
an existing world database available [here](https://dev.mysql.com/doc/index-other.html).

The requirements for this project can be found
[here](https://github.com/Kevin-Sim/SET09803-DevOps-Global-Online/blob/master/assessment/README.md).

---
## 👨‍💻 Team
1. Dorian Cain
2. Jeremiah Udoh
3. Kevron Ferdinand
4. Nguyen Nguyen
5. Reda Zerrad `Product Owner`
6. Yanida Perumal `SCRUM Master`

---
## 🐞 IntelliJ Debugger Setup

### Add a Remote JVM Debug Configuration
1. Go to **Run → Edit Configurations…**
2. Click **+** → Select **Remote JVM Debug** (sometimes *Remote Debug*).
3. Fill in:
   - **Name:** `Remote Debug App`
   - **Host:** `localhost`
   - **Port:** `5005`
   - Leave the rest as default.
4. Click **Apply → OK**.

### Debug Workflow
- **Step 1:** Run your **Docker-Compose Deployment** config → containers start.
- **Step 2:** Start your **Remote Debug App** config (Debug mode) → IntelliJ attaches to the JVM inside your container.
- **Step 3:** Run the app  → breakpoints in IntelliJ should trigger.

### Helper Scripts
We need to always have a test DB up for the tests to work and to package the code for that reason we have created helper scripts that can be found in /helper-scripts/ folder :
- **Run All tests:** by running  **run_tests.sh** it runs all tests, the script will deploy the test DB run the tests and then destroy the test DB.
- **Package the code:** by running  **build_package_clean.sh** it sets up the test DB  run all tests, packages the code then destroys the test DB.
- **Start the test DB:** by running  **start_test_database.sh** it will deploy and start the test DB and keep it running.
- **stop the test DB:** by running  **stop_test_database.sh** it will stop and destroy the test DB docker container.



---
### Test Coverage with Jacoco
	•	Build fails if overall LINE coverage < 80%.
	•	HTML report: target/site/jacoco/index.html.

---
## 📋 Checklist Submission 1 

The following are in place:

- ✅ GitHub project for coursework set-up.
- ✅ Product Backlog created.
- ✅ Project builds to self-contained JAR with Maven.
- ✅ Dockerfile for project set-up and works.
- ✅ GitHub Actions for project set-up and build is working using JAR, and Docker on GitHub Actions.
- ✅ Correct branches for GitFlow workflow created - includes `master`, `develop`, and `release` branches.
- ✅ First release created on GitHub.
- ✅ Code of Conduct defined.
- ✅ Issues being used on GitHub.
- ✅ Tasks defined as user stories.
- ✅ Project integrated with Zube.io.
- ✅ Kanban/Project Board being used.
- ✅ Sprint Boards being used.
- ✅ Full use cases defined.
- ✅ Use case diagram created.

---
## 🏁 Requirements Met


| ID | Name                                                                                                | Met  | Screenshot                                                                                 |
|----|-----------------------------------------------------------------------------------------------------|------|--------------------------------------------------------------------------------------------|
| 1  | Get all countries organized by population descending                                                | ✅ | ![all countries organized by population descending](screenshots/use-case-1-screenshot.png) |
| 2  | Produce a Report all countries in a continent organised by largest to smallest population Continent | ✅ | ![all countries organized by population descending](screenshots/use-case-2-screenshot.png) |
| 3  | Produce a Report on Countries in a Region by Population                                             | ✅ | ![all countries organized by population descending](screenshots/use-case-3-screenshot.png) |
| 4  | Produce a Report on Top N Countries in the World                                                    | ✅ | ![all countries organized by population descending](screenshots/use-case-4-screenshot.png) |
| 5  | Produce a Report on Top N Countries in a Continent                                                  | ✅ | ![all countries organized by population descending](screenshots/use-case-5-screenshot.png) |
| 6  | Produce a Report on Top N Countries in a Region                                                     | ✅ | ![all countries organized by population descending](screenshots/use-case-6-screenshot.png) |
| 7  | All Cities in the World by Population                                                               | ✅ | ![all countries organized by population descending](screenshots/use-case-7.png)            |
| 8  | Cities in a Continent by Population (Asia)                                                          | ✅ | ![all countries organized by population descending](screenshots/use-case-8.png)            |
| 9  | Cities in a Region by Population (Western Europe)                                                   | ✅ | ![all countries organized by population descending](screenshots/use-case-9.png)            |
| 10 | Cities in a Country by Population (USA)                                                             | ✅ | ![all countries organized by population descending](screenshots/use-case-10.png)           |
| 11 | Cities in a District by Population (California)                                                     | ✅ | ![all countries organized by population descending](screenshots/use-case-11.png)           |
| 12 | Top N Cities in the World by Population (N=10)                                                      | ✅ | ![all countries organized by population descending](screenshots/use-case-12.png)           |
| 13 | Produce a Report on Top N Cities in a Continent (Continent=Africa, N=10)                            | ✅ | ![all countries organized by population descending](screenshots/use-case-13.png)           |
| 14 | Produce a Report on Top N Cities in a Region (Region=South America, N=10)                           | ✅ | ![all countries organized by population descending](screenshots/use-case-14.png)           |
| 15 | Produce a Report on Top N Cities in a Country (Country = United States, N=10)                       | ✅ | ![all countries organized by population descending](screenshots/use-case-15.png)           |
| 16 | Produce a Report on Top N Cities in a District (District = California, N=10)                        | ✅ | ![all countries organized by population descending](screenshots/use-case-16.png)           |
| 17 | Produce a Report on All Capital Cities in the World by Population                                   | ✅ | ![all countries organized by population descending](screenshots/use-case-17.png)           |




