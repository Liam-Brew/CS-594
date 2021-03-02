# Safety

## Table of Contents

- [Safety](#safety)
  - [Table of Contents](#table-of-contents)
  - [State Transitions](#state-transitions)
  - [HRU Commands and Operations](#hru-commands-and-operations)
  - [Key Points](#key-points)
  - [Take-Grant Model](#take-grant-model)

## State Transitions

**State transitions** represent a change in the protection state of a system

'|' is the transition symbol:

- $X_i |-_cX_{i+1}$: command C moves the system from state $X_i$ to $X_{i+1}$
- $X_i |-*X_{i+1}$: a sequence of commands move the system from state $X_i$ to $X_{i+1}$

Commands are often called transformation procedures

## HRU Commands and Operations

```
command a(X1, X2, ..., Xk)
    if ri in A[Xs1, Xo1] and r2 in A[Xs2, Xo2] and ... and rk in A[Xsk, Xok]
    then
        op1; op2; ...; opn
    end
```

There are six primitive operations:

1. enter r into $A[X_s, X_o]$
2. delete r from $A[X_s, X_o]$
3. create subject $X_S$
4. create object $X_o$
5. destroy subject $X_s$
6. destroy object $X_o$

The HRU semantics are:

![hru_semantics](/notes/assets/access_control/hru_semantics.PNG)

**Example**: process *p* creates a file *f* with *r* and *w* permission

``` 
command create_file(p, f)
create object f;
    enter own into A[p, f];
    enter r into A[p, f];
    enter w into A[p, f];
end
```

**Example**: a mono-conditional and mono-operational command to confer a right

```
command confer_r(owner, friend, f)
    if own in A[owner, f]
        then enter r into A[friend, f]
end
```

**Attenuation of Privilege**: principle that says you can't give rights you do not possess. This restricts the addition of rights within a system and is usually ignored in a system

## Key Points

- the access control matrix is the simplest abstraction mechanism for representing protection state
- transitions alter the protection state
- six primitive operations alter the matrix

## Take-Grant Model

The **Take-Grant Model** represents the system using a directed graph, with nodes being either subjects or objects

An arc from node A to B indicates that A has some access rights to B

Access rights are:

- read (r)
- write(w)
- take (t): implies A can take B's access rights to any other node
- grant (g): implies B can be given any access right A possesses

![take_grant](/notes/assets/access_control/take_grant.PNG)

**create rule**: A can create a new graph $G_1$ from an old graph $G_0$ by adding a node B and an arc from A to B with rights set to X

**remove rule**: Suppose there is an arc with rights X from nodes A and B. Rules Y may be removed from x to produce X\Y. If X\Y is empty, the arc is deleted

![create_remove](/notes/assets/access_control/create_remove.PNG)
