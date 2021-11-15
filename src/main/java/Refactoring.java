import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;

public class Refactoring {

    public static Invoice[] invoice;
    public static Plays plays;

    public static Double totalAmount;

    /**
     * compilar-testar-fazer commit
     *
     * */
    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {

        var gson = new Gson();
        var fileName = args[0];
        var bufferedReader = new BufferedReader(new FileReader(getFileFromResource(fileName).getPath()));
        plays = gson.fromJson(bufferedReader, Plays.class);

        fileName = args[1];
        var bufferedReaderInvoices = new BufferedReader(new FileReader(getFileFromResource(fileName).getPath()));
        invoice = gson.fromJson(bufferedReaderInvoices, Invoice[].class);

        statement(invoice[0], plays);
    }

    private static File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = Refactoring.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.toURI());
    }

    private static void statement(Invoice invoice, Plays plays) {
        totalAmount = 0D;
        var volumeCredits = 0D;
        var result = "Statement for "+invoice.getCustomer();
        var format = new DecimalFormat();

        var playsArray = new JSONObject(plays);

        for (var perf : invoice.getPerformances()) {
            Object json = playsArray.get(perf.getPlayID());
            var gson = new Gson();
            Play play = gson.fromJson(json.toString(), Play.class);
            var thisAmount = 0D;

            switch (play.getType()) {

                case "tragedy":
                    thisAmount = 40000D;
                    if (perf.getAudience() > 30){
                        thisAmount += 1000 * (perf.getAudience() - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000D;
                    if (perf.getAudience() > 20){
                        thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    throw new Error("Unknown type: "+play.getType());
            }

            totalAmount += thisAmount;
        }
    }
}
