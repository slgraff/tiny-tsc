CELL.Tdescription:	Components of Cells, essentials of cell biologyneed to do:		Xto improve:		XPROBLEMS:			notes:			XCHANGES:7/25/91			latest changes2/22/92			began cleanup. NewCentury 12point, TabStops=4, LineWrap=100.10/01/92			rbt:  EXCRETES, INGESTS added2/24/93			jp2: update to new TSC syntax2/12/94			rbt:  rearranging actors to put more "primitive" actors first;  the				font is really Chicago!6/12-15/94		Consistency modifications, cell biology additions6/14/96			cell adhesion moelecules--integrin, selectin, added; specific instances				in histo.t and immune.t6/14-16			Regulatory substances cleaned up; neurotransmitters organized6/30/96			Transcription factor stuff added, DNA, RNA7/03/96			cjun and cfos transcription promoter factors and binding sites added; phosphorylation7/5/96			cytoplasmic and nuclear compartment concepts added7/9/96			addressin added}{HISTO.T componentdescription:		Mammalian Histology:  Cells, Process and Primitivesneed to do:		Xto improve:		XPROBLEMS:						notes:			Requires Bioprimitive.t and Cell.t--respectively--to				be loaded prior to including.				Immune system cells and processes are in separate sub				KB--Immune.t--intended to be loaded AFTER Histo.t for				multisystem/multicompartment simulations, such as				wound healing--HBO.exp.				6/15/94:	10 point, TabStops=4, LineWrap=100.CHANGES:6/14/96			Glycoprotein, glyconjugate moved from Histo.t to bioprimitive.t				general cell adhesion molecule concepts located in cell.t; specific				molecules to be located in histo.t or specific system KBs like immune.t6/18/96			Added fibroblast growth factor family7/9/96			High endothelial venule8/3/97			Insulin-like growth factor; epidermal.growth.factor; growth factor receptors}{	structures:		function.of  function		part.of	has.part		made.by	makes		attached.to	attached.to  ????		suspended.in	suspends		enclosed.by	encloses}\ --------------------------------------------------------------------------ACTORS\	notice that some things are "part.of" bio.cell while others\	will be "part.of", say, eukaryotic.cell--which means that get methods must collect\	parts UPWARD, starting from the most-specific concept--a form of inheritence\ Primitives first, so sub.ofs wont return illegal slots; rbt.	\ there are other "membranes" out in analogy land--may need to call this "bio.membrane"\  rbt : Changed membrane and its cellular instances to cell.membrane\\ ---------------------------------------------------Organellesc:	organelle	part.of		bio.cell	my.ref		6	my.creator	jp2	sub.of		thingc:	cell.membrane	part.of		bio.cell	my.creator	rbt jp2	sub.of		thing	my.ref		4c:	cell.fragment	sub.of		thing		my.creator	rbtc:	endomembrane  \ rbt:  i think this is a pretty useless concept, at least semantically superfluous	sub.of		cell.membrane	part.of		bio.cell	my.ref		6	my.creator	jp2c:	microbody	part.of		endomembrane	sub.of		organelle	my.ref		6	my.creator	jp2\	synonym		actin.filament  rbt:  this relationship is not identityc:	plasma.membrane\	part.of	endomembrane   rbt: ugh.  endomembrane is a sloppy concept	sub.of		cell.membrane	my.ref		6	my.creator	jp2		c:	nucleolus	part.of		bio.cell	my.ref		6	my.creator	jp2	sub.of		thing					c:	ribosome	part.of			bio.cell	made.by			nucleolus	my.ref			6	my.creator		jp2 rbt	sub.of			organelle			\ pay attention to "attached.to" ********************\c:	receptor	sub.of			stuff peptide	my.creator		rbt	c:	membrane.receptor	sub.of			receptor	my.creator		rbt	c:	adhesion.molecule	sub.of			molecule membrane.receptor	my.creator		rbtc:	integrin	sub.of			adhesion.molecule	my.creator		rbtc:	selectin	sub.of			adhesion.molecule	my.creator		rbtc:	adressin	sub.of			adhesion.molecule	my.creator		rbtc:	vascular.adressin	sub.of			adressin	my.creator		rbtc:	Endoplasmic.Reticulum	part.of	endomembrane	my.ref	6	my.creator	jp2	sub.of	organelle	c:	bound.ribosome	attached.to		endoplasmic.reticulum	sub.of			ribosome	my.ref	6	my.creator	jp2c:	Cytoskeleton	part.of	bio.cell	my.ref	6	my.creator	jp2	sub.of	thingc:	Cytosol	part.of	bio.cell	my.ref	6	my.creator	jp2	sub.of	stuff\ pay attention to "suspended.in" ********************\c:	Free.Ribosome	suspended.in	cytosol	sub.of	ribosome	my.ref	6	my.creator	jp2c:	Glyoxysome	sub.of	microbody	my.ref	6	my.creator	jp2	sub.of	thingc:	Golgi.Complex	part.of	endomembrane	my.ref	6	my.creator	jp2	sub.of	organelle\ these "ends" are probably "sub.of" something such as structure\c:	Hydrophobic.End	part.of	phospholipid	my.ref	4	my.creator	jp2	sub.of	idea		\ ????c:	Hydrophylic.End	part.of	phospholipid	my.ref	4	my.creator	jp2	sub.of	idea		\ ????c:	Lumen	part.of	bio.cell	enclosed.by	endoplasmic.reticulum	my.ref	6	my.creator	jp2	sub.of	thingc:	Lysosome	part.of	endo			\ ????	my.ref	6	my.creator	jp2	sub.of	thingc:	Microfilament	sub.of	cytoskeleton	my.ref	6	my.creator	jp2c:	Microtubules	sub.of	cytoskeleton	my.ref	6	my.creator	jp2c:	Mitochondrion	sub.of	microbody	my.ref	6	my.creator	jp2	synonym	mitrocondriac:	Nuclear.Envelope	part.of	endomembrane	my.ref	6	my.creator	jp2	sub.of	thingc:	Peroxisome	sub.of	microbody	my.ref	6	my.creator	jp2c:	Phospholipid	part.of	cell.membrane	my.ref	4	my.creator	jp2	sub.of	thing\ a polysome consists of several ribosomes attached to one mRNA molecule\	which increases rate of protein synthesis\	********************how to represent this*********************????????????\c:	Polyribosome	part.of	bio.cell	attached.to	mrna	my.ref	6	my.creator	jp2	synonym		polysome	sub.of	thingc:	Rough.ER	sub.of	endoplasmic.reticulum	my.ref	6	my.creator	jp2c:	Smooth.ER	sub.of	endoplasmic.reticulum	my.ref	6	my.creator	jp2c:	Vacuole	part.of	endomembrane	my.ref	6	my.creator	jp2	sub.of	thingc:	Vesicle	part.of	endomembrane	my.ref	6	my.creator	jp2	sub.of	thingc:	cytoplasmic.compartment	sub.of	thing	my.creator	rbtc:	nuclear.compartment	sub.of	thing	my.creator	rbt\ -------------------TRANSCRIPTION FACTORS, RELATED SUBSTANCES AND GENE REGULATIONc:	chromosome	sub.of		organelle	part.of		nucleus	my.creator	rbt	c:	gene	sub.of		thing	part.of		chromosome	my.creator	rbtc:	gene.regulator	sub.of		stuff	my.creator	rbtc:	transcription.factor	sub.of		gene.regulator	my.creator	rbt		c:	promoter.site	sub.of		thing	part.of		gene	my.creator	rbtc:	promoter.kB-binding.site	sub.of		thing	part.of		promoter.site	my.creator	rbtc:	12-O-tetradecanoylphorbol.13-acetate	sub.of		regulatory.substance	my.creator	rbtc:	TPA	sub.of		regulatory.substance	synonym		12-O-tetradecanoylphorbol.13-acetate	my.creator	rbt		c:	TPA.response.element	sub.of		promoter.site	my.creator	rbtc:	TRE	sub.of	promoter.site	synonym		TPA.response.element	my.creator	rbt				c:	serum.response.factor	sub.of		peptide regulatory.substance	my.creator	rbc:	SRF	sub.of		peptide regulatory.substance	synonym		serum.response.factor	my.creator	rbtc:	SRF.dimer	sub.of		peptide regulatory.substance	my.creator	rbtc:	terniary.complex.factor	sub.of		peptide regulatory.substance	my.creator	rbtc:	TCF	sub.of		peptide regulatory.substance	synonym		terniary.complex.factor	my.creator	rbtc:	c-fos.promoter.site	sub.of		promoter.site	part.of		gene	my.creator	rbt	c:	serum.response.element	sub.of		c-fos.promoter.site	my.creator	rbtc:	SRE	sub.of		c-fos.promoter.site	synonym		serum.response.element	my.creator	rbtc:	TCF.binding.site	sub.of		c-fos.promoter.site	my.creator	rbtc:	enhancer.site	sub.of		thing	part.of		gene	my.creator	rbt	c:	deoxyribonucleic.acid	sub.of		stuff	part.of		gene	my.creator	rbtc:	DNA	sub.of		stuff	part.of		gene	synonym		deoxyribonucleic.acid	my.creator	rbtc:	ribonucleic.acid	sub.of		stuff	my.creator	rbtc:	RNA	sub.of		stuff	synonym		ribonucleic.acid	my.creator	rbtc:	nucleic.acid	sub.of		stuff	part.of		ribonucleic.acid deoxyribonucleic.acid RNA DNA	my.creator	rbtc:	Rel.protein	sub.of		transcription.factor protein	my.creator	rbtc:	nuclear.factor-kB	sub.of		Rel.protein	my.creator	rbtc:	NF-kB	sub.of		Rel.protein	synonym		nuclear.factor-kB	my.creator	rbtc:	Group.I.Rel.protein	sub.of		Rel.protein	my.creator	rbtc:	Group.II.Rel.protein	sub.of		Rel.protein	my.creator	rbtc:	Group.I.Rel.precursor	sub.of		Rel.protein	my.creator	rbtc:	Group.II.Rel.precursor	sub.of		Rel.protein	my.creator	rbt	c:	p100	sub.of		Group.I.Rel.precursor	my.creator	rbtc:	p105	sub.of		Group.I.Rel.precursor	my.creator	rbtc:	p50	sub.of		Group.I.Rel.protein	part.of		p105	my.creator	rbtc:	p52	sub.of		Group.I.Rel.protein	part.of		p110	my.creator	rbtc:	p65	sub.of		Group.II.Rel.protein	my.creator	rbtc:	Rel	sub.of		Group.II.Rel.protein	my.creator	rbtc:	RelB	sub.of		Group.II.Rel.protein	my.creator	rbtc:	Rel.protein.complex	sub.of		Rel.protein	my.creator	rbt	c:	Rel.heterodimer	sub.of		Rel.protein.complex	my.creator	rbtc:	p50-p65	sub.of		Rel.heterodimer	my.creator	rbtc:	p65-p105	sub.of		Rel.heterodimer	my.creator	rbtc:	Rel.homodimer	sub.of		Rel.protein.complex	my.creator	rbtc:	inhibitor.protein	sub.of		protein	my.creator	rbtc:	inhibitor.protein.complex	sub.of		inhibitor.protein	my.creator	rbtc:	IkB	sub.of		inhibitor.protein	my.creator	rbtc:	IkB-p50-p65	sub.of		Rel.protein.complex	my.creator	rbtc:	reactive.oxygen.species	sub.of		stuff	my.creator	rbtc:	ROS	sub.of		stuff	synonym		reactive.oxygen.species	my.creator	rbtc:	hydrogen.peroxide	sub.of		reactive.oxygen.species	my.creator	rbt	c:	H2O2	sub.of 		reactive.oxygen.species	synonym		hydrogen.peroxide	my.creator	eai	c:  H2O2.concentration	sub.of		stuff	my.creator	eai	c:	antioxidant	sub.of		stuff	my.creator	eai	c:	n.acetyl-L-Cysteine	sub.of		antioxidant	my.creator	eai	c:	iron.chelation.agent	sub.of		stuff	my.creator	eai	c: 	deferoxamine	sub.of		iron.chelation.agent	my.creator	eai	c:	DFO	sub.of		iron.chelation.agent	synonym		deferoxamine	my.creator	eai	c:	DFO.concentration	sub.of		stuff	my.creator	eai	c:	high	sub.of		idea	my.creator	eai	c:	moderate	sub.of		idea	my.creator	eai	c:	low	sub.of		idea	my.creator	eai		c:  nitric.oxide	sub.of      reactive.oxygen.species	my.creator	eaic:	protein.kinase	sub.of		enzyme	my.creator	rbtc:	protein.kinase.C	sub.of		enzyme	my.creator	rbtc:	protein-tyrosine.kinase	sub.of		enzyme	my.creator	rbt	c:  MAP.kinase	sub.of      enzyme	my.creator  eai		c:  MAPK	sub.of      enzyme	synonym		MAP.kinase	my.creator  eaic:	protooncogene		sub.of		gene	my.creator	rbtc:	c-jun	sub.of		protooncogene	my.creator	rbt	c:	c-fos	sub.of		protooncogene	my.creator	rbtc:	protooncogene.product	sub.of		protein gene.regulator	my.creator	rbt	c:	cJun	sub.of		protooncogene.product	my.creator	rbtc:	vJun	sub.of		protooncogene.product	my.creator	rbtc:	JunB	sub.of		protooncogene.product	my.creator	rbt	c:	cFos	sub.of		protooncogene.product	my.creator	rbtc:	vFos	sub.of		protooncogene.product	my.creator	rbtc:	FosB	sub.of		protooncogene.product	my.creator	rbtc:	activator.protein-1	sub.of		transcription.factor 	my.creator	rbtc:	AP-1	sub.of		transcription.factor	synonym		activator.protein-1	my.creator	rbtc:	Fos-Jun	sub.of		activator.protein-1	my.creator	rbtc:	Jun-Jun	sub.of		activator.protein-1	my.creator	rbt	c:  Proviral.gene	sub.of		gene	my.creator	eai\ --------------------------------------------------------------------------PROCESSEScomment:c:	Anabolic.Pathc:	ATP.Cyclec:	Cellular.Respiration	function.of	mitochondrionc:	Catabolic.Pathc:	Differentiates	sub.of	relationc:	Differentiationcomment;	c:	Digests	sub.of			relation\	inverse.slot	digested.by   rbt: this causes problems because digested.by follows	c:	DIGESTED.BY	sub.of	relation	inverse.slot	digests	c:	Metabolizes	sub.of	relation\	inverse.slot	metabolized.byc:	METABOLIZED.BY	sub.of	relation	inverse.slot	metabolizes	c:	phosphorylates	sub.of			relation	c:	phosphorylated	sub.of			ideac:	unbound	sub.of			ideac:	binding	sub.of			ideac:	binds.to	sub.of			relationc:	produced	sub.of			ideac:	formed	sub.of			idea	c:	activated	sub.of			ideac:	degraded	sub.of			idea	c:	translocated.to	sub.of			relation	c:	expresses	sub.of			relation	c:	eliminates	sub.of			relation	c:	reduces	sub.of			relation	c:  oxidizes	sub.of			relation	comment:c:	Metabolismcomment;\ probably belongs in molbio kb\c:	Presents	sub.of	relation\	inverse.slot	presented.by  rbt: again, transitivity trouble because presented.by followsc:	PRESENTED.BY	sub.of	relation	inverse.slot	presentscomment:c:	Presentationcomment;\  necessary for antigen, receptor, MHC, channel presentation on membrane surfacescomment:c:	Protein.Synthesiscomment;c:	Synthesizes	sub.of	relation\ gen for protein, carbohydrate, lipid synthesis\ --------------------------------------------------------------------Transportcomment:c:	Diffusion	sub.of	phys.processc:	Endocytosis	sub.of	bioprocesscomment;c:	Excretes	sub.of		relation	my.creator	jp2 rbtcomment:c:	Exocytosiscomment;c:	INGESTS	level			basic	sub.of			relation	my.creator		rbtcomment:c:	Ion.Transport	sub.of	bioprocessc:	Osmosis	sub.of	diffusion	my.ref	6	my.creator	jp2c:	Protein.Transportcomment;c:	Secretes	sub.of	relationcomment:c:	Selective.Permeabilitycomment;\ --------------------------------------------------------------------------\ --------------------------------------------------------------------------{ HISTO.T component }\ ----------------------------------TISSUES, TISSUE COMPONENTS, AND STRUCTURESc:	ground.substance	sub.of		stuff	my.creator	rbtc:	extracellular.matrix	part.of		ground.substance	my.creator	rbt	c:	connective.tissue	sub.of		tissue	my.creator	rbtc:	connective.tissue.matrix	part.of		connective.tissue extracellular.matrix	my.creator	rbtc:	granulation.tissue	sub.of		tissue	my.creator	rbt	c:	skin	sub.of		organ	my.creator	rbtc:	epithelium	sub.of		tissue	my.creator	rbtc:	mesothelium	sub.of		epithelium	my.creator	rbtc:	stratified.squamous.epithelium	sub.of		epithelium	my.creator	rbtc:	epidermis	sub.of		stratified.squamous.epithelium	part.of		skin	my.creator	rbtc:	stratum.corneum	part.of		epidermis	my.creator	rbtc:	stratum.malpighii	part.of		epidermis	my.creator	rbt	c:	stratum.granulosum	part.of		stratum.malpighii	my.creator	rbtc:	stratum.spinosum	part.of		stratum.malpighii	my.creator	rbtc:	stratum.germinativum	part.of		stratum.malpighii	my.creator	rbt			c:	basal.lamina	part.of		epidermis	my.creator	rbtc:	dermis	sub.of		tissue			part.of		skin	my.creator	rbt	c:	dermal-epidermal.junction	part.of		dermis epidermis	my.creator	rbt	c:	rete.ridge	part.of		epidermis	my.creator	rbt	c:	dermal.papilla	part.of		dermis	my.creator	rbtc:	dermal.appendage	sub.of		cell.aggregation	part.of		dermis	my.creator	rbt		c:	sweat.gland	sub.of		gland	my.creator	rbtc:	apocrine.sweat.gland	sub.of		sweat.gland	my.creator	rbtc:	eccrine.sweat.gland	sub.of		sweat.gland	my.creator	rbt	c:	sebaceous.gland	sub.of		Dermal.appendage gland	my.creator	rbtc:	hair.follicle	sub.of		Dermal.appendage	my.creator	rbtc:	vessel	sub.of		thing	my.creator	rbt	c:	lymphatic.vessel	sub.of		vessel	my.creator	rbt	c:	blood.vessel	sub.of		vessel	part.of		Circulatory.system	my.creator	rbtc:	artery	sub.of		blood.vessel	my.creator	rbtc:	small.artery	sub.of		artery	contained.in	dermis	my.creator	rbtc:	arteriole	sub.of		artery	contained.in	dermis	my.creator	rbtc:	vein	sub.of			blood.vessel	my.creator		rbtc:	small.vein	sub.of			vein	contained.in	dermis	my.creator		rbtc:	venule	sub.of			vein	my.creator		rbtc:	high.endothelial.venule	sub.of			venule	my.creator		rbtc:	HEV	sub.of			venule	synonym			high.endothelial.venule	my.creator		rbt	c:	capillary	sub.of		arteriole	my.creator	rbtc:	capillary.bed	sub.of		arteriole	my.creator	rbt	c:	capillary.network	sub.of		arteriole	my.creator	rbt	c:	endothelium	sub.of		epithelium	part.of		blood.vessel	my.creator	rbtc:	endothelial.cell	sub.of		bio.cell	part.of		endothelium	my.creator	rbtc:	muscle	sub.of		tissue	my.creator	rbtc:	smooth.muscle	sub.of		muscle	my.creator	rbtc:	vascular.smooth.muscle	sub.of		smooth.muscle	part.of		blood.vessel	my.creator	rbtc:	pleura	sub.of		mesothelium	my.creator	rbt	\ ----------------------------------CELL TYPESc:	angioblast	sub.of		bio.cell	my.creator	rbtc:	chondrocyte	sub.of		bio.cell	my.creator	rbtc:	fibroblast	sub.of		bio.cell	my.creator	rbt	c:	myofibroblast	sub.of		fibroblast	my.creator	rbtc:	keratinocyte	sub.of		bio.cell	part.of		epidermis	my.creator	rbt		c:	mast.cell	sub.of		bio.cell	my.creator	rbt	c:	melanocyte	sub.of		bio.cell	part.of		epidermis	my.creator	rbt	c:	merkel.cell	sub.of		bio.cell	part.of		epidermis	my.creator	rbt	c:	meuron	sub.of		bio.cell	my.creator	rbt		c:	osteoblast	sub.of		bio.cell	my.creator	rbt	c:	osteoclast	sub.of		bio.cell	my.creator	rbt\ ----------------------------------CELL AND TISSUE-SPECIFIC MOLECULES AND SUBSTANCESc:	structural.protein	sub.of		protein	my.creator	rbt	c:	collagen	sub.of		structural.protein	part.of		connective.tissue.matrix	my.creator	rbt	c:	elastin	sub.of		structural.protein	part.of		connective.tissue.matrix	my.creator	rbt	c:	procollagen	sub.of		protein	my.creator	rbt	c:	tropocollagen	sub.of		protein	my.creator	rbtc:	keratin	sub.of		protein	my.creator	rbt	c:	inflammatory.peptide	sub.of		peptide	my.creator	rbt	c:	growth.factor	sub.of		regulatory.peptide	my.creator	rbtc:	epidermal.growth.factor	sub.of		growth.factor	my.creator	rbtc:	EGF	sub.of		growth.factor	synonym		epidermal.growth.factor	my.creator	rbtc:	EGF	sub.of		growth.factor	synonym		epidermal.growth.factor	my.creator	rbtc:	fibroblast.growth.factor	sub.of		growth.factor	my.creator	rbt	c:	basic.fibroblast.growth.factor	sub.of		fibroblast.growth.factor	my.creator	eai	c:	bFGF	sub.of		fibroblast.growth.factor	synonym		basic.fibroblast.growth.factor	my.creator	eai	c:	FGF-1	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-2	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-3	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-4	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-5	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-6	sub.of		fibroblast.growth.factor	my.creator	rbtc:	FGF-7	sub.of		fibroblast.growth.factor	my.creator	rbtc:	hepatocyte.growth.factor	sub.of		growth.factor	my.creator	rbtc:	HGF	sub.of		growth.factor	synonym		hepatocyte.growth.factor	my.creator	rbtc:	insulin-like.growth.factor-1	sub.of		growth.factor	my.creator	rbtc:	IGF-1	sub.of		growth.factor	synonym		insulin-like.growth.factor-1	my.creator	rbtc:	platelet-derived.growth.factor	sub.of		growth.factor	my.creator	rbtc:	PGDF	sub.of		growth.factor	synonym		platelet-derived.growth.factor	my.creator	rbtc:	cytokine	my.creator	jp2	my.ref		2	sub.of		regulatory.peptidec:	membrane.receptor	my.creator	rbt	sub.of		proteinc:	cytokine.receptor	my.creator	rbt jp2	my.ref		2	sub.of		membrane.receptorc:	EGF.receptor	my.creator	rbt	my.ref		2	sub.of		membrane.receptor protein-tyrosine.kinasec:	ErbB	my.creator	rbt	synonym		EGF.receptor	sub.of		membrane.receptor protein-tyrosine.kinasec:	HGF.receptor	my.creator	rbt	my.ref		2	sub.of		membrane.receptor protein-tyrosine.kinasec:	Met	my.creator	rbt	synonym		HGF.receptor	sub.of		membrane.receptor protein-tyrosine.kinasec:	IGF-1.receptor	my.creator	rbt	my.ref		2	sub.of		membrane.receptor protein-tyrosine.kinasec:	PGDF.receptor	my.creator	rbt	my.ref		2	sub.of		membrane.receptor protein-tyrosine.kinasec:	kinin	sub.of		inflammatory.peptide	my.creator	rbt		c:	bradykinin	sub.of		kinin	my.creator	rbt			c:	fibronectin	sub.of		glycoprotein	part.of		connective.tissue.matrix	my.creator	rbt	c:	laminin	sub.of		glycoprotein	part.of		connective.tissue.matrix	my.creator	rbt			c:	hyaluronic.acid	sub.of		proteoglycan	my.creator	rbtc:	chondroitin.sulfate	sub.of		proteoglycan	my.creator	rbtc:	dermatan.sulfate	sub.of		proteoglycan	my.creator	rbtc:	keratan.sulfate	sub.of		proteoglycan	my.creator	rbtc:	heparin.sulfate	sub.of		proteoglycan	my.creator	rbtc:	epidermal.growth.factor	sub.of		cytokine growth.factor	my.creator	rbt	c:	angiogenic.factor	sub.of		growth.factor	my.creator	rbt	c:	angiogenesis.factor	sub.of		growth.factor	synonym		angiogenic.factor	my.creator	rbt\ ----------------------------------CNS-SPECIFIC SUBSTANCES\ here until separate neurobiology knowledge base elaboratedc:	neurotransmitter	sub.of		regulatory.substance	my.creator	rbtc:	neuromodulator	sub.of		regulatory.substance	my.creator	rbtc:	monoamine	my.creator	rbt	my.ref		2	sub.of		neurotransmitterc:	serotonin	my.creator	rbt	sub.of		monoaminec:	5-HT	my.creator	rbt	synonym		serotonin	sub.of		monoaminec:	catecholamine	my.creator	jp2 rbt	my.ref		2	sub.of		monoamine neuromodulatorc:	dopamine	sub.of		catecholamine	my.creator	rbt	c:	epinephrine	sub.of		catecholamine	my.creator	rbtc:	adrenalin	sub.of		catecholamine	my.creator	jp2 rbt	synonym		epinephrine	my.ref		2 4	made.by		adrenal.medullac:	norepinephrine	sub.of		catecholamine	my.creator	rbt	my.ref		2 4	made.by		adrenal.medullac:	noradrenalin	sub.of		catecholamine	my.creator	rbt	synonym		norepinephrinec:	neuropeptide	sub.of		neurotransmitter peptide	my.creator	rbtc:	substance.P	sub.of		neuropeptide	my.creator	rbtc:	vasoactive.intestinal.peptide	sub.of		neuropeptide	my.creator	rbtc:	VIP	sub.of		neuropeptide	synonym		vasoactive.intestinal.peptide	my.creator	rbtc:	cholecystokinin	sub.of		neuropeptide	my.creator	rbtc:	CCK	sub.of		neuropeptide	synonym		cholecystokinin	my.creator	rbtc:	excitatory.amino.acid	sub.of		neurotransmitter	my.creator	rbtc:	inhibitory.amino.acid	sub.of		neurotransmitter	my.creator	rbtc:	L-aspartate	sub.of		excitatory.amino.acid	my.creator	rbtc:	L-glutamate	sub.of		excitatory.amino.acid	my.creator	rbtc:	L-glycine	sub.of		inhibitory.amino.acid	my.creator	rbtc:	gamma.amino.butyric.acid	sub.of		inhibitory.amino.acid	my.creator	rbt\ --------------------------------------------------------------------------