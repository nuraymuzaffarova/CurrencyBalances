import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class CurrencyBalances {
    
    HashMap<String, CurrencyVolume> myMap = new HashMap<>();  
    
    //to print balances in descending order 
    PriorityQueue<CurrencyVolume> ccyBalances = new PriorityQueue<>((a,b) -> { return (int) (b.volume - a.volume); });
    
    public void executeTransactions(String ccy, Double volume) {
        
        if(myMap.containsKey(ccy)) {    
        	CurrencyVolume cur = myMap.get(ccy);
            ccyBalances.remove(cur);
            cur.volume += volume;
            ccyBalances.add(cur);  
        }
        else {
        	CurrencyVolume curCcy = new CurrencyVolume(ccy, volume);
            ccyBalances.add(curCcy);
            myMap.put(ccy, curCcy);
        }           
    }
    
    public static boolean isNumeric(String str) { 
    	  try {  
    	    Double.parseDouble(str);  
    	    return true;
    	  } catch(NumberFormatException e) {  
    	    return false;  
    	  }  
    	}
    
    public static String[] verifyInput(String input) {

	    //Making an assumption here that CCY and Amount are entered with a space in between
	    //CCY should always have 3 characters 
    	//I assume user enters valid currencies
    	
    	String[] splited = input.split("\\s+");
    	String[] emptyRes = {};
    	String[] res = new String[2];
    	
    	if(splited.length == 2) {
			//always return res string with amount first, followed by the ccy 
    		if(isNumeric(splited[0]) && splited[1].length() == 3) {
    			 res[0] = splited[0];
    		     res[1] = splited[1].toUpperCase();
    		     return res;
    		}
    		else if(isNumeric(splited[1]) && splited[0].length() == 3) {
    			 res[0] = splited[1];
    		     res[1] = splited[0].toUpperCase();
    		     return res;
    		}
    		
    		else return emptyRes;	
    	}
    	else return emptyRes;
    }
  
	public static void main(String[] args) {
	    
		CurrencyBalances ccyBalances = new CurrencyBalances();
		
		Runnable balancePublisher = new Runnable() {
		    public void run() {
				Iterator<CurrencyVolume> i = ccyBalances.ccyBalances.iterator();
				 
		        while(i.hasNext()){
		        	CurrencyVolume c = i.next();
		        	if(c.volume != 0)
		        		System.out.println(c.ccyName + " " + c.volume);
		        }
		    }
		   
		};
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(balancePublisher, 0, 60, TimeUnit.SECONDS);
		
		if (args.length == 0) {
			while(true) {
			
			    System.out.println("Enter currency and amount");
		
			    Scanner inputScanner = new Scanner(System.in); 
		
			    String userInput = inputScanner.nextLine();  // Read user input
			    
			    if(userInput.equals("quit")) {
			    	System.exit(0);
			    	executor.shutdown();
			    	inputScanner.close();
			    	break;
			    }
			
			    String[] processedInput = ccyBalances.verifyInput(userInput);
			    if (processedInput.length != 2) {
			        System.out.println("Invalid transaction");
		    	}
			    else {
			    	ccyBalances.executeTransactions(processedInput[1], Double.parseDouble(processedInput[0]));
			    }
			}
		}
		else {
			try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] processedInput = ccyBalances.verifyInput(line);
				    if (processedInput.length != 2) {
				        System.out.println("Invalid input");
			    	}
				    else {
				    	ccyBalances.executeTransactions(processedInput[1], Double.parseDouble(processedInput[0]));
				    }
			    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}