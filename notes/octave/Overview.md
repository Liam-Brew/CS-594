# Octave

## Table of Contents

- [Octave](#octave)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [OCTAVE Principles](#octave-principles)
    - [Security Risk Evaluation](#security-risk-evaluation)
    - [Risk Management](#risk-management)
    - [Organizational Principles](#organizational-principles)
  - [OCTAVE Attributes](#octave-attributes)
  - [OCTAVE Method Processes](#octave-method-processes)
    - [1. Senior Management (1/2 day)](#1-senior-management-12-day)
    - [2. Operational Management (1/2 day)](#2-operational-management-12-day)
    - [3. Staff (1/2 day)](#3-staff-12-day)
    - [4. Threats (1 day)](#4-threats-1-day)
    - [5. Key Components (1/2 day)](#5-key-components-12-day)
    - [6. Evaluation (1 day)](#6-evaluation-1-day)
    - [7. Risk Analysis (1 day)](#7-risk-analysis-1-day)
    - [8. Develop and Select Protection Strategy (1 day)](#8-develop-and-select-protection-strategy-1-day)

## Introduction

**Operationally Critical Threat, Asset and Vulnerability Evaluation (OCTAVE)**: risk analysis framework that considers both technological and organizational issues. It is applied on all levels of management and exists in-house. Characteristics of OCTAVE are:

- **self-directed**: uses organizational personnel that are aware of the mission and operations
- **targeted**: organizational risk and practice-related issues
- **team**: 3-5 people combining operational, business, and technical staff
- **approach**: driven by operational risk and security practices, with technology considered only in its relation to practices

There are distinct beginning and end points in OCTAVE. It can be performed either on an event or periodically

OCTAVE is contrasted with other methods by:

| OCTAVE                      | Other Methods       |
| --------------------------- | ------------------- |
| organization evaluation     | system evaluation   |
| focus on security practices | focus on technology |
| strategic issues            | tactical issues     |
| self direction              | expert led          |

**Analysis team**: identifies information assets that are important to the organization. It focuses risk analysis on the most important of these assets. Factors that are considered are relationships among assets, threats, and vulnerabilities. Risks are evaluated in an operational context and a practice-based protection strategy is created

Phases of OCTAVE are:

1. **build** asset-based threat profiles
   1. identify important assets
   2. how are assets protected?
   3. security requirements for each identified asset
2. **identify** infrastructure vulnerabilities
   1. examine network access paths
   2. identify classes of IT components for each asset
   3. determine resistance to network attacks
3. **develop** security strategy and plans
   1. identify risks to critical assets
   2. develop a protection strategy and mitigation plans
   3. analysis based on previous phases

Criteria of OCTAVE are:

- **principles**
  - fundamental concepts driving the nature of evaluation
  - basis for evaluation
- **attributes**
  - distinctive qualities or characteristics of evaluation
  - defines what makes the evaluation successful
  - derived from principles
- **outputs**
  - required results of each phase
  - forms of outputs are:
    - organizational data (critical assets, threats, current practices)
    - technological data (key components and vulnerabilities)
    - risk analysis and mitigation data (risks to assets and risk measures)

## OCTAVE Principles

### Security Risk Evaluation

Principles of security risk evaluation are:

- **self-direction**
  - performed by people within the organization
  - leading risk evaluation and evaluating efforts
  - making decisions about efforts
- **adaptable measures**
  - catalogs information that defines:
    - accepted security practices
    - known sources of threats and vulnerabilities
  - evaluation process can accommodate changes to the catalog
- **defined process**
  - defined and standardized evaluation procedures
  - assigned responsibilities and specify all tools, worksheets and catalogs
- **foundation for a continuous process**
  - institutionalize practice-based security strategies
  - continuous improvement based on risk evaluation

### Risk Management

Principles of risk management are:

- **forward-looking view**
  - look beyond current problems
  - focus on critical assets, threats and vulnerabilities
- **focus on critical few**
  - targeted data collection on most critical assets
- **integrated management**
  - consider tradeoffs among business and security issues
  - incorporate information security issues into business processes

### Organizational Principles

Organizational Principles are:

- **open communication**
  - collaborative evaluation activities e.g. workshops
  - encourage exchange of security and risk information among all levels of an organization
- **global perspective**
  - identify multiple individual perspectives of risk
  - view risk in larger context
- **teamwork**
  - interdisciplinary team for risk evaluation
  - include additional perspectives

## OCTAVE Attributes

Any OCTAVE project must have these attributes:

- **analysis team**: interdisciplinary team that understands both business processes and IT
- **augmenting analysis team skills**: team should augment skills when necessary by including other members of the organization or external consultants
- **catalog of practices**: allows organizations to evaluate itself against an accepted measure
  - best practices that are consistent with all relevant laws, regulations, standards
- **generic threat profile**: identifies threats to critical assets based on known potential danger sources
  - sources of threats such as insiders, external attackers, physical threats etc.
- **catalog of vulnerabilities**: known technological weaknesses in infrastructure (e.g. MITRE CVE)
- **defined evaluation activities**: institutionalizes evaluation process
- **documented evaluation results**: permanent record of evaluation results
- **evaluation scope**: details which business units to include
- **next steps**: actions to take that will follow up on the results of the evaluation
- **focus on risk**: examine how technological weaknesses impact threats
- **focused activities**: events such as workshops and analysis activities to get better awareness of security and threats
- **organization and technological issues**: examine both security practices, organizational vulnerabilities, and technological weaknesses
- **business and IT participation**: business personnel understand relative importance of business operation; IT personnel understand design of system and technological vulnerabilities
- **senior management participation**: single most important factor in risk evaluation. Must have defined roles
- **collaborative and interdisciplinary approach**

## OCTAVE Method Processes

### 1. Senior Management (1/2 day)

![threat_sources](/notes/assets/octave/threat_sources.PNG)

Gathering senior management information starts Phase 1: Organizational View. In this process the following are identified:

- enterprise assets
- threats to assets
- security requirements of assets (framed in terms of CIA)
  - classification of assets (information, system, software, hardware, or people)
- current protection strategy practices
- organizational vulnerabilities

### 2. Operational Management (1/2 day)

This process involves identifying operational area knowledge. It is similar to Process 1 with senior management. This process identifies the same type of data such as assets and threats, but instead takes place on an operational level

### 3. Staff (1/2 day)

Similar to processes 1 and 2, this process works with 3-4 staff members from each area to get a low-level overview of data and strategy

### 4. Threats (1 day)

Threat profiles are created for critical assets. These assets are identified via inputs from senior and operational management and staff. For 3-5 critical assets:

- describe each asset
- give rationale for why it is deemed critical
- identify security requirements
  - divide requirements in terms of CIA
- select the most important category of security requirements

For each area of concern of an asset, identify its threat properties. OCTAVE is based on five threat properties. They are:

1. asset
2. actor
3. motive (human actors)
4. access (human actors)
5. outcome

In addition to this, there are four threat sources possible:

1. human actors using network access
2. human actors using physical access
3. system problems
4. other problems

Threat profiles are defined using **threat trees**. They are based in the following format:

![threat_tree](/notes/assets/octave/threat_tree.PNG)

### 5. Key Components (1/2 day)

This process starts Phase 2: Technological View. Technological vulnerabilities are weaknesses in a system that can be lead to an unauthorized actions. They can be identified through the use of vulnerability tools. There are three forms of technological vulnerabilities:

1. **design vulnerability**: e.g. weak authentication scheme
2. **implementation vulnerability**: e.g. buffer overflow, SQL injection
3. **configuration vulnerability**: e.g. accounts with default passwords

In this process, key components of critical assets are identified and prepared for vulnerability testing. Both the components themselves as well as their access paths are studied to provide a complete picture of the system under test.

### 6. Evaluation (1 day)

In this process, the components identified in the previous process are evaluated with respect to potential vulnerabilities, the impact of this component on the critical asset, and how these vulnerabilities may be rectified. A vulnerability summary is compiled to show the relationships between components, the technical tools used to examine them, and the vulnerabilities that are discovered. An example vulnerability summary:

![vulnerability_summary](/notes/assets/octave/vulnerability_summary.PNG)

Vulnerabilities can be classified into one of three levels:

- **high**: must be fixed immediately (within the next 24 hours)
- **medium**: must be fixed within one month
- **low**: may be fixed later

From here, actions and recommendations are compiled for how these technical vulnerabilities may be addressed

Finally, a gap analysis is performed on the threat tree to identify if there is a significant risk to an asset resulting from a key component vulnerability

### 7. Risk Analysis (1 day)

This process starts Phase 3: Strategy and Plan development. A **risk analysis** identifies the impact of each threat outcome to the organization and determines a relative priority for which risks to mitigate. This is a qualitative measure of impact to critical assets and their resulting effect on impact areas. Some examples of impact areas are:

- reputation/customer confidence
- life/health of customers
- fines/legal penalties
- financial

For each impact area, evaluation criteria are developed to help analyze the potential impact of various threats. The following is a collection of sample evaluation criteria for a legal area:

![evaluation_criteria](/notes/assets/octave/evaluation_criteria.PNG)

This helps to classify impact areas. The information developed here is used in the final development of this process, the **impact measure**. This lists potential impacts and its importance for every threat outcome:

![impact_measure](/notes/assets/octave/impact_measure.PNG)

### 8. Develop and Select Protection Strategy (1 day)

This process develops the **protection strategy**, which is the culmination of all other OCTAVE processes. There are three parts of this strategy:

1. **protection strategy**: strategies to enable, initiate, implement, and maintain security
   1. **catalog of practices** lists both overall strategies for managing security as well as operational management procedures for overseeing the various security aspects (training, policies, regulations etc.)
2. **mitigation plan**: details how to recognize, resist, and recover from each identified threat
3. **action list**: near-term actions taken by security management. Each item contains a time frame, the group which is responsible for it, and required management actions

To select the optimal protection strategy, senior management must review the following at a high level:

- asset information
- critical assets and their responsibilities
- security practices and organizational vulnerabilities
- critical asset risk profiles

The pros and cons of each protection strategy are weighed and the optimal one is selected, oftentimes after refinements are made using the non-selected strategies as a reference
