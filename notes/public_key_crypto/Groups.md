# Groups

## Table of Contents

- [Groups](#groups)
  - [Table of Contents](#table-of-contents)
  - [Additive Groups](#additive-groups)
  - [Multiplicative Groups](#multiplicative-groups)
  - [Elliptic Curve Group](#elliptic-curve-group)
    - [Adding Distinct Points P and Q](#adding-distinct-points-p-and-q)
    - [Adding Points P and -P](#adding-points-p-and--p)
    - [Doubling the Point P](#doubling-the-point-p)
    - [Benefits of Elliptic Curve Cryptography](#benefits-of-elliptic-curve-cryptography)

## Additive Groups

A set of elements, e.g. $G_7 = \{0,1,2,3,4,5,6\}$

A binary operation, e.g. + $(\bmod 7)$

- $2+4 = 6\bmod 7$
- $2+5 = 0\bmod 7$

A unit for that operation, e.g. 0

- 0 + a = a + 0 = a

Every element a has an inverse -a:

- a + (-a) = 0
- e.g. -3 = 4 since 3 + 4 = 0

Element g is a generator for the group if $a = g + ... + g for any element a

- e.g. 1 is a generator for this group: 3 = 1 + 1 + 1 etc.

## Multiplicative Groups

These are more important than additive groups for crypto

A set of elements, e.g. $\mathbb{Z}^*=\{1,2,3,4,5,6\}$ (notice how 0 is left out)

A binary operation, e.g. * $(\bmod 7)$

- $2 * 3 = 6$
- $2 * 4 = 8 \bmod 7 = 1$
- $6 * 6 = 35 \bmod 7 = 1$

A unit for that operation, e.g. 1

- 1 \* a = a * 1 = a

A **multiplicative inverse** $a^{-1}$ for every element a:

- $a * (a^-1) = 1$
- e.g. $2^{-1} = 4$

Element g is a **generator** for the group if:

- $a = g^*=...^*g=g^n$, some n for any element a

g is called a **primitive element**. An example of a generator for this group is 3:

- $3^0 = 1$
- $3^1 = 3$
- $3^2 = 9\bmod 7 = 2$
- $3^3 = 27\bmod 7 = 6$
- ...

The **order** of g is the smallest q > 0 such that $g^q = 1$

If g is a generator for $\mathbb{Z}_p^*$, then its order is p-1

So  $\mathbb{Z}_p^* = \{g^0,g^1,g^2,...,g^{p-2}\}$, e.g. $\mathbb{Z}_7^* = \{3^0,3^1,3^2,3^3,3^4,3^5\}$

For any element $a \in \mathbb{Z}_p^*$ we have $a = g^x$ for some x

Other elements generate **subgroups**:

- 2 generates {1,2,4}
- 6 generates {1,6}

The size of these subgroups (order of their generators) are divisors of p-1

## Elliptic Curve Group

$$y^2 = x^3 + ax + b$$

Let x, y, a, b be real numbers

If $4a^3 + 27b^2 \ne 0$, an additive group can be formed out of the points on a curve. The **infinity point** gives the identity of that curve

Elliptic curve arithmetic:

- points on a curve $y^2 = x^3 + ax + b$
- addition P+Q
- identity O (point at infinity)
- doubling 2P
- scalar multiplication: 23P = 2(2(2(2P) + P) + P) + P

Elliptic curve cryptography differs from traditional cryptography by the following:

| Crypto over $Z_P^*$                     | Crypto over Elliptic Curve                                            |
| --------------------------------------- | --------------------------------------------------------------------- |
| elements 1,2,...,p-1                    | points on a curve $y^2 = x^3 +ax+b$ (e.g. over a finite field $F_P$) |
| operation: multiplication a . b         | operation: addition P+Q                                               |
| unit (identity) 1                       | unit (identity) O point at infinity                                   |
| inverse: $a^{-1}$; a . $a^{-1} = 1$       | inverse: -P; P + (-P) = 0                                             |
| exponentiation: $a^n = a * a * ... * a$ | scalar multiplication: 23P = 2(2(2(2P) + P) + P) + P                  |
| DLP: given $a^n$, compute n             | ECDLP: given kP, compute k|

### Adding Distinct Points P and Q

![adding_distinct_elliptic_curves](/notes/assets/public_key_crypto/adding_distinct_elliptic_curves.PNG)

### Adding Points P and -P

![adding_negatives_elliptic_curves](/notes/assets/public_key_crypto/adding_negatives_elliptic_curves.PNG)

### Doubling the Point P

![doubling_elliptic_curves](/notes/assets/public_key_crypto/doubling_elliptic_curves.PNG)

### Benefits of Elliptic Curve Cryptography

The benefits of this methodology deal with its smaller parameter sizes. This allows faster encryption and signature operations. For identity-based encryption, it has the same efficiency as El Gamal on $Z_P^*$
