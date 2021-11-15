import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class Refactoring {

    public static Invoice[] invoice;
    public static Plays plays;

    public static Double totalAmount;
    public static Double volumeCredits;

    public static String result;

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
        volumeCredits = 0D;
        result = "Statement for "+invoice.getCustomer()+"\n";
        var format = NumberFormat.getCurrencyInstance(Locale.US);

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

            // soma créditos por volume
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // soma um crédito extra para cada dez espectadores de comédia
            if ("comedy".equalsIgnoreCase(play.getType())){
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }

            //exibe a linha para esta requisiçao
            result += play.getName() + ": " + format.format(thisAmount/100) + " (" + perf.getAudience() + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is "+format.format(totalAmount/100)+"\n";
        result += "You earned "+volumeCredits+" credits\n";

        System.out.println(result);
    }
}
