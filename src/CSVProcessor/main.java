package CSVProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class main {

    static final double ORIGIN_CHARGE = 0.25;
    static final String[] ORIGIN_IATA = {"LAX", "LAS", "SAN", "SFO", "SJC"};

    public static void main(String[] args) throws Exception {
        for (String origin : ORIGIN_IATA) {
            File inputFile = new File("C:\\Users\\International 2\\Desktop\\Rate overhaul\\Lufthansa\\lax_etc.csv");
            String filePath = "C:\\Users\\International 2\\Desktop\\Rate overhaul\\Lufthansa\\" + origin + "_processed.csv";
            FileWriter fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String outputString = processLine(scanner.nextLine(), origin);
                System.out.println(outputString);
                printWriter.println(outputString);
            }
            printWriter.close();
        }
    }

    static String processLine(String inputLine, String origin){
        String processedLine;
        String firstChar = inputLine.substring(0,1);
        if (firstChar.equals("#")){
            processedLine = inputLine;
        } else {
            inputLine = inputLine.replace("$", "");
            inputLine = inputLine.replace(" ", "");
            String[] inputArray = inputLine.split(",");
            inputArray[0] = origin;
            for (int i = 4; i < 8; i++){
                inputArray[i] = addOriginCharge(inputArray[i]);
            }
            inputArray[9] = addOriginCharge(inputArray[9]);
            processedLine = String.join(",", inputArray);
        }
        return processedLine;
    }

    static String addOriginCharge(String startingCharge){
        String adjustedCharge;
        try {
            double chargeDouble = Double.parseDouble(startingCharge);
            chargeDouble += ORIGIN_CHARGE;
            chargeDouble = Math.round(chargeDouble * 100);
            chargeDouble = chargeDouble / 100;
            adjustedCharge = String.format("%.2f", chargeDouble);
        }
        catch (NumberFormatException e){
            adjustedCharge = startingCharge;
        }
        return adjustedCharge;
    }

}
