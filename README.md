# Gradle TeamCity Git Version Plugin

Gradle plugin to set project version from Git tags and branch names automatically

Adds -dev to version number when building on developer machine, increments version build number when building on TeamCity server.

### Usage

Just add a single line to your `plugins` section:

```
plugins {
    id 'com.github.kropp.teamcity-gitversion' version '0.1.5'
}
```