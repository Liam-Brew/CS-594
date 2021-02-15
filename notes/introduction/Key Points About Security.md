# Key Points About Security

## Table of Contents

- [Key Points About Security](#key-points-about-security)
  - [Table of Contents](#table-of-contents)
  - ["Sweet Spot"](#sweet-spot)
  - [Information Security](#information-security)
    - [DoD Terminology](#dod-terminology)
  - [Security vs. Reliability](#security-vs-reliability)
    - [Hardware Failures](#hardware-failures)
    - [Software Failure](#software-failure)
    - [Security Breach](#security-breach)
    - [Survivable (Dependable) Systems](#survivable-dependable-systems)
  - [Management Security Concerns](#management-security-concerns)
    - [Sources of Security Requirements](#sources-of-security-requirements)
  - [Security in the System Life Cycle](#security-in-the-system-life-cycle)
    - [Risk Analysis](#risk-analysis)
    - [Security Policy](#security-policy)

Information security is a key part of the system lifecycle:

- conception and design
- development
- operation of deployed system
- system "decommission"

Use of technology (firewalls, crypto etc.) is only part of security. True security is based on people, processes, and technology. Actively managing the security process is a major part of ensuring security. People themselves are a huge part of the security process, even if they may not know it

Security is a wide array of options and not a yes/no choice

- no security is not enough
- perfect security is difficult, expensive, hard to do, and takes a long time
- a security "sweet spot" exists that is subjective to the situation at hand

## "Sweet Spot"

Extremes of security: open computer with no access control or firewalls vs. a system in a locked Faraday cage with armed guards and no connections

Costs of security:

- development cost and time
- equipment and maintenance cost
- usability
- functionality
- performance

Finding the "sweet spot" of security:

- depends on the specific environment and situation
- understand security requirements
  - "sweet spot" is getting the right level of security for each environment
- errors in finding the "sweet spot"
  - uniformly low security because the risk is not understood
  - uniformly strong and expensive security everywhere

## Information Security

A set of properties of the information system, not a technology

CIA Triad:

- **confidentiality**: only permitted entities are allowed to see the information
- **integrity**: only permitted entities are allowed to modify the information (includes creation and deletion)
  - **integrity preservation**: you know it can't be changed
  - **integrity violation detection**: you can't trust the information and revert to a backup
- **availability**: the information is available when needed

Related security concepts:

- **identification**: means of saying who/what an entity is
- **authentication**: means to verify that an entity is who it claims to be (to support confidentiality and integrity)
- **access control**: means to enforce which entities have access to information to support confidentiality and integrity
- **authorization**: combination of authentication (who) and access control
- **non-repudiation**: integrity of the pair of information and its creator
- **privacy**: confidentiality of personal information
- **anonymity**: confidentiality of identity
- **recovery**: restoration of a system to a correct state after a security incident

### DoD Terminology

- **Communications Security (COMSEC)**: security of information while in transit. Includes packet nets, radio links, free space optics etc.
- **Computer Security (COMPUSEC)**: security of information while stored or being processed on a computer
- **Information Security (INFOSEC)**: COMPUSEC + COMSEC
- **Transmission Security (TRANSEC)**: security of transmission media
- **Operations Security (OPSEC)**: operational processes for protecting potentially sensitive unclassified material (people and technology)
- **Automated Information Systems (AIS)**: computers + networks linking computers

## Security vs. Reliability

CIA is threatened by security attacks, software flaws, and hardware failures. It can be hard to determine which class is the cause

### Hardware Failures

- no malicious cause
- usually affects **availability**
- typically independent events
- testing is a reliable way to discover these failures
- stochastic and temporal failure models are useful metrics (ex: MTBF)

### Software Failure

- no malicious attack: design or coding error
- mostly affects availability but can also impact integrity and confidentiality
- often correlated events from the same flaw as similar state conditions arise in different instantiations
- stochastic models are of limited value
- examples: memory management failures and raise conditions

### Security Breach

- malicious attack
- serious attacks often attempt to hide event
- can affect all three CIA elements
- in most cases, the most serious impacts are attacks on integrity or confidentiality
- many attacks are highly correlated worldwide but some are very targeted and correlations may be hard to find

Security flaw locations:

- system design
  - not planning for or neglecting security
- system implementation
  - ambiguous documentation
  - implementation errors
- system use
  - configuration: often weakest security "out of the box"
  - failure to keep up with updates/patches
  - physical security
- ill-advised user actions
  - poor password management
  - social engineering

### Survivable (Dependable) Systems

- systems that are both reliable and secure
- reliability and security requirements are often similar (particularly for availability)
- designing survivable systems
  - understand and manage risks, understand threats
- recovery plans for failures and breaches can be combined as they are similar (backups)

## Management Security Concerns

- classified information at government agencies
  - national security, loss of life, "sources and methods"
- unclassified government information
  - political, financial, legal, and career impacts
- corporate
  - financial, intellectual property, legal, corporate image, career impacts

### Sources of Security Requirements

- risk analysis (national security, lives, PII etc.)
- legal (HIPAA, privacy laws etc.)
- higher level government or corporate policies
- corporate/agency/personal image

Requirements may change due to changing threat environment or new discoveries

## Security in the System Life Cycle

Security system engineering is part of the overall engineering process. It should include design and development and be level-based (lower-level designs implement higher level policies). Security should be integral to the overall design of the system

Steps to include security in overall system design are:

- risk analysis
- security requirements analysis
  - security and reliability are "non-functional" requirements
- high level security policy (technology, management processes, and personal policies)
- system engineering
  - security design and development should be integral elements from the start
- security management of deployed system
- incident response
- business continuity planning
- decommissioning of systems and data

### Risk Analysis

- what is at risk?
- where does the threat come from?
  - motivation
  - capabilities
- what vulnerabilities can be exploited?
  - technical
  - process
  - people
- risk management
  - eliminate/reduce risk (crypto, firewalls etc.)
  - accept risk (with recovery process)
  - transfer risk (e.g., to an insurance company)

### Security Policy

- statement of security requirements
- security policy statements should have a corresponding enforcement mechanism
- policies exist at multiple levels:
  - high level: "company proprietary information shall be protected from release to unauthorized personnel"
  - mid level: "there shall be no externally initiated FTP sessions"
  - low level: a firewall rule blocking incoming traffic on ports 20 (FTP data), 21 (FTP control) and 69 (tftp)
- policies also define management processes such as incident response and personnel rules
