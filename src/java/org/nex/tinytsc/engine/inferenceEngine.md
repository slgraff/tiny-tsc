# Inference Engine Notes
## General
In any inference engine, there are<br/>
* Rules
* Facts

In TSC, Facts come from *Episodes*, and Rules are supplied by *ProcessRules*.

Both Rules and Episodes convey their information in *Sentences* which take this form:<br/>
`(Predicate (Subject) truth )` or<br/>
`( Predicate (Subject Object) truth )`

In general, in TSC, if some sentence is not `true` we just set its truth field to `false` rather than deleting it.

## Binding
Binding is the process of first, matching the *predicates* in each sentence in a Rule with those in the Episode.

But, Rules and Episodes are different creatures:<br/>
An Episode contains *facts*, things which are measured and defined predicates which may be more specific than those in a Rule. A Rule's predicates, in order to be useful, may define rather *general* predicates. That's discussed next.

What is crucial, however, is that predicates are defined as Concepts just like everything else.  That is, TSC operates in a fixed vocabulary (ontology), which means that you cannot just write a knowledge base for it with arbitrary predicates. That's because, internally, when the binding agent is trying to match two predicates, it will perform an operation which asks *is this episode predicate an instance of that rule predicate* using an internal `isA` test. For that test to work, the predicates must have been defined within the structure of topics, which we call a *taxonomy*.

## Predicates
Predicates are *Classes* of objects, such as, e.g. *Human*, *Animal*, or *Automobile* for Actors,  *Abuts*, or *Contains* for Relations, or *Hot* or *Cold* for States.

Predicates, in Episodes, are *Facts* which tend to be *concrete* to some level, from, e.g. *Female* to specify a kind of human, to *Sue Smith* (rare) to name a specific individual.

Predicates, in Process Rules, specify classes, which means they can, but are not required, to be *abstractions* of higher level classes. As a trivial example, *Sentient* could" be an abstraction for any kind of animal, including humans, which then entails even "Sue Smith" if she shows up in some Episode.

What this means is that the entire ensemble of rules and episodes exists within the constraints of particular *Ontologies*; it is unlikely to be productive when faced with a random vocabulary.

What it also means is that the implementation, to be performant, must provide an efficient implementation of the *isA* test which answers this question:<br/>
*Is this predicate the same as that one* which is really asking: if they are not exactly the same, is that one an abstraction of this one?

## Episodes
Episodes are *events* in *Process Models* known as *Envisionments*. Environments begin with *Initial Conditions*. Think of a Stage in which you "set the stage" with some *Actors* arranged in certain *Relations* and existing in certain *States*.  That stage setting would be the initial conditions.
## Process Rules
Process Rules are IF-THEN (Condition-Action) rules which define *Patterns*.  The Condition side of rules (those specified by an *IF* or an *IF-NOT* define specific patterns to be found in the Episodes.

When conditions are met, then the actions, defined with *THEN* are "fired", which means a *new Episode* in the Process Model is created, and linked back to the current Episode being acted upon. That is, the new Episode is a clone of the current Episode, with changes made by the Consequent field of the rule which created it.

Process Rules specify *Variables* - unknowns, whereas Episodes specify *Facts* which must be *bound* (called *Rule Variable Binding*) to Variables.

## Pattern Matching (Rule Firing)
Pattern Matching is a process of matching patterns of either Actors, Relations, or States in any Episode with those of the same fields in candidate Process Rules.

A first test is this:

Consider the Predicates found in an Episode's Actors. Only Rules which *can match* those Predicates are eligible to be tested.  If actor tests are satisfied, we then bind all the Variables in the Rule to the Facts in the Episode; those bindings will then be used when looking at Relations and States.

### Complexity #1: Sentence Truth
Sentences in both Rules and Episodes can be either `true` or `false` which makes an enormous difference in how the inference engine processes them.
### Complexity #2: Rule Clause Polarity
Rules clause can be either positive, e.g. *IF*, or they can be negative, e.g. *IF-NOT*

## Clauses
### Positive antecedent clauses
#### IF-ACTORS
#### IF-RELATIIONS
#### IF-STATES

### Negative antecedent clauses
#### IF-NOT-ACTORS
#### IF-NOT-RELATIONS
#### IF-NOT-STATES

### Consequent clauses
#### THEN-ACTORS
#### THEN-RELATIONS
#### THEN-STATES
#### THEN-CONJECTURE
#### THEN-SAY
