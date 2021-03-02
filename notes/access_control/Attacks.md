# Attacks

## Table of Contents

- [Attacks](#attacks)
  - [Table of Contents](#table-of-contents)
  - [Types of Attacks](#types-of-attacks)
    - [Trojan Horse](#trojan-horse)
    - [Logic Bomb](#logic-bomb)
    - [Trap Doors](#trap-doors)
    - [Buffer Overflow/Stack Smashing](#buffer-overflowstack-smashing)
    - [Viruses and Worms](#viruses-and-worms)
    - [Race Conditions](#race-conditions)
    - [SQL Injection](#sql-injection)
    - [Cross-Site Scripting (XSS)](#cross-site-scripting-xss)
  - [Sandboxing](#sandboxing)
    - [Certifying Compilers](#certifying-compilers)
    - [Java Stack Inspection](#java-stack-inspection)
    - [Java Security](#java-security)
    - [Hypervisor-based Virtualization](#hypervisor-based-virtualization)

## Types of Attacks

### Trojan Horse

A malicious program disguised as an innocent one, typically hidden within programs such as games and apps. When run, the Trojan Horse executes with the user's privileges

### Logic Bomb

A piece of code in the OS or app that is dormant until a certain time has elapsed or an event has occurred. These can act as a Trojan Horse or virus once triggered

### Trap Doors

Code inserted by the programmer to bypass a normal check

### Buffer Overflow/Stack Smashing

Going out-of-bounds of a C array to over-write the return address. This causes the return address to instead point to the buffer (which has been filled with code from the attacker), causing the system to execute attacker code after the completion of the program

![buffer_overflow](/notes/assets/access_control/buffer_overflow.PNG)

### Viruses and Worms

- **virus**: program that reproduces itself by attaching its code to another program
- **worms**: actively replicate without a helper program
  
### Race Conditions

Also known as time-of-check to time-of-use bugs. In between the a program checking to see if a action is allowed and the actual calling of that action, the pointer to said action can be changed, thereby implementing a different action

### SQL Injection

Used to gain access to the greater database beyond what is authorized and revealed to a specific program

![sql_injection](/notes/assets/access_control/sql_injection.PNG)

The result of the above is returning all user information in the users table

### Cross-Site Scripting (XSS)

Provide scripting code (e.g., JavaScript) in the input field of an HTML web page. Websites that don't validate input may run the code

## Sandboxing

Sandboxing is a strategy that isolates applications from critical system resources and other programs. It helps prevent malware or other harmful programs from damaging the entire system

### Certifying Compilers

Code consumers can check the code itself to ensure it's safe. The code includes annotations to make this feasible. A *certifying compiler* generates self-certifying code, with the Java Virtual Machine being the first demonstration of this idea. The *loader* checks the certificate against the code

![jvm_certification](/notes/assets/access_control/jvm_certification.PNG)

### Java Stack Inspection

Java checks permissions, e.g. file permissions on the file open operation

**permission**: set of rights to a resource

The calling of an operation demands a permission, and the caller must have a grant of that permission beforehand

Java stack inspection checks for all of the callers of code to ensure integrity

- example: trusted code is called by trusted code which was called by un-trusted code. This is detected by stack inspection

### Java Security

**bytecode verification**: ensures that Java code has not been tampered with

**stack inspection**: access control based on all influences of an API call

### Hypervisor-based Virtualization

A **hypervisor** is a thin layer of software running on hardware, isolating all other software (including the OS) from the hardware

The hypervisor virtualizes the physical machine. This enables different OSs to run on the same piece of hardware through the use of partitions

**partition**: each partition is a virtual machine and has one or more virtual processors. Partitions can share resources

The hypervisor enforces memory access rules, policy for CPU usage, and ownership of other devices

Virtualization can worsen security. If an attacker detects the use of a hypervisor and takes control of that hypervisor, they can seize control of all guest operating systems hosted on said hypervisor
