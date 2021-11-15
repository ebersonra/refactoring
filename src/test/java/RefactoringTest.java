import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static org.testng.Assert.*;

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
    public void testThisAmount() {

        Double thisAmount = Refactoring.totalAmount;

        assertNotNull(thisAmount);
        assertEquals(thisAmount, new Double(173000));
    }
}
