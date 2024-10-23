![Maven Central Version](https://img.shields.io/maven-central/v/dev.edgetom/interaction-api?style=flat&logo=sonatype&color=%2300bf33&link=%3Curl%3Ehttps%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fdev.edgetom%2Finteraction-api%3C%2Furl%3E)
![example workflow](https://github.com/tgross03/Interaction-API/actions/workflows/maven.yml/badge.svg?event=push)

# Interaction-API

A Spigot API for managing interactions with items.

> [!WARNING]  
> At the moment the API is designed and tested for Spigot version `1.19.4`!
> Other versions will be usable in the future.

## Include the API in your project

### Maven (Recommended)

You can include the package as a dependency in Maven.
For that you have to include the following entry in your `pom.xml` in the surrounding
`<dependencies> ... </dependencies>`:

```xml
<dependency>
    <groupId>dev.edgetom</groupId>
    <artifactId>interaction-api</artifactId>
    <version>VERSION</version>
</dependency>
```
Replace the placeholder `VERSION` with the version you want to use. You can find the all versions of
the API in the [Maven Central Repository](https://central.sonatype.com/artifact/dev.edgetom/interaction-api).

### Gradle

Alternatively you can include the API via Gradle:

```Gradle
implementation group: 'dev.edgetom', name: 'interaction-api', version: 'VERSION'
```

Replace the placeholder `VERSION` with the version you want to use. You can find the all versions of
the API in the [Maven Central Repository](https://central.sonatype.com/artifact/dev.edgetom/interaction-api).
