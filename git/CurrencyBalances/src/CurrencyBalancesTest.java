import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.Test;


public class CurrencyBalancesTest {
	
	
	CurrencyBalances currencyBalances = new CurrencyBalances();
	
	List<String> inputTransactions = Arrays.asList("USD 100", "50 CNY", "-30 GBP", "200 EUR", "40 GBP");
	
	@Test
	public void testCurrencyBalances() {
		
		inputTransactions.forEach((t) -> {
			
			String[] processedInput = currencyBalances.verifyInput(t);
			
			currencyBalances.executeTransactions(processedInput[1], Double.parseDouble(processedInput[0]));
			
		});
		

	    PriorityQueue<CurrencyVolume> expectedCcyBalances = new PriorityQueue<>((a,b) -> { return (int) (b.volume - a.volume); });

	    CurrencyVolume ccyVol1 = new CurrencyVolume("USD", 100.0);
	    CurrencyVolume ccyVol2 = new CurrencyVolume("CNY", 50.0);
	    CurrencyVolume ccyVol3 = new CurrencyVolume("GBP", 10.0);
	    CurrencyVolume ccyVol4 = new CurrencyVolume("EUR", 200.0);

		
	    expectedCcyBalances.add(ccyVol1);
		expectedCcyBalances.add(ccyVol2);
		expectedCcyBalances.add(ccyVol3);
		expectedCcyBalances.add(ccyVol4);
		
		assertEquals(ccyVol4.ccyName, currencyBalances.ccyBalances.poll().ccyName);
		assertEquals(ccyVol1.ccyName, currencyBalances.ccyBalances.poll().ccyName);
		assertEquals(ccyVol2.ccyName, currencyBalances.ccyBalances.poll().ccyName);
		assertEquals(ccyVol3.ccyName, currencyBalances.ccyBalances.poll().ccyName);

		
	}
	
}
