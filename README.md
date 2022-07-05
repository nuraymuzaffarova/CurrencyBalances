# CurrencyBalances


Program can be run either by passing the argument of a file to read the transactions from or by running without any args.

If no args provided, it will ask user to input the transaction and click enter. 

Assumptions: 

 - User enters only valid currencies (no predefined or accepter ccys are set)
 - Ccy is always 3 characters 
 - Ccys are capitalised upon printing 
 - Transaction amount and Ccy are entered with a space inbetween 
 - Input is accepted in any order - CCY Amount or Amount CCY 
 - If file is passed, it should have each Transaction data in a new line - same assumptions as above apply when reading from file. 
 
 
 The publisher is publishing the balance of each CCY every 60 seconds, in descending order. 
 0 balance won't be published 
