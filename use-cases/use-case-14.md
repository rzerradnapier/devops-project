# USE CASE: 14 Produce a Report on Top N Cities in a Region

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of the top N most populated cities in a region where N is provided so that I can analyse the largest cities within a region.

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

  1. Analyst requests produce a report on top n cities in a region.
  2. System extracts relevant data.
  3. System sorts/calculates as required.
  4. System generates the report.

## EXTENSIONS

  2. Data not found:
    1. Analyst informed no data exists.

## SUB-VARIATIONS

None.

## SCHEDULE

DUE DATE: Release 2.7
