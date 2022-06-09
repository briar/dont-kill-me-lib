# Do not kill me library

An Android library helping to keep a foreground service with wake-locks running. No other use-cases considered.

## Include in your project

    implementation 'org.briarproject:dont-kill-me-lib:[version]'

where `[version]` is the latest version.

## Publishing

To publish latest release in staging repo on maven central:

    ./gradlew publish

To do the actual release from staging repo to maven central:

    ./gradlew closeAndReleaseRepository

Alternatively, visit https://oss.sonatype.org/#stagingRepositories and release there.
