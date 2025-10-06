# USE CASE: 23 Produce a Population Report for Continents

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a population report for each continent showing the total population, population living in cities (with %), and population not living in cities (with %) so that I can understand continental urbanisation.

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains relevant data.

### Success End Condition

Report is produced.
### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

Request for this report.

## MAIN SUCCESS SCENARIO

  1. Analyst requests produce a population report for continents.
  2. System extracts relevant data.
  3. System sorts/calculates as required.
  4. System generates the report.

## EXTENSIONS

  1. Data not found:
    1.1 Analyst informed no data exists.

## SUB-VARIATIONS

None.

## Use case diagram

![Use Case 23 Diagram](../use-cases-diagram/use-case-23.png)

## SCHEDULE

DUE DATE: Release 4.0
