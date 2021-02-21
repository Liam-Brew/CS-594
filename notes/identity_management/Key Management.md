# Key Management

## Table of Contents

- [Key Management](#key-management)
  - [Table of Contents](#table-of-contents)
  - [Key Management Protocols](#key-management-protocols)
    - [Freshness](#freshness)
  - [Attacks on Key Management Protocols](#attacks-on-key-management-protocols)
    - [Wide-Mouthed Frog Protocol](#wide-mouthed-frog-protocol)
    - [Woo-Lam Authentication Protocol](#woo-lam-authentication-protocol)
    - [Needham-Schroder Protocol](#needham-schroder-protocol)
    - [Kerberos Protocol](#kerberos-protocol)

## Key Management Protocols

![key_management_basic](/notes/assets/identity_management/key_management_basic.PNG)

Suppose that two parties share a key with a central authority and want to communicate. The sender gets the receiver's key from the authority, which is also sent by the authority to the receiver. How can these parties determine freshness of the key and that an old compromised key is not used?

The below example illustrates a MitM attack:

![key_management_mitm](/notes/assets/identity_management/key_management_mitm.PNG)

MitM attacks can be fixed by adding information in messages coming from the authority to indicate who the message is intended for. This information is known as a **session key**

![key_management_mitm_avoidance](/notes/assets/identity_management/key_management_mitm_avoidance.PNG)

However, session keys can be cracked via repeated MitMs and decryption practices such as cryptanalysis. Therefore **freshness** is important

### Freshness

Freshness is used to guide the usage of session keys. Only fresh session keys should be used to ensure the integrity of the communication

A possible protocol is:

$A \rightarrow S: A, B$
$S \rightarrow A: \{A, B, K_AB, T\}_{KAS}, \{A, B, K_AB, T\}_{KBS}$
$A \rightarrow B: \{A, B, K_{AB}, T\}_{KBS}$

Once this process has been completed messages can then be sent as follows:

$A \rightarrow B: \{M\}_{KAB}$

## Attacks on Key Management Protocols

### Wide-Mouthed Frog Protocol

Alice forwards a session key to Bob via Sam:

$A \rightarrow S: \{T_A, B, K_{AB}\}_{KAS}$
$S \rightarrow B: \{T_S, B, K_{AB}\}_{KBS}$

Problem: Sam changes the timestamp to preserve freshness, but Eve can mount a replay attack:

$E \rightarrow S: \{T_S, A, K_{AB}\}_{KBS}$
$S \rightarrow A: \{T1_S, B, K_{AB}\}_{KAS}$
$E \rightarrow S: \{T1_S, B, K_{AB}\}_{KAS}$
...etc

### Woo-Lam Authentication Protocol

Alice authenticates to Bob without the use of a shared key. Sam certifies Alice's identity based on a response to a challenge from Bob

$A \rightarrow B: A$
$B \rightarrow A: N_B$
$A \rightarrow B: \{N_B\}_{KAS}$
$B \rightarrow S: \{A, \{N_B\}_{KAS}\}_{KBS}$
$S \rightarrow B: \{N_B\}_{KBS}$

This protocol is vulnerable to a MitM attack by Eve:

$E \rightarrow B: A$
$E \rightarrow B: E$
$B \rightarrow A: N_B$
$B \rightarrow E: N1_B$
$E \rightarrow B: \{N_B\}_{KES}$
$B \rightarrow S: \{A, \{N_B\}_{KES}\}_{KBS}$
$B \rightarrow S: \{E, \{N_B\}_{KES}\}_{KBS}$
$S \rightarrow B: \{N_B\}_{KBS}$

The problem here is that the protocol does not tie the last message from Sam to the response that he is certifying

### Needham-Schroder Protocol

To preserve freshness, nonces are used instead of timestamps. Sam sends Alice a challenge under the session key and Alice authenticates with her response

$A \rightarrow S: A, B, N_A$
$S \rightarrow A: \{N_A, B, K_{AB}, \{K_AB, A\}_{KBS}\}_{KAS}$
$A \rightarrow B: \{K_AB, A\}_{KBS}$
$B \rightarrow A: \{N_B\}_{KAB}$
$A \rightarrow B: \{N_B-1\}_{KAB}$

This is vulnerable to replay attacks if the session key is compromised. Session keys are more vulnerable than secret keys as they are used to encrypt all communications for a session, meaning that there is more encrypted data and therefore more opportunity for using cryptanalysis to get the key. Alternatively, secret keys are only used to encrypted communications to the trusted party for key establishment

Eve can authenticate to Bob as Alice using $K_{AB}$ in the last three steps. This is because there is no nonce on the message being sent to Bob, allowing this message to be sent later:

$A \rightarrow S: A, B, N_A$
$S \rightarrow A: \{N_A, B, K_{AB}, \{K_AB, A\}_{KBS}\}_{KAS}$
$E \rightarrow B: \{K_AB, A\}_{KBS}$
$B \rightarrow A: \{N1_B\}_{KAB}$
$E \rightarrow B: \{N1_B-1\}_{KAB}$

### Kerberos Protocol

The revised version of Needham-Schroder that uses timestamps instead of nonces

$A \rightarrow S: A, B$
$S \rightarrow A: \{T_S, L, K_{AB}, B, \{T_S, L, K_{AB}, A\}_{KBS}\}_{KAS}$
$A \rightarrow B: \{T_S, L, K_{AB}, A\}_{KBS}, \{A, T_A\}_{KAB}$
$B \rightarrow A: \{A, T1_A\}_{KAB}$

This removes the problem of nonces but introduces clock synchronization issues. Therefore Kerebos is typically implemented in local area networks

![kerberos](/notes/assets/identity_management/kerberos.PNG)
