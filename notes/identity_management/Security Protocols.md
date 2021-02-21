# Security Protocols

## Table of Contents

- [Security Protocols](#security-protocols)
  - [Table of Contents](#table-of-contents)
  - [Protocol Examples](#protocol-examples)
    - [Car Unlocking](#car-unlocking)
    - [Two Factor Authentication](#two-factor-authentication)
  - [Nonce](#nonce)
    - [Issues](#issues)
  - [Attacks on Protocols](#attacks-on-protocols)
    - [Man-in-the-middle Attack](#man-in-the-middle-attack)
    - [Chosen Protocol Attacks](#chosen-protocol-attacks)

## Protocol Examples

### Car Unlocking

**Static**:
$T \rightarrow E: KT$
Transponder T sends the secret KT to the engine E

**Non-interactive**:

$T \rightarrow E: T, \{T,N\}_{KT}$

Transponder T sends to the engine E its own identity, as well as its identity and the nonce N encrypted under secret key $KT$. $N$ is a nonce

Operate under the assumption of secret-key cryptography, so $\{T,N\}_{KT}$ signifies:

- **confidentiality**: only $T$ and $E$ know $N$
- **integrity**: $E$ knows message came from $T$

**Interactive (challenge-response)**:

$E \rightarrow T: N$
$T \rightarrow E: \{T,N\}_{KT}$

### Two Factor Authentication

$S \rightarrow U: N$
$U \rightarrow P: N, \textrm{PIN}$
$P \rightarrow U: \{N, \textrm{PIN}\}_{KP}$
$U \rightarrow S: \{N, \textrm{PIN}\}_{KP}$

## Nonce

**nonce**: 'number used once'. Used to avoid replay attacks by proving message freshness. A nonce may be:

- random number
- counter
- random challenge from third party
- timestamp

### Issues

- nonce can be too short and wrap around
- issues with remembering due to randomness
- counters and timestamps can lose sync leading to DoS attacks
- weak ciphers

## Attacks on Protocols

### Man-in-the-middle Attack

**Example**: Identify Friend or Foe (IFF)

Basic idea is that a fighter challenges a bomber

$F \rightarrow B: N$
$B \rightarrow F: \{N\}_K$

But what if the bomber reflects the challenge back at the fighter's wingman? This allows the enemy bomber to impersonate a friendly aircraft

$F \rightarrow B: N$
$B \rightarrow F: N$
$F \rightarrow B: \{N\}_K$
$B \rightarrow F: \{N\}_K$

### Chosen Protocol Attacks

Using crypto keys in more than one application is dangerous. The below shows dangers associated with using keys for both age identification as well as purchases

![chosen_protocol](/notes/assets/identity_management/chosen_protocol.PNG)
