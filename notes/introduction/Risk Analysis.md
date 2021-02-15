# Risk Analysis

## Table of Contents

- [Risk Analysis](#risk-analysis)
  - [Table of Contents](#table-of-contents)
    - [Risk Analysis Step 1: Assets](#risk-analysis-step-1-assets)
  - [Risk Analysis Step 2: Threats](#risk-analysis-step-2-threats)
    - [Who is a threat?](#who-is-a-threat)
  - [Capabilities of Threats](#capabilities-of-threats)
  - [Risk Analysis Step 3: Vulnerabilities](#risk-analysis-step-3-vulnerabilities)
    - [System Security Testing](#system-security-testing)
  - [Risk Analysis Step 4: Risk Management](#risk-analysis-step-4-risk-management)
    - [Risk Management Decision Overview](#risk-management-decision-overview)
    - [Risk Management and Dependable Systems](#risk-management-and-dependable-systems)
    - [Quantitative (Objective) Risk Management](#quantitative-objective-risk-management)
    - [Qualitative (Subjective) Risk Management](#qualitative-subjective-risk-management)
  - [NIST Risk Process Framework](#nist-risk-process-framework)
    - [System Characterization](#system-characterization)
    - [Vulnerability Identification](#vulnerability-identification)
    - [Control Analysis](#control-analysis)
    - [Control Recommendations](#control-recommendations)

Risk analysis starts with understanding what assets are at risk, what their threats are, and what vulnerabilities can be exploited. This helps to determine the security "sweet spot"

Elements to determine risk are:

- what is at risk? (national security, property, money etc.)
- what is the threat and where does it come from? (who? motivation? target? capabilities?)
- what vulnerabilities can be exploited? (technology, process, people etc.)

A **risk** is a combination of an asset, a threat, and a vulnerability. An **event** is when a threat uses a vulnerability to compromise an asset. Determining the likelihood of an event is a key part of the risk analysis

**Risk analysis**: process to systematically identify the assets, threats, and vulnerabilities in a system. It is a continuous process over the life of a system:

- early design: input to basic system design decisions to avoid building vulnerabilities (e.g., wired vs. wireless links)
- detailed design: input to technology and security design (e.g., whether to use encryption of WiFi)
- deployed system: identify new threats, vulnerabilities and assets
- system modification: risk analysis is an input during updates and upgrades

### Risk Analysis Step 1: Assets

Identify and inventory assets at risk, such as:

- safety
- items with readily established dollar amounts
- intellectual property
- reputation

This list should be prioritized according to their value. One way to value assets is by looking at their **time value**. Under time value, the value of an asset may vary over time, thereby impacting the way a threat acts and the kinds of protections needed. In military and intelligence terms, time value has the following implications:

- some secrets need to be protected for only a short time (ex: an attack will occur in one hour)
- others need to be protected for years, such as sources and methods of gathering and using intelligence

## Risk Analysis Step 2: Threats

Identify threats according to possible vectors and likelihood. Some criteria are:

- who?
- motivation?
- target?

Develop a **threat tree** (vulnerability tree): work back from each asset through the source of a threat, building a tree of paths to the asset

Prioritize threats: is the NSA interested in my credit card number?

### Who is a threat?

Types of assets you have will identify the kinds of threats:

- classified information: threats from foreign nations, terrorists
- pure financial assets: threats from thieves (including insiders, outsiders, and customers)
- intellectual property: threats from people who can use information (competitors, foreign nations, potential customers)
- running business: threats from competitors (DoS attack), hackers, disgruntled customers
- reputation: threats from hackers, political opponents, social opponents
- computing/network resources: threats from people storing large files, using your PC to attack others (trojans for DDoS, SPAM), VPNs to avoid trace back

## Capabilities of Threats

To select the right level of protection you should understand the capabilities of threats:

- intellectual capabilities
  - skilled hackers
  - "script kiddies"
  - foreign governments
- computing resources (particularly for key breaking)
- access to systems (insiders)

## Risk Analysis Step 3: Vulnerabilities

What parts of the overall system might be attacked? (networks, processors, people through social engineering etc.)

Vulnerabilities will be system design dependent:

- initially identify the areas where vulnerabilities may be present
- as the design develops specific vulnerabilities can be assessed
- risk assessment should be used to influence the system design process

### System Security Testing

- automated vulnerability scanning tool
  - ex: scan for open FTP ports
  - potential vulnerabilities
  - depends on the context
- Security Test and Evaluation (ST&E)
  - test scripts, procedures, and expected test results
  - test effectiveness of security controls
- Penetration testing
  - ensure external-facing assets are secure

## Risk Analysis Step 4: Risk Management

Risk management is how you deal with the risk identified in risk analysis

- default risk management approach is accepting the risk
- typical approach is to combine acceptance, reduction, and transfer for different risks
- overall system design, security technology, and methodology are used to reduce risks. These are security controls
  - risk analysis is a key input to the system design process
  - a preliminary risk analysis is a starting point for security requirements
  - a detailed risk analysis is based on the system design

### Risk Management Decision Overview

The decision about which approach to use for risk management is based on a cost-benefit analysis of the cost of the control versus the expected/potential loss if the event occurs

### Risk Management and Dependable Systems

Risk analysis and risk management approaches also apply to dependable system development, with the difference that threats are external events and not intentional acts

Two approaches to risk management: quantitative and qualitative

### Quantitative (Objective) Risk Management

- compute expected value loss for all events
  - **exposure factor**: percentage of loss that a realized threat event would have on a specific asset (e.g. loss of hardware vs. loss of all computing resources)
  - **Single Loss Expectancy SLE** = asset value x exposure factor (EF)
- compute probability of each type of expected loss
  - **Annualized Rate of Occurrence (ARO)**
- compute overall **Annual Loss Expectancy (ALE)** = SLE * ARO
- for each security measure compare the cost of implementation to the reduction of ALE
  - if the cost is lower implement the security measure

| Pros                                                                                                                                 | Cons                                                                                              |
| ------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------- |
| objective, independent process                                                                                                       | can be difficult to enumerate all types of events and get meaningful data on probability and cost |
| solid basic for cost/benefit analysis of safeguards                                                                                  | time consuming and costly to do right                                                             |
| credibility for auditing and management                                                                                              | many unknowns give a false sense of control                                                       |
| used for reliability-related design questions where threats and likelihood of "events" are easier to measure (ex: redundant servers) |                                                                                                   |

### Qualitative (Subjective) Risk Management

- establish classes of loss values "impact"
  - low, medium high
  - under \$10k, \$10k - \$1M, over $1M
  - type of loss (e.g. compromised credit cards, PII, loss of life)
- establish classes of likelihood compromise
  - low, medium, high
- decide on a risk management approach to each combination of (class of loss, likelihood of loss)
- focus effort on high loss items

## NIST Risk Process Framework

![nist_risk_process](/notes/assets/introduction/nist_risk_process.jpg)

### System Characterization

- purpose: delineate system boundary
- questionnaires
  - planned and in-use controls
  - technical and managerial staff
- on-site interviews
- document review
  - policies
  - system documentation
  - results of previous risk assessments
- automated scanning tools

### Vulnerability Identification

- develop security requirements checklist
- [system security testing](#system-security-testing)

### Control Analysis

- security controls in place or planned
- control methods
  - technical: access control, IDS, authentication etc.
  - management and operational: policies, procedures, personnel and physical security
- control categories
  - preventative: access control, crypto etc.
  - detective: audit trails, IDS, checksums

### Control Recommendations

- cost-benefit analysis
  - cost of recommended control
  - savings in reduced risk (lowered probability of an event)
- consider also:
  - legislation and regulation
  - enterprise policy
  - operational impact
  - safety and reliability