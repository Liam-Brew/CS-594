# Hash Functions

## Table of Contents

- [Hash Functions](#hash-functions)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Simple (Insecure) Hash Function](#simple-insecure-hash-function)
  - [Feedforward Mode (Davies-Meyer)](#feedforward-mode-davies-meyer)
    - [Block Cipher vs. Hash Function](#block-cipher-vs-hash-function)

## Overview

Hash functions take an arbitrary size input and distills it down to a fixed size. Some uses are hashing a message before a digital signature and computing a MAC. Desirable properties of hash functions are:

- **collision resistance**: no M$_1$, M$_2$ exist such that h(M$_1$) = h(M$_2$)
  - due to the birthday theorem this is impossible even for perfect hash functions: collisions occur in 2$^{n/2}$ trials
- **preimage resistance**: given X, you can't find M such that h(M) = x
- **second preimage resistance**: given M$_1$, can't find M$_2$ such that h(M$_1$) = h(M$_2$)

Hash functions must pick an output size (n) large enough to avoid collisions, with some examples being:

- MD5: N=128; N$^{64}$ work units
- SHA1: N = 160; N$^{80}$ work units

## Simple (Insecure) Hash Function

![simple_hash](/notes/assets/private_key_crypto/simple_hash.PNG)

In this example, assume a block cipher E$_k$ such as AES. The hash function:

- breaks message m into 128 bit block m$_1$, ..., m$_k$
- define H $_0$ and $H_i$ = $E(H_{i-1}+m_i)$
- hash is H$_k$

This can be broken by the following attack:

- pick message m that splits into m$_1$ and m$_2$
- define $H_1 = E_0(m_1)$, $H_2 = E_0(H_1+m_2)$
- define $m_1' = H_1 + m_2 and m_2' = H_2 + m_2 + H_1$
- Let m' be a message that splits into $m_1'$ and $m_1'$
- then:

$$E_0(E_0(m_1') + m_2') \\
= E_0(E_0(H_1+m_2)+H_2+m_2+H_1)\\
= E_0(H_2+H_2+m_2+H_1)\\
= E_0(m_2+H_1)\\
= H_2$$

## Feedforward Mode (Davies-Meyer)

Given a block cipher, $H_1 := E_{Pi}(H_{i-1})+H_{i-1}$

Each step of the cipher takes as input the hash resulting from the previous step in the chain and uses the current plaintext block as its key. This key should be larger than 64 bits

### Block Cipher vs. Hash Function

Block ciphers only need to be a pseudorandom permutation. Keys are implicit, so key schedules can therefore be weak. This is resistant against chosen plaintext/ciphertext attacks

Alternatively, crypto hash functions must be random. The key schedule must be perfect and resistant against related-key attacks
