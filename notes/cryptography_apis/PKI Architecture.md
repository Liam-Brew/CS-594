# PKI Architecture

## Table of Contents

- [PKI Architecture](#pki-architecture)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Writing Certificates](#writing-certificates)
  - [Reading Certificates](#reading-certificates)
  - [Certificate Signer](#certificate-signer)
  - [Creating Certificates](#creating-certificates)
    - [Self-Signed v1 Certificates](#self-signed-v1-certificates)
    - [CA Root Certificates (v3 Certificates)](#ca-root-certificates-v3-certificates)
    - [Intermediate Certificates](#intermediate-certificates)
    - [End Certificates](#end-certificates)
  - [Getting Certificates](#getting-certificates)
    - [CA-Signed Certificate](#ca-signed-certificate)
    - [Certificate Signing Request (CSR)](#certificate-signing-request-csr)
    - [CA Creates Certificate from CSR](#ca-creates-certificate-from-csr)

## Overview

![pki](/notes/assets/cryptography_apis/pki.PNG)

## Writing Certificates

```JcaPEMWriter``` class

Example:

 ```java
StringWriter sbuf = new StringWriter();
JcaPEMWriter wr = new JcaPEMWriter(sbuf);
wr.writeObject(cert);
wr.flush();
wr.close();
String externCert = sbuf.toString();
```

## Reading Certificates

```CertificateFactory``` class

- ```generateCertificate()```: read from input stream (PEM)

Example:

 ```java
// Create the certificate factory
CertificateFactory fact = CertificateFactory.getInstance("X.509", "BC");

// Read the certificate
InputStream is = new FileInputStream(...);
X509Certificate x509Cert = (X509Certificate)fact.generateCertificate(is);
```

## Certificate Signer

```ContentSigner``` class: digitally sign a certificate

```ContentVerifier``` class: authenticate a certificate signature

Example:

```java
public ContentSigner getContentSigner(PrivateKey privateKey) {
    return new JcaContentSignerBuilder("SHA512withRSA").setProvider("BC").build(privateKey);
}

public ContentVerifier getContentVerifier(PublicKey publicKey) {
    return new JcaContentVerifierProviderBuilder()
        .setProvider("BC")
        .build(publicKey);
}
```

## Creating Certificates

### Self-Signed v1 Certificates

This is available in Bouncy Castle API only. Its parameters are:

- ```long id```
- ```X500Name issuerName```
- ```KeyPair keyPair```
- ```int durationHours```

```java
ContentSigner sigGenerator = getContentSigner(keyPair.getPrivate());

X509v1CertificateBuilder certBuilder = new JcaX509v1CertificateBuilder(
    issuerName,
    BigInteger.valueOf(id),
    System.currentTimeInMillis(),
    System.currentTimeInMillis()+duration,
    issuerName,
    keyPair.getPublic()
);


X509CertificateHolder certHolder = certBuilder.build(sigGenerator);

return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);
```

### CA Root Certificates (v3 Certificates)

Similar to the v1 creation procedure, this is available only in the Bouncy Castle API and has the same parameters

```java
ContentSigner sigGenerator = getContentSigner(keyPair.getPrivate());

X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
    issuerName,
    BigInteger.valueOf(id),
    System.currentTimeInMillis(),
    System.currentTimeInMillis()+duration,
    issuerName,
    keyPair.getPublic()
);

JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

certBuilder
    .addExtension(
        Extension.subjectKeyIdentifier,
        false,
        extUtils.createSubjectKeyIdentifier(keyPair.    getPublic()))
    .addExtension(
        Extension.basicConstraints,
        true,
        new BasicConstraints(1))
    .addExtension(
        Extension.keyUsage,
        true,
        CA_USAGE);

X509CertificateHolder certHolder = certBuilder.build(sigGenerator);

return new JcaX509CertificateConverter()
    .setProvider("BC")
    .getCertificate(certHolder);
```

### Intermediate Certificates

This is available only in the Bouncy Castle API. Its parameters are:

- ```long id```
- ```PrivateKey issuerKey```
- ```X509Certificate issuerCert```
- ```X500Name subjectName```
- ```PublicKey subjectKey```
- ```int durationHours```

```java
ContentSigner sigGenerator = getContentSigner(issuerKey);

X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
    getDN(issuerCert),
    BigInteger.valueOf(id),
    System.currentTimeInMillis(),
    System.currentTimeInMillis()+duration,
    subjectName,
    subjectKey
);

JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

certBuilder
    .addExtension(
        Extension.subjectKeyIdentifier,
        false,
        extUtils.createSubjectKeyIdentifier(subjectKey))
    .addExtension(
        Extension.basicConstrains,
        true,
        new BasicConstraints(0))
    .addExtension(
        Extension.keyUsage,
        true,
        CA_USAGE
    );

X509CertificateHolder = certBuilder.build(sigGenerator);

return new JcaX509CertificateConverter()
    .setProvider("BC")
    .getCertificate(certHolder);
```

### End Certificates

This is available only in the Bouncy Castle API. An example use case for this type of certificate is a server. Its parameters are the same as those used in the intermediate certificate

```java
ContentSigner sigGenerator = getContentSigner(issuerKey);

X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
    getDN(issuerCert),
    BigInteger.valueOf(id),
    System.currentTimeInMillis(),
    System.currentTimeInMillis()+duration,
    subjectName,
    subjectKey
);

JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

certBuilder
    .addExtension(
        Extension.subjectKeyIdentifier,
        false,
        extUtils.createSubjectKeyIdentifier(subjectKey))
    .addExtension(
        Extension.basicConstrains,
        true,
        new BasicConstraints(false))
    .addExtension(
        Extension.keyUsage,
        true,
        END_USAGE)
    .addExtension(
        Extension.extendedKeyUsage,
        false,
        SERVER_USAGE_EXT)
    );

/*
 * This saves the server's DNS name to the certificate,
 * allowing clients to do hosting verification to prevent MitMs.
 */
GeneralName san = new GeneralName(GeneralName.dNSName, serverDNS);
GeneralName sans = new GeneralNames(new GeneralName[]{ san });

certBuilder
    .addExtension(
        Extension.subjectAlternativeName,
        false,
        sans
    );

X509CertificateHolder = certBuilder.build(sigGenerator);

return new JcaX509CertificateConverter()
    .setProvider("BC")
    .getCertificate(certHolder);
```

## Getting Certificates

### CA-Signed Certificate

Getting this certificate consists of the following steps:

1. generate a private key
2. build a self-signed (v1) certificate
3. send certificate signing request (CSR) to CA
4. CA returns v3 certificate for key, with a certificate chain with the CA certificate at the root

A scenario for this would be requesting a certificate from a CA

### Certificate Signing Request (CSR)

**Certificate Signing Request (CSR)**: request for a signer certificate from the CA. Its process is:

- CSR sent to CA to request certificate
- CSR is signed by requester
  - identified by subject key identifier
- certificate is signed by CA
  - identified by authority key identifier

This code is available using the Bouncy Castle API only. Its parameters are:

- ```long id```
- ```PrivateKey issuerKey``` (CA private key)
- ```X509Certificate issuerCert```
- ```X500 Principal subjectName```
- ```PublicKey subjectKey```
- ```BasicConstraints basicConstraints```
- ```KeyUsage usage```
- ```long duration```

```java
// Generate subject alternative name(s) for the CSR
List<GeneralName> names = new ArrayList<GeneralName>();
int nameType;

// Pick names, examples being some of the following:
nameType = GeneralName.uniformResourceIdentifier;
nameType = GeneralName.iPAddress;
nameType = GeneralName.dnsName;

// Add names to the list
names.add(new GeneralName(nameType, name));

// Add SANs as an extension to the CSR

GeneralNames sans = new GeneralNames(names.toArray(
    new GeneralName[names.size()])
);

ExtensionsGenerator extGen = new ExtensionGenerator();

extGen.addExtension(
    Extension.subjectAlternativeName,
    false,
    sans
);

// Build CSR
PKCS10CertificationRequestBuilder requestBuilder = new JcaPKCS10CertificationRequestBuilder(
    subject,
    keyPair.getPublic()
);

requestBuilder.addAttribute(
    PKCSObjectIdentifiers.pkcs_9_at_extensionRequest,
    extGen.generate()
);

ContentSigner sigGenerator = getContentSigner(keyPair.getPrivate());

// This is what is sent to the CA
PKCS10CertificationRequest request = requestBuilder.build(sigGenerator);
```

### CA Creates Certificate from CSR

```java
BasicConstraints basicConstraints = new BasicConstraints(false);

PublicKey subjectKey = new JcaPKCS10CertificationRequest(csr).getPublicKey();

ContentVerifierProvider contentVerifier = getContentVerifier(subjectKey);

if (!csr.isSignatureValid(contentVerifier)) {
    throw new IllegalArgumentException(...);
}

X509v3CertificateBuilder certBuilder = 
    new JcaX509v3CertificateBuilder(
        getDN(issuerCert),
        BigInteger.valueOf(id),
        System.currentTimeInMillis(),
        System.currentTimeInMillis()+duration,
        csr.getSubject(),
        subjectKey);

JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

certBuilder
    .addExtension(
        Extension.authorityKeyIdentifier,
        false,
        extUtils.createAuthorityKeyIdentifier(issuerCert))
    .addExtension(
        Extension.subjectKeyIdentifier,
        false,
        extUtils.createSubjectKeyIdentifier(csr.getSubjectKeyPublicKeyInfo()))
    .addExtension(
        Extension.basicConstraints,
        true,
        new BasicConstraints(false))
    .addExtension(
        Extension.keyUsage,
        true,
        END_USAGE);

for (Attribute attr : csr.GetAttributes()) {
    // Process extension request
    if (attr.getAttrType().equals(
        PKCSObjectIdenfieirs.pkcs_9_at_extensionRequest)) {
            // Only process SAN extension request
            Extensions extensions = Extensions.getInstance(attr.getAttrValues().getObjectAt(0));
        }
        GeneralNames.fromExtensions(extensions, Extension.subjectAlternativeName);

        if (sans != null) {
            certBuilder.addExtension(
                Extension.subjectAlternativeName,
                false,
                sans)
            // Check SAN values for allowable values to add to the certificate
            for (GeneralName name : sans.getNames()) {
                switch (name.getTagNo()) {
                    case GeneralName.dNSName:
                        // ...
                }
            }
        }
}

ContentSigner sigGenerator = getContentSigner(issuerKey);

X509CertificateHolder certHolder = certBuilder.build(sigGenerator);

return new JcaX509CertificateConverter()
    .setProvider("BC")
    .getCertificate(certHolder);
```
