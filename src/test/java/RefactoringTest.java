import br.com.refactoring.domain.Invoice;
import br.com.refactoring.domain.Plays;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


public class RefactoringTest {

    private static final String[] FILES_NAME = new String[]{"plays.json","invoices.json"};

    @BeforeMethod
    public void setUp() throws FileNotFoundException, URISyntaxException {
        Refactoring.main(FILES_NAME);
    }

    @Test
    public void testPlayGetAsLike() {

        Plays plays = Refactoring.plays;
        assertNotNull(plays);

        assertEquals(plays.getAsLike().getName(),"As You Like It");
    }

    @Test
    public void testPlayGetHamlet() {

        Plays plays = Refactoring.plays;
        assertNotNull(plays);

        assertEquals(plays.getHamlet().getName(),"Hamlet");
    }

    @Test
    public void testPlayGetOthello() {

        Plays plays = Refactoring.plays;
        assertNotNull(plays);

        assertEquals(plays.getOthello().getName(),"Othello");
    }

    @Test
    public void testInvoiceGetCustomer() {

        Invoice[] invoice = Refactoring.invoice;

        assertNotNull(invoice);
        assertNotEquals(invoice.length, 0);

        assertEquals(invoice[0].getCustomer(),"BigCo");
    }

    @Test
    public void testInvoiceGetPerformancesGetPlayID() {

        Invoice[] invoice = Refactoring.invoice;

        assertNotNull(invoice);
        assertNotEquals(invoice.length, 0);
        assertNotNull(invoice[0].getPerformances());
        assertEquals(invoice[0].getPerformances().size(), 3);

        assertEquals(invoice[0].getPerformances().get(0).getPlayID(),"hamlet");
        assertEquals(invoice[0].getPerformances().get(1).getPlayID(),"asLike");
        assertEquals(invoice[0].getPerformances().get(2).getPlayID(),"othello");
    }

    @Test
    public void testTotalAmount() {

        Double totalAmount = Refactoring.totalAmount;
        var format = NumberFormat.getCurrencyInstance(Locale.US);

        String formatTotalAmount = format.format(totalAmount/100);

        assertNotNull(totalAmount);
        assertNotEquals(totalAmount, Double.parseDouble("0.0"));
        assertNotNull(formatTotalAmount);
        assertNotEquals(formatTotalAmount," ");

        assertEquals(formatTotalAmount, "$1,730.00");
    }

    @Test
    public void testVolumeCredits() {

        Double volumeCredits = Refactoring.volumeCredits;

        assertNotNull(volumeCredits);
        assertNotEquals(volumeCredits, Double.parseDouble("0.0"));

        assertEquals(volumeCredits, Double.valueOf("47.0"));
    }

    @Test
    public void testResult() {
        String result = Refactoring.result;
        var format = NumberFormat.getCurrencyInstance(Locale.US);

        assertNotNull(result);
        assertNotEquals(result," ");

        assertTrue(result.contains(String.valueOf(Refactoring.volumeCredits)));
        assertTrue(result.contains(format.format(Refactoring.totalAmount/100)));
        assertTrue(result.contains(Refactoring.plays.getHamlet().getName()));
        assertTrue(result.contains(Refactoring.invoice[0].getCustomer()));
    }
}
