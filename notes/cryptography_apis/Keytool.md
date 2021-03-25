# Keytool

## Table of Contents

- [Keytool](#keytool)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
    - [Example: KeyStore for server](#example-keystore-for-server)
  - [Keytool Operations](#keytool-operations)
    - [Public-Private Keypair (Offline)](#public-private-keypair-offline)
    - [Export Certificate as .PEM](#export-certificate-as-pem)
    - [Publice-Private Keypair (Online)](#publice-private-keypair-online)
    - [Generate CSR](#generate-csr)
    - [Generate Server Certificate from CSR](#generate-server-certificate-from-csr)
    - [Import Trust Root](#import-trust-root)

## Overview

**Keytool**: command line tool for managing keystores

Suggested truststore file types for this tool are:

- .jks: for JKS type (JDK)
- .bks: for BKS type (Bouncy Castle)

Suggested keystore file types:

- .p12: for PKCS12 (JDK or Bouncy Castle)

### Example: KeyStore for server

Two server keystores:

- offline (self-signed certificate)
  - keystore.p12
  - keep root key offline for security
  - sign online server certificate
- online (certificate signed by offline key)
  - keystore.jks
  - online key for e.g. SSL connections

## Keytool Operations

Define bash shell alias for the BC keytool:

```bash
# Define bash shell alias for BC keytool
alias keytoolbc = 'keytool \
    -provider org.bouncycastle.jce.provider
        .BounceCastleProvider
    -providerpath \
     ${HOME}/Tools/Bouncycastle/crypto/jars'
```

### Public-Private Keypair (Offline)

Generate public-private key pair for offline server:

```bash
keytoolbc -genkeypair \
    -keystore keystore.p12 \
    -storepass changeit \
    -keypass changeit \
    -storetype PKCS12 \
    -alias root-key -keysize 2048 -keyalg RSA \
    -sigalg SHA512withRSA \
    -dname "C=US,0=Stevens,CN=CS594 CA"
```

### Export Certificate as .PEM

Export root certificate as PEM file (-rfc):

 ```bash
keytoolbc -exportcert \
    -keystore keystore.p12 \
    - storepass changeit \
    - keypass changeit \
    -storetype PKCS12 \
    -alias root-key \ 
    -rfc \
    -file root-cert.pem
```

### Publice-Private Keypair (Online)

Generate key-pair for online server:

 ```bash
keytoolbc -genkeypair \
    -keystore keystore.jks \
    -storepass changeit \
    -keypass changeit \
    -storetype JKS \
    -alias server-key \
    -keysize 2048 -keyalg RSA \
    -sigalg SHA512withRSA \
    -dname "C=US,O=Stevens,CN=CS594 Server"
```

### Generate CSR

Generate CSR for online server certificate:

 ```bash
keytoolbc -certreq \
    -alias server-key \
    -storepass changeit \
    -keypass changeit \
    -storetype JKS \
    -keysize 2048 -keyalg RSA \
    -sigalg SHA512withRSA \
    -dname "C=US,O=Stevens,CN=CS594 Server" \
    -file server-cert.csr
```

### Generate Server Certificate from CSR

Generate online server certificate from CSR:

 ```bash
keytoolbc -gencert \
    -keystore keystore.p12 \
    -storepass changeit \
    -keypass changeit \
    -storetype PKCS12 \
    -alias root-key \
    -infile server-cert.csr \
    -outfile server-cert.cer \
    -validity 365
```

### Import Trust Root

Import trust root into online keystore:

 ```bash
keytoolbc -importcert \
    -keystore keystore.jks \
    -storetype JKS \
    -storepass changeit \
    -alias server-key \
    -keypass changeit \
    -file server-cert.cer
```
