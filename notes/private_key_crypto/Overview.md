# Overview

## Table of Contents

- [Overview](#overview)
  - [Table of Contents](#table-of-contents)
  - [Crypto Framework](#crypto-framework)
    - [Attacks](#attacks)
  - [Historical Ciphers](#historical-ciphers)
    - [Caesar Cipher](#caesar-cipher)
    - [Vigenere Cipher](#vigenere-cipher)
  - [Birthday Theorem](#birthday-theorem)
    - [Birthday Attack](#birthday-attack)
    - [Meet in the Middle Attack](#meet-in-the-middle-attack)

## Crypto Framework

There are multiple aspects of crypto, resulting in an arms race between the different components:

- **cryptography**: code making
- **cryptanalysis**: code breaking
- **cryptology**: both

The **random oracle** crypto model gives us an idealization of ciphers and hash functions. This model produces random outputs based on inputs with no correlation between the two. Additionally, for every repeated input, the oracle produces the same output as was generated the first time

**Hash functions**, which are one-way functions, produce a fixed-length random output (the hash) from an input of arbitrary. It should be impossible to reverse engineer the output to determine the input

**Stream ciphers**, which are random generators, produces an indefinite-length output based on a fixed-length input (the key). Pseudorandom number generators are used to accomplish this

**Block ciphers** are random permutations which produce a fixed-length, randomized output of a fixed-length input. They are the idealization of block ciphers and there should be no correlation between the output and the input, but repeated inputs should always yield the same output

The "random" numbers used here are actually pseudorandom due to the limitations of algorithms and hardware

### Attacks

The types of attacks that might impact these cryptographic operations are:

- **known plaintext**: attacker knows pairs of plaintext and ciphertext
- **chosen plaintext**: attacker injects their own plaintext and retrieves the corresponding ciphertext for use in cryptanalysis
- **chosen ciphertext**: the attacker knows a ciphertext and queries information about it to the oracle
- **chosen plaintext/ciphertext**: similar to the previous two; the attacker gets query information about either from the oracle
- **related key**: attacker can query data for values relating to an underlying key data

The objectives of these attacks can be:

- **forgery**: answer to a query an attacker hasn't made
- **key recovery**: determine the underlying key

## Historical Ciphers

### Caesar Cipher

Stream ciphers have long existed, with an important example being the Caesar Cipher. They are easy to break, as seen by the aforementioned Caesar Cipher:

Assume plaintext alphabet $\mathbb{Z}_{26} = \{0,1,2,...,25\}$, i.e. 'A' = 0, 'B' = 1 et cetera. Key K is a letter/number in $\mathbb{Z}_{26}$

Encryption: $y = x + k (\bmod26)$

Decryption: $x = y - k (\bmod26)$

These ciphers are vulnerable to the following:

- **known plaintext**: attacker knows plaintext (x) and ciphertext (y)
- **chosen plaintext**: attacker chooses plaintext
- **chosen ciphertext**: attacker chooses ciphertext

### Vigenere Cipher

This cipher uses poly-alphabetic substitution to randomly shift letters based on their position with the corresponding character at that position in the key:

![vigenere](/notes/assets/private_key_crypto/vigenere.PNG)

This can be cracked if the key length is known, which can be realized through several methodologies

## Birthday Theorem

> "In a set of r randomly chosen people, what is the probability that two of them will have the same birthday?"

The probability of at least two members of a group of $n$ people **not** having the same birthday is $(\frac {364} {365})^{n-1}$. Therefore as n increases, this probability decreases, meaning that the chance of a shared birthday increases

This theorem talks about the probability of collisions of values for any two elements in a population. This quickly becomes likely: if there are $n$ values, then a collision can be expected with $\sqrt n$ samples from the population

- choose $k$ elements, then $\frac {k(k-1)} 2$ pairs of elements
- each pair has a $\frac 1 n$ chance of being a collision
- probability of collision is close to $\frac {k(k-1)} {2n} \approx \frac {k^2} {2n}$
- therefore, probability of collision is 50% when $k = \sqrt n$

If there are $n$-bit values, then there are $2^n$ possible values. This requires $\sqrt{2^n} = 2^{n/2}$ elements before a collision occurs

### Birthday Attack

This attack is predicated on the fact that collisions occur much faster than you would expect

> Ex: financial transactions have a 64 bit key, which implies collisions after $2^{32} = 4$ billion transactions

Eve can insert old messages that she has recorded that are signed with the repeated key

This attack relies on the probability of repetition within a single set of samples

### Meet in the Middle Attack

Unlike a birthday attack, instead of waiting for a key to repeat Eve builds a table of $2^{32}$ different random 64-bit keys with the computed MAC for the start transaction operation. Eve waits for a message with the MAC she has computed to occur

The probability of a collision occurring is 1 in $\frac {2^{64}} {2^{32}} = 1 \textrm{ in } 2^{32}$. Eve in all likelihood has discovered the authentication key for this transaction. This allows her to inject their own new messages

This attack relies on the intersection of two sets, the actual samples the generated values
