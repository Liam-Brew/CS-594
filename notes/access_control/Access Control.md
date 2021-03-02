# Access Control

## Table of Contents

- [Access Control](#access-control)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Principles of Access Control](#principles-of-access-control)
  - [Access Control Matrix](#access-control-matrix)
    - [Matrix Examples](#matrix-examples)
  - [Users, Principals, Subjects, and Objects](#users-principals-subjects-and-objects)

## Overview

**Discretionary Access Control (DAC)**: individual user can set allow or deny access to an object. Vulnerable as users can be tricked into giving access rights they should not provide

**Mandatory Access Control (MAC)**: the system mechanism controls access. Users cannot alter that access and there exists multi-level security

**Originator Controlled Access Control (ORCON)**: access control is set by the creator of the information. The owner (if different) cannot alter access control. An example is DRM preventing copying of Blu-ray movies

## Principles of Access Control

- open design
- complete mediation
- auditing and accountability
- separation of privilege
- least privilege
- least common mechanism
- psychological acceptability

## Access Control Matrix

**protection state of a system**: describes current settings and values of the system that are relevant to protection

**access control matrix**: describes protection state precisely. THis is a matrix describing hte rights of subjects. State transitions change elements of the matrix

![access_control_matrix](/notes/assets/access_control/access_control_matrix.PNG)

- subjects $S = \{s_1, ..., s_n\}$
- objects $O = \{o_1, ..., o_m\}$
- rights $R = \{r_1, ..., r_k\}$
- entries $A[s_i, o_j] \subseteq R$
- $A[s_i, o_j] = \{r_x, ..., r_y\}$ means that subject $s_i$ has rights $r_x, ..., r_y$ over object $o_j$

### Matrix Examples

**Accounting system:**

![matrix_example_accounting](/notes/assets/access_control/matrix_example_accounting.PNG)

**Network:**

![matrix_example_network](/notes/assets/access_control/matrix_example_network.PNG)

## Users, Principals, Subjects, and Objects

**Users**: actual people who are using the system

**Principals**: roles that users may have. There is a one-to-many mapping from users to principals. Users should always be viewed as multiple principals

![users_and_principals](/notes/assets/access_control/users_and_principals.PNG)

**Subject**: an application executing on behalf of a principal. A principal may at any time be idle or have one or more subjects executing on their behalf

![principals_and_subjects](/notes/assets/access_control/principals_and_subjects.PNG)

Usually (but not always):

- each subject is associated with a unique principal
- all subjects of a principal have identical rights (i.e. rights of the invoking principal

**Object**: anything on which a subject can perform operations (mediated by rights). Usually objects are passive (files, directories, database tables etc.). Subjects can also be objects, with operations (kill, suspend, resume etc.)
