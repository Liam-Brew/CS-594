# RSA

## Table of Contents

- [RSA](#rsa)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Protocol](#protocol)
  - [RSA in Use](#rsa-in-use)
  - [RSA for Secrecy](#rsa-for-secrecy)
  - [RSA for Signing](#rsa-for-signing)
  - [RSA Attacks](#rsa-attacks)
    - [Optimal Asymmetric Encryption Padding (OAEP)](#optimal-asymmetric-encryption-padding-oaep)

## Overview

RSA handles both encryption and decryption in the same algorithm

Alice has a **public encryption key** $KU_A$ and a **private decryption key** $KR_A$

Bob encrypts the message with $KU_A$ and Alice decrypts with $KR_A$

## Protocol

The RSA protocol is as follows:

- encrypt $E_KU(M) = M^E\bmod n$
  - public key KU = (e, n)
- decrypt $D_KR(C) = C^d\bmod n$
  - private key KR = (..., d)
- **key property**: choose e, d such that $(M^e)^d = M\bmod n$
  - find e, d such that ed = kt + 1

This is secure as $d = e^{-1\bmod t$, therefore the attacker needs to know t

For prime p, we know $x^{p-1} = 1 \bmod p for x \in \mathbb{Z}_p^*$

Suppose n = pq where p and q and prime

Find t such that $x^t = 1\bmod n for all x \in \mathbb{Z}_n^*$

- e.g. $\phi(n)$ = Euler totient function
- $\phi(n) = (p-1)(q-1)$

Then $x^{t+1}=x x\bmod n for all x \in \mathbb{Z}_n$

RSA relies on the fact that its very hard to find p and q even when giving n due to them being very large prime numbers

## RSA in Use

1. choose two primes p and q, define n = pq
2. choose e, d such that $d = e^{-1}, ed = 1\bmod t$
3. encrypt $E_{KU}(M) = M^e\bmod n$
   1. public key KU = (e, n)
4. decrypt $D_{KR}(C) = C^d\bmod n$
   1. private key KR = (p, q, t, d)
5. then $(M^e)^d = M^{ed} = M^{kt+1} = M$
   1. for t remember that $ed = 1\bmod t$

## RSA for Secrecy

These operations are expensive, so a session key can be used to reduce costs

- Bob encrypts message with secret session key K, and encrypts this secret key with KU
- Alice decrypts K with KR and decrypts the message with K

This uses private-key block ciphers as they are much cheaper to operate. Under this approach, **public key crypto is used to share the key so that private key crypto can carry the majority of the actual content**

## RSA for Signing

Bob signs a message with KR. Alice authenticates with KU and compares with the message she received

- sign $D_{KR}(M) = M^d\bmod n$
- authenticate $E_{KU}(C) = C^e\bmod n$
- then $(M^d)^e = M^{de} = M \mod n$

Hashed signing can be used to reduce costs, as signing with ciphertext can be expensive

- Bob signs hash of message with KR
- Alice "decrypts" with KU and compares hash with the hash message of the message she computes

## RSA Attacks

Given (e, n), Eve needs to find p, q, t, d. To do this she needs to factor n = pq. This uses **factorization**, which for a large enough n we think is a hard problem. If Eve knows t ord d, she can figure out p, q

As RSA can be used for both encryption and signing, its use can be dangerous

- decryption = signature, so "sign this to prove who you are" is really dangerous
- never use the same key pair for encryption and signing

Multiplicative attacks also threaten RSA

- M3 = M1\*M2 = S3 = S1*S2
- important to hash messages before signature

### Optimal Asymmetric Encryption Padding (OAEP)

Problem: need to add some randomness to encryption, otherwise hacker pre-computes ciphertext for known plaintext

Process message M and nonce N to X, Y as:

- $X= M\oplus \textrm{Hash}(N)$
- $Y= N\oplus \textrm{Hash}(X)$

Do this before encrypting to avoid multiplicative attacks

Recipient:

- $Y\oplus \textrm{Hash}(X) = N\oplus \textrm{Hash}(X)\oplus\textrm{Hash}(X) = N$
- $X\oplus \textrm{Hash}(N) = M\oplus \textrm{Hash}N)\oplus\textrm{Hash}(N) = M$