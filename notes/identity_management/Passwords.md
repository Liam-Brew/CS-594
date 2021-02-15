# Passwords

## Table of Contents

- [Passwords](#passwords)
  - [Table of Contents](#table-of-contents)
  - [Authentication](#authentication)
  - [Social Engineering](#social-engineering)
  - [User Authentication with Passwords](#user-authentication-with-passwords)
  - [Basic Password Protocol](#basic-password-protocol)
  - [Hashed Password Protocol](#hashed-password-protocol)
  - [Offline Dictionary Attacks](#offline-dictionary-attacks)
  - [Batch Offline Dictionary Attacks](#batch-offline-dictionary-attacks)
  - [Preventing Batch Dictionary Attacks](#preventing-batch-dictionary-attacks)
  - [Further Defenses](#further-defenses)
  - [The Common Password Problem](#the-common-password-problem)

## Authentication

Authentication is the process of binding an identity to a subject. There are three potential options:

1. something you have (e.g. a token or OTP)
2. something you know (e.g. a password)
3. something you are (e.g. a fingerprint or iris)

Rules of passwords are: 

- length
- composition (such as digits or special characters)
- dictionary membership
- don't write it down
- don't share it
- change it often
- don't re-use across sites

Usability issues with passwords are as follow:

- reliable password entry
- remembering the password
  - naive password choice
  - user abilities and training
  - design errors

Operational issues with passwords are as follow: 

- failure to reset default passwords
- storing passwords in clear text
- using TLS/SSL to transmit passwords
- rate-limit password guessing

## Social Engineering

- use a plausible story, e.g. "what's your PIN so I can cancel your card?"
- hackers impersonating IRS auditors to get agents to change passwords
- traditional responses:
  - mandatory access control
  - operational security
- social psychology: power of groups and group dynamics forcing people to do things they wouldn't normally
- users you can't train (customers)

## User Authentication with Passwords

![identity_protocol](/notes/assets/identity_management/identity_protocol.jpg)

People often choose passwords from a small set:

- six most common passwords appeared .9% of the time
- 23% of users choose passwords in a dictionary of size 360,000,000
- **online dictionary attacks**
  - defeated by doubling response time every failure
  - harder to block when attacker commands a bot-net

## Basic Password Protocol

![basic_protocol](/notes/assets/identity_management/basic_protocol.jpg)

Problems with basic password protocol: compromise of server exposes all passwords as passwords are stored in the clear

## Hashed Password Protocol

![hash_protocol](/notes/assets/identity_management/hash_protocol.jpg)

Passwords are stored as a hash, meaning that a compromise of a server only reveals hashed passwords that are not usable. As all real hash functions contain the remote chance of collisions, a false positive can be sent to a hacker trying to gain access

## Offline Dictionary Attacks

If an attacked obtains $vk = H(pw)$ from a server, hash all words in Dict until a word $w$ is found such that $H(w) = vk$

Time complexity is $O(|Dict|)$

Off-the-shelf tools enable scanning of 360,000,000 guesses in a few minutes

## Batch Offline Dictionary Attacks

Suppose an attacker steals a password file F and obtains hashed passwords for all users. A batch dictionary attack:

- builds list $L \in (w, H(w))$ for all words in a dict
- finds the intersection of L and F

Total time complexity is $O(max(|Dict|, |F|))$. This is much more efficient than a dictionary attack on each password

## Preventing Batch Dictionary Attacks

**Public salt**: when setting a password pick a random n-bit salt S. When verifying the password for A, test if $H(pw, S_A) = h_A$

Recommended salt length is 64 bits

Batch attack time complexity is now $O(|Dict| x F|)$

![salt_pw](/notes/identity_management/assets/salt_pw)

## Further Defenses

**Slow hash function** H: unnoticeable to user, but makes offline dictionary attack harder

**Secret salts**: when setting a password choose a short random $r$ (8 bits). When verifying password for A, try all values of $r_A$. This is 128 times slower on average and 256 times slower for the attacker

![further_defenses](/notes/assets/identity_management/further_defenses.jpg)

## The Common Password Problem

Users tend to use the same password on many sites. The standard solutions are:

- client side software that converts a common password pw into a unique site password: $pw' = H(pw, user-id, server-id)$. $pw'$ is sent to the server
- Single sign-on: OpenID, SAML, ...
- biometrics
  - problems: not generally secret and cannot be changed