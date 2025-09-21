# USE CASE: 2 Produce a Report on Countries in a Continent by Population

## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst I want to produce a report of all countries in a continent organised by largest to smallest population so that I can analyse population distribution per continent.

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains country and continent data.

### Success End Condition

Report of all countries in continent sorted by population is produced.
### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

Request for population data by continent.

## MAIN SUCCESS SCENARIO

  1. Analyst specifies a continent.
  2. System extracts countries belonging to continent.
  3. System sorts by population.
  4. System generates the report.

## EXTENSIONS

  2. Continent not found:
    1. Analyst informed no such continent.

## SUB-VARIATIONS

None.

## SCHEDULE

DUE DATE: Release 1.1
