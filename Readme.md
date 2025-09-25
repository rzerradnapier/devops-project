# devops-project

![workflow](https://github.com/rzerradnapier/devops-project/actions/workflows/main.yml/badge.svg)  

[![LICENSE](https://img.shields.io/github/license/rzerradnapier/devops.svg?style=flat-square)](https://github.com/rzerradnapier/devops/blob/master/LICENSE)  

[![Releases](https://img.shields.io/github/release/rzerradnapier/devops/all.svg?style=flat-square)](https://github.com/rzerradnapier/devops/releases)

---

# develop branch

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/rzerradnapier/devops-project/main.yml?branch=develop)

---

## Table of Contents
1. [IntelliJ Debugger Setup](#intellij-debugger-setup)
2. [User Stories](#user-stories)
   - [Country Reports](#country-reports)
   - [City Reports](#city-reports)
   - [Capital City Reports](#capital-city-reports)
   - [Population Reports](#population-reports)
   - [Population Queries](#population-queries)
   - [Language Reports](#language-reports)

---

## IntelliJ Debugger Setup

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
- **Step 3:** Access your app (e.g., `http://localhost:8080/…`) → breakpoints in IntelliJ should trigger.

---

## User Stories

### Country Reports
- **US-01**: As an analyst, I want to list all countries worldwide by population (largest → smallest) to compare global populations.
- **US-02**: As an analyst, I want to list all countries in a continent by population (largest → smallest) to analyze distribution per continent.
- **US-03**: As an analyst, I want to list all countries in a region by population (largest → smallest) to compare at a regional level.
- **US-04**: As an analyst, I want to retrieve the top **N** most populated countries worldwide so that I can identify the largest globally.
- **US-05**: As an analyst, I want to retrieve the top **N** most populated countries per continent to focus on major populations there.
- **US-06**: As an analyst, I want to retrieve the top **N** most populated countries per region to focus on major populations regionally.

---

### City Reports
- **US-07**: Report all cities in the world by population (largest → smallest).
- **US-08**: Report all cities in a continent by population (largest → smallest).
- **US-09**: Report all cities in a region by population (largest → smallest).
- **US-10**: Report all cities in a country by population (largest → smallest).
- **US-11**: Report all cities in a district by population (largest → smallest).
- **US-12**: Report the top **N** most populated cities worldwide.
- **US-13**: Report the top **N** most populated cities per continent.
- **US-14**: Report the top **N** most populated cities per region.
- **US-15**: Report the top **N** most populated cities per country.
- **US-16**: Report the top **N** most populated cities per district.

---

### Capital City Reports
- **US-17**: Report all capital cities in the world by population (largest → smallest).
- **US-18**: Report all capital cities in a continent by population (largest → smallest).
- **US-19**: Report all capital cities in a region by population (largest → smallest).
- **US-20**: Report the top **N** most populated capital cities worldwide.
- **US-21**: Report the top **N** most populated capital cities per continent.
- **US-22**: Report the top **N** most populated capital cities per region.

---

### Population Reports
- **US-23**: Produce a report for each continent with:
   - Total population
   - Population in cities (with %)
   - Population not in cities (with %)
- **US-24**: Same as above, but per region.
- **US-25**: Same as above, but per country.

---

### Population Queries
- **US-26**: Retrieve the total population of the world.
- **US-27**: Retrieve the total population of a continent.
- **US-28**: Retrieve the total population of a region.
- **US-29**: Retrieve the total population of a country.
- **US-30**: Retrieve the total population of a district.
- **US-31**: Retrieve the total population of a city.

---

### Language Reports
- **US-32**: Produce a report of speakers of **Chinese, English, Hindi, Spanish, and Arabic**, ordered from greatest to smallest, including their percentage of the world population.

---