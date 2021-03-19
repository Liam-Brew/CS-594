# Advanced Cryptosystems

## Table of Contents

- [Advanced Cryptosystems](#advanced-cryptosystems)
  - [Table of Contents](#table-of-contents)
  - [Threshold Cryptosystems](#threshold-cryptosystems)
  - [Identity-Based Encryption](#identity-based-encryption)
  - [Forward Secure Encryption](#forward-secure-encryption)

## Threshold Cryptosystems

This system uses **shared control** to ensure a minimum of individual entities to approve an action. The secret is divided into n shares, so for $0 \le m \le n$:

- m shares are sufficient to reconstruct the secret (m-1 is the threshold)
- it is infeasible to reconstruct the secret with less than m shares
- this is known as a (m,n)-threshold scheme

This is based on polynomial interpolation:

- m points uniquely identify a polynomial of degree m-1
- encode the secret as a number s ($a_0$ = s below)
- find an m-1 degree polynomial $f(x) = a_0 + a_1x + a_2x^2 + ... + a_{m-1}x^{m-1}$
- distribute n points (x,y) from the polynomial to participants
  - knowledge of m points is enough to identify polynomial and find $s = a_0$

## Identity-Based Encryption

This is meant to help reduce the management overhead of PKI. There are a couple of proposed variations of this system:

- public key is "alice@gmail"
  - no need to look up the recipient certificate
- public key is the "alice@gmail || current-date"
  - short-lived private keys allow revocation and mobility
- credentials: embed user credentials in public key, such as public key = "alice@gmail || data || clearance=secret"
  - Alice can decrypt only if she has clearance on a given date
  - easy to grant and revoke credentials

This system can be implemented using elliptic curves:

- choose prime p (and q), a, b
- some pairs of elliptic curves have 'bilinear pairing', e : $G_1 x G_1 \rightarrow G_2$ such that $e(a.P,b.Q) = e(P,Q)^{ab}$
- parameters:
  - master key $s \in \mathbb{Z_q^*}$
  - arbitrary public point P on $G_1$
  - public key $P_{Pub} = s.P$
  - user public key $g_{ID} = e(ID,P_{pub})$
  - user private key $d_{ID} = s.ID$

To **encrypt** M, choose a random $r \in \mathbb{Z_q}$. The encryption is: 

$$C = (r.P, M \oplus \textrm{hash}(g_{ID}^r)) = (U,V)$$

To **decrypt** (U,V):

$$V \oplus \textrm{hash}(e(d_{ID},U))\\
V \oplus \textrm{hash}(e(dS.ID,r.P))\\
V \oplus \textrm{hash}(e(ID,P)^{s.r})\\
V \oplus \textrm{hash}(e(ID,s.P)^r)\\
V \oplus \textrm{hash}(e(ID,P_{pub})^r)\\
V \oplus \textrm{hash}(g_{ID}^r)\\
V \oplus \textrm{hash}(g_{ID}^r) \oplus \textrm{hash}(g_{ID}^r)\\
M$$

## Forward Secure Encryption

This system is designed to prevent compromise of old traffic by the capture of equipment. There are a few ways to do this:

- Diffie-Helman to create a transient key, then authenticate this
- (US Defense Messaging System): create one-time El Gamal keys using your DSA key and serve them up
- use identity-based scheme to create a "key of the day" for each future day and destroy the corresponding private keys as they expire
