# Public Key Crypto

## Table of Contents

- [Public Key Crypto](#public-key-crypto)
  - [Table of Contents](#table-of-contents)
  - [Key Agreement in Asymmetric (Public) Key Crypto](#key-agreement-in-asymmetric-public-key-crypto)
  - [Diffie-Helman Key Agreement](#diffie-helman-key-agreement)
    - [Attacks](#attacks)
  - [El Gamal Encryption](#el-gamal-encryption)
  - [El Gamal Signing](#el-gamal-signing)
    - [Digital Signature Standard (DSS)](#digital-signature-standard-dss)

## Key Agreement in Asymmetric (Public) Key Crypto

Key sharing in asymmetric crypto is designed to allow keys to be shared without any setup in advance (such as a third party found in private key crypto)

There is now two keys: KR (private) and KU (public):

- sender sends their KU to receiver: $\textrm{KR}_B \rightarrow A$
- receiver sends their KU to sender: $\textrm{KR}_A \rightarrow B$
- sender and receiver now compute the same keys: $K = DH(KR_B,KU_A)$
- sender sends receiver the data encrypted with this shared key: $E_k(M)$
- receiver decrypts using their local copy of the shared key: $D_k(E_k(M)) = M$

This is a high-level example of Diffie-Helman

## Diffie-Helman Key Agreement

Protocol:

- choose a large prime p and a generator g for $Z_p^*$
- Bob chooses a random $b \in Z_p^*$ and computes $x = g^b\bmod p$
- Bob sends x to Alice
- Alice chooses a random $a \in Z_p^*$ and computes $y = g^a\bmod p$
- Alice sneds y to Bob
- Alice computes $K_a = x^a$
- Bob computes $K_b = y^b$

The key agreement is represented by:

$$K_a = x^a = (g^b)^a = g^{ba} = g^{ab} = (g^a)^b = y^b = K_b\bmod p$$

This allows Bob alice to agree on the same key K through hidden keys

### Attacks

Eve can see $y = g^a$ and $x = g^b$. To compute the private parts a and b, she needs to compute $log_gy$ and $log_gx$, wherein $log_g(g^w) = w$

**Discrete logarithms**: if p is prime and g is primitive, we think these are hard problems. Diffie-Helman is based on this problem making it too difficult for attackers to crack the encryption

To ensure security, large key sizes (2056+) are required. However, large keys result in very expensive calculations, more so than block ciphers

## El Gamal Encryption

El Gamal is Diffie-Helman with long term keys:

- Alice has a private key $KR_A = a$ and a public key $KU_A = g^a$
- Bob has a private key $KR_B = b$ and a public key $KU_B = g^b$

Bob looks up $KU_A$ and makes the long-term shared key $(KU_A)^b = g^{ab} =\ (KU_B)^a$

El Gamal combines Diffie-Helman with a transient private key, instead of always basing it on the same keys time after time

Protocol:

- Bob chooses random $0<k<1$
  - computes $K = (KU_A)^k = g^{ak}\bmod p$
  - encrypts M as $(C_1,C_2) = (g^k, M*K)$
- Alice receives $(C_1,C_2)$
  - computes $K = C_1^a = (g^k)^a = g^{ka}\bmod p$
  - decrypts by $C_2*K^{-1} = M*K*K^{-1} = M$

## El Gamal Signing

Different than El Gamal Encryption. This is a signature trick, given:

- private key $KR_A = a$ and public key $KU_A = g^a$
- transient private key k and transient public key $r = g^k$
- form the private equation $r*a + s*k = \textrm{Hash}(M)$, i.e. $s = (\textrm{Hash}(M) - r*a)k^{-1}\bmod p$

The digital signature on M is (r,s), with the signature verification being $g^{(ra + sk)} = g^{\textrm{Hash}(M)}$

In summary:

$$g^{(ra + sk)} = g^{ra}g^{sk} = (g^a)^r(g^k)^s = (KU_A)^r * r^s = g^{\textrm{Hash}(M)}$$

### Digital Signature Standard (DSS)

Based on El Gamal with a few technical fixes

- p: prime of 1024 bits
- q: prime dividing p-1
- g: element of order q in the integers mod p
  
Signature on m is: (r,s) such that

$$r = (g^k\bmod p)\bmod q \\
\textrm{Hash}(M) = r*a + s*k$$

The only known vulnerability is choosing $q = \textrm{Hash}(M_1) - \textrm{Hash}(M_2)$
