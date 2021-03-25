# JCE

## Table of Contents

- [JCE](#jce)
  - [Table of Contents](#table-of-contents)
  - [Symmetric Key Crypto API](#symmetric-key-crypto-api)
    - [SecretKeySpec](#secretkeyspec)
    - [Cipher](#cipher)
  - [Useful Abstractions](#useful-abstractions)
    - [(Internal) Ciphertext](#internal-ciphertext)
    - [(External) Ciphertext](#external-ciphertext)
    - [Utilities](#utilities)
    - [Symmetric Key Encryption](#symmetric-key-encryption)
    - [Generating Random Keys](#generating-random-keys)
    - [Message Digests and MACs](#message-digests-and-macs)
  - [Public Key Crypto API](#public-key-crypto-api)
    - [Elements](#elements)
    - [RSA](#rsa)
    - [Key Wrapping](#key-wrapping)
    - [Object Description with ASN.1](#object-description-with-asn1)
    - [Certificates](#certificates)
    - [X509 Extensions](#x509-extensions)

## Symmetric Key Crypto API

### SecretKeySpec

- symmetric key spec (AES, 3DES, etc.)
- doesn't enforce good choice of key

```java
public SecretKey fromBytes(byte[] keyBytes) {
    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

    SecretKeyFactory factory = SecretKeyFactory.getInstance("AES", "BC");
    
    return factory.generateSecret(key); 
}
```

### Cipher

- factory for symmetric key encryption

```java
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
cipher.init(ENCRYPT_MODE, key);

int ctlen = cipher.update(input, 0, input.length, cipherText, 0);

ctlen += cipher.doFinal(cipherText, ctlen);
```

Some operations of ciphers are:

- ```getInstance()```
  - type of algorithm:
    - name of algorithm("AES")
    - mode of use ("ECB", "CBC")
    - type of padding ("No padding", "PKCS7Padding")
  - provider ("Sun" or "BC")
- ```init()```
  - encryption
  - decryption
  - key wrapping
- ```update()```
  - processing step (incremental encryption)
  - input buffer, starting offset, length of next read
  - output buffer, starting offset
  - returns number of bytes output
    - ```int ctlen = cipher.update(input, 0, input.length, cipherText, 0)```;
- ```doFinal()```
  - write remaining update

## Useful Abstractions

### (Internal) Ciphertext

In code, the buffer containing the ciphertext is grouped together with its length and initialization vector

```java
public class IntCipherText {
    /*
    * randBytes may be salted (for hashed password) or IV (for encrypted data)
    */
    public final byte[] cipherText;
    public final int ctLength;
    public final byte[] randBytes;

    /* Passes ciphertext data into the ciphertext construction */
    public CipherText external() {
        return new CipherText(this);
    }

    public static String toString(IntCipherText enc) {
        return new CipherText(enc).toString;
    }
}
```

### (External) Ciphertext

This externalizes the previously defined data

```java
public class CipherText {
    private byte[] content;

    public CipherText(byte[] ciphertext, int ctlen, byte[] iv) {
        ByteArrayOutputStream bos = ...;
        DataOutputStream os = new DataOutputStream(bos);
        /* write IV, ctlen, ciphertext to os... */
        content = bos.toByteArray();
        os.close();
    }

    public IntCipherText internal() {
        /* use ByteArrayInputStream and DataInputStream */
    }

    public String toString() {
        /* base64 encoding of content */
    }
}
```

### Utilities

```java
public static byte[] getRandomBytes(int nbytes) {
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

    byte[] salt = new byte[nbytes];
    random.nextBytes(salt);
    return salt;
}

public static Cipher getEncryptCipher(SecretKey key, byte[] iv) {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    IvParameterSpec ivParams = new IvParameterSpec(iv);
    cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
}
```

### Symmetric Key Encryption

```java
public static IntCipherText encrypt(SecretKey key, byte[] plainText, int ptLength) {
    byte[] iv = getRandomBytes(16);
    Cipher cipher = genEncryptCipher(key, iv);

    byte[] cipherText = new byte[cipher.getOutputSize(ptLength)];

    int ctLength = cipher.update(plainText, 0, ptLength, cipherText, 0);
    ctLength += cipher.doFinal(cipherText, ctLength);
    return new IntCipherText(cipherText, ctLength, iv);
}
```

### Generating Random Keys

- ```Key``` interface:
  - ```getAlgorithm()```
  - ```getEncoded()```
  - ```getFormatted()```
- ```KeyGenerator``` class:
  - getInstance() provider method
  - ```Init()``` e.g. set key size
  - ```generateKey()```

### Message Digests and MACs

- ```getInstance()```
  - returns algorithm, e.g. SHA-1, SHA-512 etc.
- ```update()```
  - processing step (incremental hash)
- ```digest()```
  - final digest value

The following is an example of the use of digests and MACs:

```java
public static byte[] hash(byte[] plainText) {
    MessageDigest digest = MessageDigest.getInstance("SHA-512");
    digest.reset();

    byte[] hash = digest.digest(plainText);
    return hash;
}

public static byte[] mac(byte[] key, byte[] plainText) {
    MAC mac = Mac.getInstance("HmacSHA1");
    SecretKeySpec secret = new SecretKeySpec(key, mac.getAlgorithm());
    mac.init(secret);

    byte[] digest = mac.doFinal(plainText);
    return digest;
}
```

## Public Key Crypto API

### Elements

Interfaces: ```PublicKey```, ```PrivateKey```

```KeyFactory``` class

```KeyPair``` class: placeholder for public and private keys

```KeyPairGenerator```:

- ```getInstance()```: factory
- ```initialize()```: e.g. key size
- ```generateKeyPair()```

Example:

 ```java
SecureRandom random = new SecureRandom();

KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

generator.initialize(2048, random);

KeyPair pair = generator.generateKeyPair();
PublicKey pubKey = pair.getPublic();
PrivateKey privKey = pair.getPrivate();
```

### RSA

```RSAPubKeySpec``` (class)

- modulus
- public exponent

```RSAPublicKey``` (interface)

```RSAPrivateKeySpec``` and ```RSAPrivateKey```

```RSAKeyGenParameterSpec```

Example of setting up RSA keys:

```java
public RSAKeyGenParameterSpec RSA_KEY_SPECS = new RSAKeyGenParameterSpec(2048,RSAKeyGenParameterSpec.F4);

RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
        new BigInteger("d46f473a2d746537de2056ae3092c451", 16),
        new BigInteger("11", 16)
);
RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(
        new BigInteger("d46f473a2d746537de2056ae3092c451", 16),
        new BigInteger("57791d5430d593164082036ad8b29fb1", 16)
);

RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
```

Example of generating a public key from a corresponding private key:

```java
public static PublicKey fromPrivateKey(PrivateKey privateKey) {
    BigInteger exponent;
    BigInteger modulus;
    if (privateKey instanceof RSAPrivateKey) {
        modulus = ((RSAPrivateKey) privateKey).getModulus();
        if (privateKey instanceof RSAPrivateCrtKey) {
            exponent = ((RSAPrivateCrtKey) privateKey).getPublicExponent();
        } else {
            // Works just as well, assuming exponent is always 65537
            exponent = RSA_KEY_SPECS.getPublicExponent();
        }
    }
    
    RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePublic(publicKeySpec);
}
```

### Key Wrapping

This process encrypts a key with another key. It uses a MAC for integrity. An example of this follows:

```java
KeyPair keyPair;
Key sessionKey;

Cipher cipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA512AndMGF1Padding", "BC");

// Wrap session key with RSA public key
cipher.init(Cipher.WRAP_MODE, keyPair.getPublic());
byte[] wrappedKey = cipher.wrap(sessionKey);

// Unwrap session key with RSA private key
cipher.init(Cipher.UNWRAP_MODE, keyPair.getPrivate());
Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
```

### Object Description with ASN.1

**Abstract Syntax Notation (ASN.1)**: language for describing metadata. It is used for encoding X509 certificates

**Object Identifier (OID)**: globally unique structured name

**Module**: collection of type definitions

**Tagging**: type of tags on data

- built-in or application defined
- explicit: type info given by data object
- implicit: tag value implicit from context

Encoding rules are as follow:

- BER: basic encoding rules (X509 certificates)
- DER: distinguished encoding rules (X509 certificates)
  - PEM: base64-encoded version of DER
- CER: canonical encoding rules
- PER: packet encoding rules
- XER: XML encoding rules

```EncryptedPrivateKeyInfo```:

- ```getAlgParameters()```: algorithm identifier
- ```getKeySpec()```: key size
- ```getEncryptedData()```: ciphertext
- ```getEncoded()```: byte array representation

### Certificates

**Distinguished names**: defined as a name structure for X.500 directory structure. They are comma-separated name-value pairs:

- CN: common name
- OU: organizational unit name
- O: organization name
- C: country
- L: locality name
- ST: state or province name

```X500Principal``` class:

- ```getEncoded()```: DER encoding
- ```getName()```

**Digitally signed public key**: authenticate the identity of the certificate holder

```Certificate``` class:

- ```getType()```: "X.509"
- ```getPublicKey()```
- ```verify()```: check signature
- ```getEncoded()```

```X509Certificate``` class:

- ```getVersion()```: v1 (self-signed) or v3
- ```getTBSCertificate()```: returns the input data used in calculating the signature
- ```getSerialNumber()```: used to prevent replay attacks
- ```getIssuerX500Principal()```: DN and serial number (should) uniquely identify the certificate
- ```getNotBefore()```: don't use this certificate before this date
- ```getNotAfter()```: expiration date of the certificate
- ```checkValidity()```: checks if the certificate is expired
- ```getSubjectX500Principal()```: get the distinguished name of the key owner
- ```getSignature()```
- ```getSigAlgParams()```
- ```getSigAlgName()```

### X509 Extensions

These extensions add additional metadata to certificates. They were introduced in X509 v3. They provide for:

- key usage
  - digital signature
  - non-repudiation
  - key/data encipherment
- subject alternative names: allow for renaming a service (e.g. renaming a service hosted on AWS from the Amazon DNS)
  - DNS name
  - IP address
  - URI
- basic constraints
  - true: certificate can be used to sign other certificates
  - false: end-entity certificate only
- extended key usage
  - SSL/TLS server/client authentication
  - signing executable code
  - email encryption and signing
  - creating timestamps
