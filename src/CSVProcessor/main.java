package CSVProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class main {

    static final double ORIGIN_CHARGE = 0.10;

    public static void main(String[] args) throws Exception {
        File inputFile = new File("C:\\Users\\International 2\\Desktop\\Rate overhaul\\Lufthansa\\ord_etc.csv");
        FileWriter fileWriter = new FileWriter("C:\\Users\\International 2\\Desktop\\Rate overhaul\\Lufthansa\\ord_etc_processed.csv");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()){
            String outputString = processLine(scanner.nextLine());
            System.out.println(outputString);
            printWriter.println(outputString);
        }
        printWriter.close();

    }

    static String processLine(String inputLine){
        String processedLine;
        String firstChar = inputLine.substring(0,1);
        if (firstChar.equals("#")){
            processedLine = inputLine;
        } else {
            inputLine = inputLine.replace("$", "");
            inputLine = inputLine.replace(" ", "");
            String inputArray[] = inputLine.split(",");
            for (int i = 3; i < 7; i++){
                inputArray[i] = addOriginCharge(inputArray[i]);
            }
            inputArray[8] = addOriginCharge(inputArray[8]);
            processedLine = String.join(",", inputArray);
        }
        return processedLine;
    }

    static String addOriginCharge(String startingCharge){
        //final double ORIGIN_CHARGE = 0.10;
        String adjustedCharge;
        try {
            double chargeDouble = Double.parseDouble(startingCharge);
            chargeDouble += ORIGIN_CHARGE;
            chargeDouble = Math.round(chargeDouble * 100);
            chargeDouble = chargeDouble / 100;
            adjustedCharge = Double.toString(chargeDouble);
        }
        catch (NumberFormatException e){
            adjustedCharge = startingCharge;
        }
        return adjustedCharge;
    }

}
