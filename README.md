# SpringBoot WhiteList Middleware

## Introduction
This Spring Boot WhiteList Middleware is designed to provide a mechanism for implementing white-list functionality in Spring Boot applications. It allows developers to annotate methods with @DoWhiteList and specify the whitelist key, enabling the middleware to intercept method calls and perform whitelist validation based on configured whitelist properties.

## Features
* Annotation-driven whitelist implementation.
* Configurable whitelist properties.
* Middleware interception for whitelist validation.

## Getting Started
Prerequisites
* Java Development Kit (JDK) 8 or higher
* Maven

## Installation
To use this middleware in your Spring Boot application, you can include it as a dependency in your pom.xml file:

```xml
<dependency>
    <groupId>your-group-id</groupId>
    <artifactId>your-artifact-id</artifactId>
    <version>your-version</version>
</dependency>

```

## Usage
* Annotate the methods that require whitelist validation with @DoWhiteList.
* Specify the whitelist key in the annotation.
* Configure whitelist properties in your Spring Boot application properties file.
