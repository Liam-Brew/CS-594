# Protocols

## Table of Contents

- [Protocols](#protocols)
  - [Table of Contents](#table-of-contents)
  - [Encrypting e-mail](#encrypting-e-mail)
    - [PGP](#pgp)
  - [Linking Principals to Keys](#linking-principals-to-keys)
  - [Denning-Sacco Protocol](#denning-sacco-protocol)
    - [Public Key Needham-Schroeder](#public-key-needham-schroeder)
  - [Transport Layer Security (TLS)](#transport-layer-security-tls)
  - [IPsec Network Security](#ipsec-network-security)
  - [Public Key Protocols Problems](#public-key-protocols-problems)

## Encrypting e-mail

### PGP

1. affixes a signature to a message
2. encrypt it with a message key
3. encrypt a message key with the recipient's public key

$$A \rightarrow B: \{K_M\}_B, \{M, sig_A\{h(M)\}\}_{KM}$$

X.400 created a detached signature:

$$A \rightarrow B: \{K_M\}_B, \{M\}_{KM}, sig_A\{h(M)\}_{KM}$$

## Linking Principals to Keys

There are a few methods to do this:

- have system admin physically install keys on machines (SSH, IPSEC)
- set up keys, then exchange a short string out of band to check principal
  - STU-II Bluetooth simple pairing
- certificates: Sam signs Alice's public key and/or signature verification key. Credential certificate can include access permissions
  - $C_A = \textrm{sig}_s\{T_S,L,A,K_A,V_A\}$

## Denning-Sacco Protocol

Needham-Schroeder for key sharing: principals must share private keys with a trusted third party

Denning-Sacco: trusted third party authenticates principals with certificates:

- $A \rightarrow S: A, B$
- $S \rightarrow A: C_A, C_B$
- $A \rightarrow B: C_A, C_B, \{sig_A\{T_A,K_{AB}\}\}_{KB}$

The problem with this is that Bob can masquerade as Alice:

- $A \rightarrow S: A, B$
- $S \rightarrow A: C_A, C_B$
- $A \rightarrow B: C_A, C_B, \{sig_A\{T_A,K_{AB}\}\}_{KB}$
- $B \rightarrow S: B, C$
- $S \rightarrow B: C_B, C_C$
- $B \rightarrow C: C_A, C_A, \{sig_A\{T_A,K_{AB}\}\}_{KC}$

### Public Key Needham-Schroeder

- $A \rightarrow B: \{N_A, A\}_{KB}$
- $B \rightarrow A: \{N_A, N_B\}_{KA}$
- $A \rightarrow B: \{N_B\}_{KB}$

$N_A \oplus N_B$ is a shared key

This is vulnerable to a MitM attack:

- $A \rightarrow C: \{N_A, A\}_{KC}$
- $C \rightarrow B: \{N_A, A\}_{KB}$
- $B \rightarrow C: \{N_A, N_B\}_{KA}$
- $C \rightarrow A: \{N_A, N_B\}_{KA}$
- $A \rightarrow C: \{N_B\}_{KC}$
- $C \rightarrow B: \{N_B\}_{KB}$

The fix for this is to put all names in all messages (**explicitness**)

## Transport Layer Security (TLS)

![tls](/notes/assets/public_key_crypto/tls.PNG)

## IPsec Network Security

![ipsec](/notes/assets/public_key_crypto/ipsec.PNG)

This provides security below the transport layer in the network layer. There are two modes in IPsec:

- **transport**: using message authentication on packet payload to verify the identity of the router
- **tunnel**: encrypting the entire message to make all communications obscure to attackers

Different security associations exist to combine these modes in various methods

Under the **Defense in Depth** principal, protocols can be combined to provide redundant security as well as preventing any vulnerabilities. An example of this can be running SSH over TLS over IPsec

## Public Key Protocols Problems

Public key protocols suffer the following problems:

- man-in-the-middle attacks
- chosen protocol attacks
- math attacks
- data being encrypted (or signed) must be suitably packaged
- many other traps, some extremely obscure

Additionally, there are performance issues suffered by the need for long keys (e.g., 2048 is only 'ok' for RSA). The benefit is that elliptic curve variants can have shorter keys, yet for these the computations are slow, as the difference between squaring and doubling is visible. Therefore for many applications public key cryptography isn't worth it
