 /* ========================================	PatRec.main.c=========================================== */// #include <stdio.h>			// for printf, etc.// #include <stdlib.h>// #include <Memory.h>#include "PatRec.main.h"#include "PatRec.body.h"// ____________________________________________________________ mainextern FILE	*theLog;			// the log file handleextern FILE	*TrainData;		// raw data for trainingextern FILE	*TestData;		// raw data for testingextern FILE	*TrainSetup;extern FILE	*TestSetup;extern short	numPros;FileStruct	**TestFilesH; FileStruct	**TrainFilesH;ProStruct		**TrainDataH;ProStruct		**TestDataH;AAStruct 		**AminoFrameH;Exemplars	**ExemplarH;void	doTrain 		(short num);void	doTest  		(short num);void	logSummary	(void);void	logSummary	( ){}void	doTrain (short num){		short	i;	fprintf(theLog, "Starting Training\n");		numPros = (*TrainFilesH)->numfiles;		clearData( (*TrainDataH) );	for	( i = 0; i < (*TrainFilesH)->numfiles; i++ )	{		TrainData = fopen((*TrainFilesH)->files[i].fname, "r");		if	(TrainData == NULL)		{	printf("#### Couldn't open file ");			printf( "%s\n", &(*TrainFilesH)->files[i].fname );		}		else			{				rewind(TrainData);					readData(TrainData, i, (*TrainDataH) );		}	}	trainPatRec();}void	doTest (short num){		short	i;	fprintf(theLog, "Starting Testing\n");		clearData( (*TestDataH) );	for	( i = 0; i < (*TestFilesH)->numfiles; i++ )	{		TestData = fopen((*TestFilesH)->files[i].fname, "r");		if	(TestData == NULL)		{	printf("#### Couldn't open file ");			printf( "%s\n", &(*TestFilesH)->files[i].fname );		}		else			{				rewind(TestData);						readData(TestData, i, (*TestDataH) );			studyProtein(i);	// study this protein			logSummary();		}	}		fprintf(theLog, "Game Over\n");	}void main	(){		short	numtrain, numtest;		printf("Getting Started...\n");	ExemplarH = (Exemplars **) NewHandleClear(sizeof(Exemplars));	if	( MemError () )		printf("Couldn't allocate Exemplars\n");	TestFilesH = (FileStruct **) NewHandleClear(sizeof(FileStruct));	if	( MemError () )		printf("Couldn't allocate TestFiles\n");	TrainFilesH = (FileStruct **) NewHandleClear(sizeof(FileStruct));	if	( MemError () )		printf("Couldn't allocate TrainFiles\n");	TrainDataH = (ProStruct **) NewHandleClear(MAXNUMPROS * sizeof(ProStruct));	if	( MemError () )		printf("Couldn't allocate Train Data\n");	TestDataH = (ProStruct **) NewHandleClear(MAXNUMPROS * sizeof(ProStruct));	if	( MemError () )		printf("Couldn't allocate Test Data\n");		AminoFrameH = (AAStruct **) NewHandleClear(AACOUNT *sizeof(AAStruct));	if	( MemError () )		printf("Couldn't allocate Amino Frames\n");			TrainSetup = fopen("Train.Setup",  "r");	// each line is a file name	if	(TrainSetup == NULL)		printf("#### Couldn't open training setup file\n");	else	{			numtrain = getTrainSetup();	// go get the protein datafile names to use		fclose(TrainSetup);		TestSetup = fopen("Test.Setup",  "r");	// each line is a file name		if	(TestSetup == NULL)			printf("#### Couldn't open testing setup file\n");		else		{			numtest = getTestSetup();			fclose(TestSetup);			theLog = fopen("LogFile", "w");					doTrain(numtrain);		// train it			fclose(TrainData);			doTest(numtest);			// now test it					fclose(theLog);			fclose(TestData);		}	}}