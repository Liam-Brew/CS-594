# Overview

## Table of Contents

- [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Background](#background)
  - [Providers](#providers)

## Background

Java Cryptography Architecture (JCA):

- provider-based API
- provider: collection of factory objects (for keys etc.)
- original focus on authentication

Java Cryptography Extension (JCE):

- extension for encryption
- subject to export restrictions ("munitions")
- now part of JDK

## Providers

**Sun JCA/JCE**:

- default implementation in JDK
- subject to US export laws

**Bouncy Castle**:

- lightweight API (for J2ME MIDP)
- open source
- default implementation in Android
- installing Bouncy Castle:

    1. install jar files
       1. ```$JAVA_HOME/jre/lib/ext```
    2. enable provider in java.security
       1. ```security.provider.2=org.bouncycastle.jce.provider.BouncyCastleProvider```
    3. install during execution
       1. ```Security.addProvider(new BouncyCastleProvider());```

**Spongy Castle**:

- port of full Bouncy Castle to Android
