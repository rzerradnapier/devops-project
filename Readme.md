# devops-project

![workflow](https://github.com/rzerradnapier/devops-project/actions/workflows/main.yml/badge.svg)  

[![LICENSE](https://img.shields.io/github/license/rzerradnapier/devops.svg?style=flat-square)](https://github.com/rzerradnapier/devops/blob/master/LICENSE)  

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


| ID    | Name | Met  | Screenshot |
|-------|------|------|------------|
| 1     |  |  |  |




