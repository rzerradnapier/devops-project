# USE CASE: 3 Produce a Report on Countries in a Region by Population

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of all countries in a region organised by largest to smallest population so that I can compare populations at a regional level.

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains country and region data.

### Success End Condition

Report of all countries in region sorted by population is produced.
### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

Request for population data by region.

## MAIN SUCCESS SCENARIO

  1. Analyst specifies region.
  2. System extracts countries in that region.
  3. System sorts by population.
  4. System generates the report.

## EXTENSIONS

  2. Region not found:
    1. Analyst informed no such region.

## SUB-VARIATIONS

None.

## SCHEDULE

DUE DATE: Release 1.2
