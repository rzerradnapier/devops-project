# USE CASE: 25 Produce a Population Report for Countries

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a population report for each country showing the total population, population living in cities (with %), and population not living in cities (with %) so that I can understand national urbanisation.

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

  1. Analyst requests produce a population report for countries.
  2. System extracts relevant data.
  3. System sorts/calculates as required.
  4. System generates the report.

## EXTENSIONS

  1. Data not found:
    1.1 Analyst informed no data exists.

## SUB-VARIATIONS

None.

## Use case diagram

![Use Case 25 Diagram](../use-cases-diagram/use-case-25.png)

## SCHEDULE

DUE DATE: Release 4.2
