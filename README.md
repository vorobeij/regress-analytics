# Benchmarks analytics for android projects

# The problem

We do not have tools to inspect historical app's performance.
At some point you start to see your application became slow.
But at what point?
What were those changes impacting performance so much that you start notice?
What if new code impacts in a slight way, so you can't notice that?

# Solution

1. Run performance benchmark locally
2. Save all benchmark data to local storage
3. Analyse new data and create report on how much your changes affect performance.

> This solution may be deployed on a server for `develop` branch.
> Each commit into develop could be measured from performance point of view.

# Caveats

1. Benchmarks are sensitive to environment. Run all your benchmarks on one machine/emulator to see meaningful insights.

## Flow

1. Before merging branch launch benchmarks
2. Add that data to database
3. Analyse historical data and make decision about performance regress

## Script inputs (table item fields)

1. git author
2. git commit date
3. git message
4. git branch
5. are changes committed?
6. benchmark data

<aside>
💡 Pass **project folder path** and **benchmark json path**
</aside>

## Create report with charts

For each benchmark:

1. Sort all entries **time ascending**
2. Map each entry N as x coordinate
3. y = time, ms

### Chart tooltip

1. Author
2. Date
3. Branch
4. Commit message

## Alerts

1. Calculate median value and confidence interval
2. If new min value > median + confidence interval → alert

## About the project

- [Features](./wiki/features.md)

## Installation

1. Install database [h2](https://www.h2database.com/html/download.html)
   , [video](https://www.youtube.com/watch?v=6wUQagjtJ4c)

## Android Studio flow

Run benchmarks:

```shell
bundle exec fastlane android benchmarks
```

which

1. runs microbenchmarks and pull data from device
2. runs macrobenchmarks and pull data from device
3. launches regress analytics script
   1. save new entries and send them to storage (remote or local database)
   2. delete saved files
   3. if new benchmarks regress performance, throw exception

// todo use suspend functions https://github.com/JetBrains/Exposed/wiki/Transactions#working-with-coroutines