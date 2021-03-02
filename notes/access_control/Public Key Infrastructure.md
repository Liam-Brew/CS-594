# Public Key Infrastructure

## Table of Contents

- [Public Key Infrastructure](#public-key-infrastructure)
  - [Table of Contents](#table-of-contents)
  - [Digital Signature](#digital-signature)
  - [Digital Certificate](#digital-certificate)
    - [Certificate Revocation](#certificate-revocation)
    - [Trust Establishment](#trust-establishment)
    - [X509 Certificate Authority](#x509-certificate-authority)

## Digital Signature

**Digital signature**: data item that vouches for the origin and integrity of a message. The originator of a message uses a signing (private) key. The recipient uses a verification (public) key to verify the message's origin and its integrity

![digital_signature](/notes/assets/access_control/digital_signature.PNG)

## Digital Certificate

**Digital certificate**: a binding between an entity's public key and one or more attributes relating to its identity. Certificates are signed by the third-party certificate authority (CA) to ensure that they cannot be tampered with

**Certificate Policy (CP)**: a document that sets out the rights, duties, and obligations of each party in a PKI. Typically has a legal effect

![certificate_policy](/notes/assets/access_control/certificate_policy.PNG)

### Certificate Revocation

Two methods:

**Certificate Revocation List (CRL)**: published by CAs at well-defined intervals and list un-expired certificates that have been revoked. It is the user of a certificate's responsibility to download CRLs and verify if a certificate has been revoked

**Online Certificate Status Protocol (OCSP)**: a highly-available server to enable real-time checking of a particular certificate to see if it has been revoked or suspended. This removes the window of opportunity inherent with reporting a compromised certificate and updating the corresponding CRL

![crl_vs_ocsp](/notes/assets/access_control/crl_vs_ocsp.PNG)

### Trust Establishment

Example: inter-enterprise communication between Apple and Google

![trust_establishment](/notes/assets/access_control/trust_establishment.PNG)

**Cross-certification**: allows enterprises to establish mutual trust. However, this exponentially increases the complexity of the system, requiring $n^2$ certificates for $n$ entities. To overcome this, a **bridge CA** certifies each of the entity's certificate and is in turn trusted by all of the entities. Bridge CAs are not roots of hierarchies; the root of trust for users is their own organization, which in turn trusts the bridge CA

![bridge_ca](/notes/assets/access_control/bridge_ca.PNG)

### X509 Certificate Authority

Responsible for:

- key generation
- digital certificate generation
- certificate issuance and distribution
- revocation
- key backup and recovery system
