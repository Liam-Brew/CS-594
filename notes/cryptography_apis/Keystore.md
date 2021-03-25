# Keystore

## Table of Contents

- [Keystore](#keystore)
  - [Table of Contents](#table-of-contents)
  - [Keystore and Truststore](#keystore-and-truststore)
    - [JDK Keystore Types](#jdk-keystore-types)
    - [Bouncy Castle Keystore Types](#bouncy-castle-keystore-types)
  - [KeyStore API](#keystore-api)
    - [Truststore Operations](#truststore-operations)
    - [Keystore Operations](#keystore-operations)
    - [Load or Init Keystore](#load-or-init-keystore)
    - [Save Keystore](#save-keystore)
    - [Credentials](#credentials)

## Keystore and Truststore

**Keystore**: storage of private credentials

- credential = key + certificate chain

**Truststore**: storage of trust roots

- certificates for trusted entities
- Java system-wide defaults: cacerts.jks

```KeyStore``` class is the implementation for both the keystore and/or truststore

### JDK Keystore Types

JKS: private keys and certificates

- primarily for certificates
- master password for integrity

JCEKS: can also contain symmetric keys

- 3DES encryption for keys

PKCS12: implementation of PKCS#12 standard

- intended for credentials
- cannot store trust roots

### Bouncy Castle Keystore Types

BKS: symmetric keys, private keys, and certificates

- master password for integrity
- 3DES encryption for keys

UBDER: proprietary (will not work with keytool)

- 3DES encryption for keys
- Blowfish encryption of the store (symmetric key encryption algorithm)

PKCS12: implementation of PKCS#12 standard

- intended for credentials
- can be used to store trust roots

## KeyStore API

### Truststore Operations

- ```getInstance(type, provider)```
- ```load(inputStream, password)```: password protects integrity of certificates
- ```setCertificate(alias, certificate)```
- ```getCErtificate(alias)```: trusted certificates

### Keystore Operations

- ```load(inputStream, password)```: password protects integrity of certificates
- ```setKeyEntry(alias, key, password, chain)```
  - password protects secrecy of key
  - Credential = key + chain
- ```getKey(alias, password)```
- ```getCertificateChain(alias)```

### Load or Init Keystore

 ```java
public static KeyStore load(File store, char[] password, String keystoreType) {
    KeyStore keystore = KeyStore.getInstance(keystoreType);
    if (!store.exists()) {
        keystore.load(null, null);
    } else {
        InputStream in = new FileInputStream(store);
        keystore.load(in, password);
        in.close();
    }

    return keystore;
}
```

### Save Keystore

 ```java
public static KeyStore save(File store, char[] password, String keystoreType, KeyStore keystore) {
    OutputStream out = new FileOutputStream(store);
    keystore.store(out, password);
    out.close();
}
```

### Credentials

 ```java
public final class PrivateCredential implements Destroyable {
    // X509 certificate chain
    private X509Certificate[] chain;

    // Private key
    private PrivateKey key;

    // Alias
    private String alias;

    X509Certificate[] toX509Certificates(Certificate[] certificates);

    // Get credential from keystore
    PrivateCredential getCredential(KeyStore keystore, String alias, char[] password) {
        PrivateKey key = (PrivateKey) keystore.getKey(alias, password);
        X509Certificate[] chain = toX509Certificates(
            keystore.getCertificateChain(alias));
            
        return new PrivateCredential(chain, key, alias);
    }

    // Get certificate from truststore
    public X509Certificate getCertificate(KeyStore truststore, String alias) {
        Certificate certificate = truststore.getCertificate(alias);
        if (certificate == null) {
            throw new IllegalStateException(...);
        }
        return (X509Certificate) certificate;
    }

    // Encoding a client cert chain
    private byte[][] encodeChain(X509Certificate credential, X509Certificate[] certChain) {
            byte[][] chain = new byte[certChain.length+1][];
            chain[0] = credential.getEncoded();

            for (int i = 0; i < certChain.length; i++) {
                chain[i+1] = certChain[i].getEncoded();
            }

            return chain;
        }

    // Create client certificate from encoded CSR (with certificate chain)
    public byte[][] createClientCertificate(byte[] encodedCsr, ...) {
        PKCS10CertificationRequest csr = new PKCS10CertificationRequest(encodedCSR);

        PrivateCredential credential = getCredential(CA_CERT_ALIAS, caKeyPassword);
        PrivateKey issuerKey = credential.getPrivateKey();
        X509Certificate[] credentialChain = credential.getCertificate();
        X509Certificate issuerCert = credentialChain[0];

        X509Certificate clientCert = createClientCert(id, issuerKey, issuerCert, csr, ...);
        
        return encodeChain(clientCert, credentialChain);
    }
}
```

For more relating to credential management, see [Keytool](Keytool.md)
