# Getting Started

## Run the default

```
./gradlew run
```

## Choose different tests

```
./gradlew run --args='RaceCondition'
./gradlew run --args='Lock'
./gradlew run --args='SynchronizedBlock'
./gradlew run --args='SynchronizedMethod'
./gradlew run --args='AtomicInteger'
./gradlew run --args='SynchronizedMethodFlip'
```

## Correct results

This is the correct output, anything less indicates threading errors.

```
Count = 10000
```
