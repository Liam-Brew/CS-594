# Block Ciphers

## Table of Contents

- [Block Ciphers](#block-ciphers)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Block Cipher Ideas](#block-cipher-ideas)
  - [Data Encryption Standard (DES)](#data-encryption-standard-des)
    - [Triple DES (3DES)](#triple-des-3des)
  - [Advanced Encryption Standard (AES)](#advanced-encryption-standard-aes)
  - [Block Cipher Modes](#block-cipher-modes)
    - [Electronic Code Book (ECB)](#electronic-code-book-ecb)
    - [Cipher Block Chaining (CBC)](#cipher-block-chaining-cbc)
    - [CBC-MAC](#cbc-mac)
    - [Output Feedback Mode Stream Cipher (OFB)](#output-feedback-mode-stream-cipher-ofb)
    - [Counter Mode (CTR)](#counter-mode-ctr)
    - [Galois Counter Mode (GCM)](#galois-counter-mode-gcm)
  - [Attacks on Block Ciphers](#attacks-on-block-ciphers)
    - [Linear Cryptanalysis](#linear-cryptanalysis)
    - [Differential Cryptanalysis](#differential-cryptanalysis)
    - [Birthday Attack on CBC-MAC](#birthday-attack-on-cbc-mac)

## Overview

Block ciphers are used to encrypt a fixed-length block of bits

![block_ciphers](/notes/assets/private_key_crypto/block_ciphers.PNG)

## Block Cipher Ideas

The idea here deals with operating the cipher on blocks of text instead of characters, such as N=64 for DES and N = 128 for AES

Shannon's theory on block ciphers state the need for two operations, **permutation** and **substitution**, as well as two key principles:

- **confusion**: make the relationship between ciphertext and the key as complex as possible
- **diffusion**: small changes to the input should diffuse completely through output
  - 1 input bit flip on average causes 1/2 output bits to flip

![sboxes](/notes/assets/private_key_crypto/sboxes.PNG)

Each output bit depends on combining the input and the key in a complex way. Invertible lookup tables (S-boxes) serve to approximate a random permutation. These tables are modified after every iteration to promote randomness

Block sizes are based on the birthday theorem due to the fact that collisions can be seen in $2^{n/2}$ inputs. As a result of this, protocols such as DES (64 bits) have been discontinued due to their lack of security (collisions found in 4 billion inputs)

The number of rounds to run through depends on the speed of diffusion of input through the cipher. The more iterations, the more complex the ciphertext. Simple permutations based on S-boxes are slow and easy to guess, whereas linear transformation (each input bit on each round is a XOR of some output bits from the previous round) is more efficient

Each principal block cipher has ideal use cases, pros, and cons:

- 3DES
  - extends key length
  - slow and should only be used in legacy systems
- AES
  - elegant and efficient
  - recent related-key attacks on AES-256
    - key schedule in AES-256 is weaker than AES-128
      - proposed solution is to add more rounds
- Serpent
  - 32 4-bit S-boxes wide, 32 rounds, 128 bit block, 256-bit key: "built like a tank"

## Data Encryption Standard (DES)

This encryption standard was popular in the 1970s for banking, DES has since been proven vulnerable especially due to its 56 bit key

The phases of DES are:

1. an initial permutation (IP)
2. key transformation
3. 16 rounds of
   1. expansion permutation of input (Avalanche Effect)
      1. 32 bits are expanded to 48 bits, thus a single bit affects 2 substitutions
   2. S-box substitution
   3. P-box permutation (diffusion)
4. final permutation (IP$^{-1}$)

DES was withdrawn due to hardware advancements allowing for it to be cracked within ~9 hours, with banks moving to 3DES

### Triple DES (3DES)

In 3DES, DES is ran three times to improve security. There are two different modes:

- EEE mode: 3 different keys
- EDE mode: 2 different keys (legacy support)
  - EDDE with K$_1$ = K$_3$ is very popular

## Advanced Encryption Standard (AES)

AES has a 128-bit block arranged as 16 bytes. After each iteration, bytes are shuffled as below. The key bytes are XORed, then bytewise S-box $S(x) = \frac 1 x + b$ in GF$(2^8)$

![aes_shuffle](/notes/assets/private_key_crypto/aes_shuffle.PNG)

Key and state bytes are arranged in rectangular arrays:

![aes_arrays](/notes/assets/private_key_crypto/aes_arrays.PNG)

To achieve diffusion, rows are shifted and columns are mixed:

![aes_row_shift](/notes/assets/private_key_crypto/aes_row_shift.PNG)
![aes_column_mix](/notes/assets/private_key_crypto/aes_column_mix.PNG)

Next, **whitening** is performed by XORing the key with plaintext or ciphertext. This is done to prevent the collection of plaintext-ciphertext pairs:

![aes_whitening](/notes/assets/private_key_crypto/aes_whitening.PNG)

Finally, nonlinear **byte substitution** is performed to individual bytes of the state to produce the output:

![aes_bytesub](/notes/assets/private_key_crypto/aes_bytesub.PNG)

## Block Cipher Modes

### Electronic Code Book (ECB)

This mode encrypts a block at a time. This must make use of padding to ensure correct sizes. This should only be used for small amounts of data, but even then should not be used as patterns can still be fairly obvious

![ecb](/notes/assets/private_key_crypto/ecb.PNG)

### Cipher Block Chaining (CBC)

This mode is better suited for bulk encryption. The resulting ciphertext from one block is used in the encryption of the next block. This better hides patterns, but is still vulnerable to "cut and splice" attacks if the attacker knows some plaintext

![cbc](/notes/assets/private_key_crypto/cbc.PNG)

For the first plaintext block, an **initialization vector (IV)** is used. To ensure security, this value must not be repeated. This IV value can be generated using a few different strategies, with two of the best options being:

- **random IV**: add a random starting ciphertext block C$_0$
- **nonce-generated IV**: use message number as a nonce and encrypt it for IV. This relies on reliable message delivery as the nonce is implicit

Note that CBC is intended to secure **secrecy**, not integrity, and therefore should not be used when the message is at risk of modification

### CBC-MAC

This is very similar to CBC, with the difference being that the ciphertext is discarded. The last output ciphertext is the **message authentication code (MAC)**. The receiver has access to the messages and the MAC. Using the same IV and algorithm, the receiver examines the sent message and sees if the MAC they generate is the same as the one sent

![cbc_mac](/notes/assets/private_key_crypto/cbc_mac.PNG)

Note that when both **confidentiality** (CBC) and **integrity** (CBC-MAC) are required, different keys should be used for encrypting and authenticating. Never output the intermediate ciphertext blocks in the computation of the MAC

When MACing messages with different lengths, use CMAC

### Output Feedback Mode Stream Cipher (OFB)

In this mode, a keystream is generated via CBC:

![ofb](/notes/assets/private_key_crypto/ofb.PNG)

Due to the birthday theorem, the keystream will begin to repeat after 2$^{n/2}$ blocks. Therefore, a high n value is required

### Counter Mode (CTR)

![ctr](/notes/assets/private_key_crypto/ctr.PNG)

This is an alternative method of generating a keystream. A random nonce and a counter is used. The advantage of this over OFB is that it is easy to parallelize: $K_i = E_k(\textrm{Nonce } || i)$

A common problem for this is that applications fail to ensure that the nonce is random

### Galois Counter Mode (GCM)

This mode encrypts an authenticator tag for MAC. Unlike CBC or CBC-MAC, one encryption per block takes place and the mode is parallelisable

This mode is used in SSH and IPsec and is defined for 128 bit ciphers, therefore only giving it 64 bit security. Additionally, it is not suited for short tags or long messages

## Attacks on Block Ciphers

### Linear Cryptanalysis

This is a method to attack block ciphers. This features the building of a probabilistic linear approximation to S-boxes by XORing certain plaintext and ciphertext bits. These are combined to approximate the whole cipher, then the guessing of key bits based on known plaintext or ciphertext

### Differential Cryptanalysis

This is based on the examination of how differences in input affect differences in output. This can be determined through behaving non-random behavior in the cipher

Assuming that lots of plaintext and ciphertext pairs are known, then the difference $d_P = P_1 \oplus P_2$, $d_C = C_1 \oplus C_2$. The distribution of $d_C$ values given $d_P$ may reveal the key, or at least allow deductions about parts of it to the point where a brute-force would reveal the rest

### Birthday Attack on CBC-MAC

An example of a birthday attack on CBC-MAC:

- Eve records MAC values until a collision occurs
  - yields $2^{64}$ values with a 128 bit block cipher
- CBC-MAC has the property: if MAC$_k$(a) = MAC$_k$(b), then MAC$_k$(a || c) = MAC$_k$(b || c)
- Eve gets Bob to authenticate M$_k$(a || c)
- Eve sends this code with b || c to Alice
