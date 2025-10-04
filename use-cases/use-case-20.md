# USE CASE: 20 Produce a Report on Top N Capital Cities in the World

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of the top N most populated capital cities in the world where N is provided so that I can identify the most populated capitals globally.

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

  1. Analyst requests produce a report on top n capital cities in the world.
  2. System extracts relevant data.
  3. System sorts/calculates as required.
  4. System generates the report.

## EXTENSIONS

  1. Data not found:
    1.1 Analyst informed no data exists.

## SUB-VARIATIONS

None.

## Use case diagram

![Use Case 20 Diagram](../use-cases-diagram/use-case-20.png)

## SCHEDULE

DUE DATE: Release 3.3
