\ upper

c: root

c: thing
	subOf root

c: stuff
	subOf root
	
c: idea
	subOf root
	
c: relation
	subOf thing
	
c: slot
	subOf relation

c: taxonomicSlot
	subOf slot
	
c: informationSlot
	subOf slot
	
c: subOf
	instanceOf taxonomicSlot
	
c: hasSubs
	instanceOf taxonomicSlot
	inverseSlot	subOf
	
c: instanceOf
	instanceOf	taxonomicSlot
	
c: hasInstances
	instanceOf taxonomicSlot
	inverseSlot	instanceOf
	
c: inverseSlot
	instanceOf	slot

c: synonym
	instanceOf	slot
	inverseSlot	synonym
	
c: myRef
	instanceOf	slot
	
c: myCreator
	instanceOf	slot
	
c: partonomicSlot
	subOf		slot
	
c: partOf
	instanceOf	partonomicSlot
	
c: hasParts
	instanceOf	partonomicSlot
	inverseSlot	partOf
	
c: attachedTo
	inverseSlot	attachedTo
	instanceOf	slot
	
c: functionOf
	instanceOf	slot
	
c: hasFunction
	instanceOf	slot
	inverseSlot	functionOf

c: viewOf
	instanceOf	slot

c: hasViews
	instanceOf	slot
	inverseSlot	viewOf

c: disjointFrom
	instanceOf	slot
	inverseSlot	disjointFrom

c: rule
	subOf	thing
	
c: processRule
	subOf	rule
	
c: agendaTask
	subOf	thing
	
c: model
	subOf 	thing
	
c: qpSlot
	subOf	slot
	
c: actors
	instanceOf	qpSlot
	
c: relations
	instanceOf	qpSlot
	
c: states
	instanceOf	qpSlot
	
c: ifActors
	instanceOf	qpSlot

c: ifNotActors
	instanceOf	qpSlot
	
c: ifRelations
	instanceOf	qpSlot

c: ifNotRelations
	instanceOf	qpSlot
	
c: ifStates
	instanceOf	qpSlot
	
c: ifRun
	instanceOf	qpSlot

c: ifNotRun
	instanceOf	qpSlot


c: ifNotStates
	instanceOf	qpSlot
	
c: thenCreate
	instanceOf	qpSlot
	
c: thenActors
	instanceOf	qpSlot
	
c: thenRelates
	instanceOf	qpSlot
	
c: thenStates
	instanceOf	qpSlot
	
c: thenSay
	instanceOf	qpSlot
	
c: thenConjecture
	instanceOf	qpSlot

c: thenRun
	instanceOf	qpSlot

c: thenTerminate
	instanceOf	qpSlot
	