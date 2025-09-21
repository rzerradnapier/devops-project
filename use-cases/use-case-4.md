# USE CASE: 4 Produce a Report on Top N Countries in the World

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of the top N most populated countries in the world where N is provided so that I can identify the largest countries globally.

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains country population data.

### Success End Condition

Report of top N countries by population is produced.
### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

Request for top N countries by population.

## MAIN SUCCESS SCENARIO

  1. Analyst enters N.
  2. System extracts all countries.
  3. System sorts and selects top N.
  4. System generates the report.

## EXTENSIONS

  2. Invalid N provided:
    1. Analyst informed input not valid.

## SUB-VARIATIONS

None.

## SCHEDULE

DUE DATE: Release 1.3
