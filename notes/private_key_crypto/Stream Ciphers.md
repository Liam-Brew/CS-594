# Stream Ciphers

## Table of Contents

- [Stream Ciphers](#stream-ciphers)
  - [Table of Contents](#table-of-contents)
  - [One-time Pad (OTP)](#one-time-pad-otp)
  - [Modern Stream Ciphers](#modern-stream-ciphers)
  - [Linear Feedback Shift Registers](#linear-feedback-shift-registers)
  - [Rivest Cipher 4 (RC4)](#rivest-cipher-4-rc4)

## One-time Pad (OTP)

This is a very powerful encryption technique, and is the only one to be known to be provably secure provided that the key is random and not reused. It is basically a Vigenere cipher with an infinitely long key

Encryption: $y_i = x_i + k_i$

Decryption: $x_i = y_i + k_i$

These tend to be fairly impracticable due to limitations in distributing and managing OTPs

## Modern Stream Ciphers

![stream_cipher](/notes/assets/private_key_crypto/stream_cipher.PNG)

XOR each bit of plaintext from a continuous stream with a bit from pseudo-random sequence

At the receiver, use the same private key and XOR it again to extract the plaintext

The danger of using stream ciphers is if the same initialization key is used for two messages, they will be encrypted with the same keystream:

- $P_1$ and $P_2$ are encrypted to $C_1$ and $C_2$ with the same IV
- $C_1 + C_2 = P_1 + K + P_2 + K = P_1 + $P_2$
- if $P_1$ is known, then $P_2$ can be recovered
- other attacks exist that are based only on plaintext differences

A **nonce**, number used once, is used to encrypt a single message. The nonce must be sent to receiver in order to decrypt the message

## Linear Feedback Shift Registers

Under this approach, output bits depend on previous output bits using a linear recurrence

Assume initial values $x_1,x_2,...,x_m$. This yields the following output:

$$x_{n+m} = c_0x_n + c_1x_{n+1}+...+c_{m-1}x_{n+m-1}(\bmod2)$$

The pros of this approach are that it is fast and uses a small key that generates a key stream with a large periodicity. The cons are that it is vulnerable to a known plaintext attack

An example key sequence to demonstrate this attack is 0110**1011**11100. For this, try m = 4

This attack is based on the recurrence formula $x_{n+4} = c_0x_n + c_1x_{n+1} + c_2x_{n+2} + c_3x_{n+3}$. Solve the equations:

$$1 = c_0*0 + c_1*1 + c_2*1 + c_3*0 \\
0 = c_0*1 + c_1*1 + c_2*0 + c_3*1 \\
1 = c_0*1 + c_1*0 + c_2*1 + c_3*0 \\
1 = c_0*0 + c_1*1 + c_2*0 + c_3*1$$

This yields the solution $c_0 = 1, c_1 = 1, c_2 =0, c_3 = 0$

## Rivest Cipher 4 (RC4)

An example of a stream cipher is **RC4**, which is basically a self-modifying state machine:

- i:=i+1
- j:=j+s[i]
- swap(s[i],s[j])
- k:=s[s[i]+s[j]]

![rc4](/notes/assets/private_key_crypto/rc4.PNG)

The vulnerability of this method is that it does not make use of a nonce. This means that it is up to the application using it to create a nonce

Wired Equivalent Privacy (WEP) was based on RC4 and was easily exploited due to the fact that it's nonce is only 24 bits
