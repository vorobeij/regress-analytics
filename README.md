# Benchmarks analytics for android projects

[Android project example](https://github.com/vorobeij/android-app-template)

# The problem

We do not have tools to inspect historical app’s performance. At some point you start to see your application became
slow. But at what point? What were those changes impacting performance so much that you start notice? What if new code
impacts in a slight way, so you can’t notice that?

# What this script does

[Android benchmarks library](https://developer.android.com/topic/performance/benchmarking/benchmarking-overview)

1. Read new benchmarks data from `projectRootPath`/benchmarks/json (
   see [Arguments.kt](./app/src/main/kotlin/ru/vorobeij/regress/Arguments.kt))
2. Send new benchmarks data to backend api, which returns list
   of [benchmark regressions](./app/src/main/kotlin/ru/vorobeij/regress/benchmark/data/BenchmarkAnalyticsResult.kt)
3. Throw exception if performance worse more than `threshold`, %

May be deployed on CI for `develop` branch. Each commit into `develop` could be measured from performance point of view.

# Workflow

## Before launching this script

1. Commit all of your code. Otherwise it won’t be saved
2. Run all your benchmarks
3. Pull benchmarks data from the device

**Microbenchmarks**

```bash
#!/bin/sh

# Input arguments
mkdir -p ../benchmarks/json
BENCHMARK_RESULTS_OUTPUT=$(readlink -f ../benchmarks/json)
PACKAGE_NAME=ru.vorobeij.android.app.benchmark.test

# Private constants
BENCHMARK_RESULT_OUTPUT_DEVICE=/sdcard/Download
BENCHMARK_RUNNER=androidx.benchmark.junit4.AndroidBenchmarkRunner
ROOT=..
OUTPUT_JSON_NAME=$PACKAGE_NAME-test-benchmarkData.json

cd $ROOT
# Info: https://developer.android.com/topic/performance/benchmarking/benchmarking-in-ci
./gradlew assembleAndroidTest
#adb install ./app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb install ./microbenchmark/build/outputs/apk/androidTest/release/microbenchmark-release-androidTest.apk
# List installed instrumentation
adb shell pm list instrumentation
# Launch instrumentation benchmark
adb shell am instrument -w -e additionalTestOutputDir $BENCHMARK_RESULT_OUTPUT_DEVICE \
                        $PACKAGE_NAME/$BENCHMARK_RUNNER \
                        -e no-isolated-storage true
# Pull benchmark results
adb pull $BENCHMARK_RESULT_OUTPUT_DEVICE/$PACKAGE_NAME-benchmarkData.json \
         $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME
cat $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME | wc -l

# Check for emulators connected:
jsonLines=$(cat $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME | wc -l)
if [ $jsonLines -lt 1 ]; then
  echo "\n\u001b[31mERROR: benchmark failed\u001b[0m"
  exit 1
fi
```

**Macrobenchmarks**

```bash
#!/bin/sh

# Input arguments
mkdir -p ../benchmarks/json
BENCHMARK_RESULTS_OUTPUT=$(readlink -f ../benchmarks/json)
PACKAGE_NAME=ru.vorobeij.macrobenchmark

# Private constants
BENCHMARK_RESULT_OUTPUT_DEVICE=/sdcard/Download
BENCHMARK_RUNNER=androidx.test.runner.AndroidJUnitRunner
ROOT=..

cd $ROOT
# Info: https://developer.android.com/topic/performance/benchmarking/benchmarking-in-ci
./gradlew assembleBenchmark
#adb install ./app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb install ./app/build/outputs/apk/benchmark/app-benchmark.apk
# List installed instrumentation
adb shell pm list instrumentation
# Launch instrumentation benchmark
adb shell am instrument -w -e additionalTestOutputDir $BENCHMARK_RESULT_OUTPUT_DEVICE \
                        $PACKAGE_NAME/$BENCHMARK_RUNNER \
                        -e no-isolated-storage true
# Pull benchmark results
OUTPUT_JSON_NAME=$PACKAGE_NAME-benchmarkData.json
adb pull $BENCHMARK_RESULT_OUTPUT_DEVICE/$OUTPUT_JSON_NAME \
         $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME

cat $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME | wc -l

# Check for emulators connected:
jsonLines=$(cat $BENCHMARK_RESULTS_OUTPUT/$OUTPUT_JSON_NAME | wc -l)
if [ $jsonLines -lt 1 ]; then
  echo "\n\u001b[31mERROR: macro benchmark failed\u001b[0m"
  exit 1
fi
```

## Launching the script

1. Clone repo and run `./gradlew publishToMavenLocal`
2. Copy `regress.jar` to your project

   **analyse_benchmarks.sh**

    ```bash
    #!/usr/bin/env bash
    
    STORAGE_FILE=../benchmarks/reports/benchmarks.json
    java -jar regress.jar --projectRootPath $(readlink -f ../) \
                          --threshold 10 \
                          --storageFilePath $(readlink -f $STORAGE_FILE)
    ```

3. Commit all your current progress
4. Launch **analyse_benchmarks.sh**
5. If your new code makes benchmarks slower, you’ll know it now

## Android Studio flow

Run benchmarks with `bundle exec fastlane android benchmarks`:

```ruby
desc "Benchmarks"
  lane :benchmarks do
    sh "sh ../ci/microbenchmarks.sh"
    sh "sh ../ci/macrobenchmarks.sh"
    sh "sh ../ci/analyse_benchmarks.sh"
  end
```

1. run microbenchmarks and pull data from device
2. run macrobenchmarks and pull data from device
3. launche regress analytics script
    1. save new entries and send them to storage (remote or local database)
    2. delete saved files
    3. if new benchmarks have performance impact, throw an exception (fail the build)

### Local usage

1. Run `bundle exec fastlane android benchmarks` after each commit

Only after 10 entries you can see errors in console.

# Roadmap

1. Implement [BenchmarkRepository](https://github.com/vorobeij/regress-analytics/blob/main/app/src/main/kotlin/ru/vorobeij/regress/benchmark/reposotory/BenchmarksRepository.kt)
for your benchmark backend
2. Create backend for benchmarks data

## Project structure

- [Features](./wiki/features.md)
