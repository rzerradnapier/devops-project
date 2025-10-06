# USE CASE: 5 Produce a Report on Top N Countries in a Continent

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of the top N most populated countries in a continent where N is provided so that I can focus on the largest countries within that continent.

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains country population and continent data.

### Success End Condition

Report of top N countries in continent by population is produced.
### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

Request for top N countries in continent.

## MAIN SUCCESS SCENARIO

  1. Analyst enters continent and N.
  2. System extracts countries of continent.
  3. System sorts and selects top N.
  4. System generates report.

## EXTENSIONS

  2. Continent not found:
    1. Analyst informed no such continent.

## SUB-VARIATIONS

## Use case diagram

![use-case-5.png](../use-cases-diagram/use-case-5.png)

None.

## SCHEDULE

DUE DATE: Release 1.4
