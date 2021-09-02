import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    final static BigDecimal MOD_97 = BigDecimal.valueOf(97);
    final static Integer MAX_IBAN_LENGTH = 34;

    public static void main(String[] args) {

        System.out.println("Hello, this is IBAN validator");

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Please enter 1 if you want to check if IBAN valid");
            System.out.println("2. Please enter 2 if you want to import file with IBANs");
            System.out.println("Enter q if you want to quit");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.println("Please enter IBAN");
                String iban = scanner.next().toUpperCase();

                if (isIbanValid(iban)) {
                    System.out.println("IBAN " + iban + " is valid");
                } else {
                    System.out.println("IBAN " + iban + " is invalid");
                }

            } else if (input.equals("2")) {
                System.out.println("Please enter path where is your file with IBANs");
                String path = scanner.next();
                validateIbanFromFile(path);
            } else if (input.equals("q")) {
                System.out.println("Thank you for using iban validator! Bye!");
                scanner.close();
                break;
            }
        }
    }

    private static void validateIbanFromFile(String path) {
        List<String> data = IOUtils.readFileIntoList(path);
        List<String> validatedData = new ArrayList<>();
        boolean isIbanValid;

        for (int i = 0; i < data.size(); i++) {
            isIbanValid = isIbanValid(data.get(i));
            validatedData.add(data.get(i) + ";" + Boolean.toString(isIbanValid));
        }

        String fileName = path.replace(".", ".out.");
        IOUtils.saveDataIntoFile(fileName, validatedData);
        System.out.println("Please see result: " + fileName);
    }

    public static boolean isIbanValid(String iban) {

        return isCorrectLength(iban)
                && isCorrectCountryCode(iban)
                && isTwoDigits(iban)
                && isRemainderEquals1(iban);
    }

    public static boolean isCorrectLength(String iban) {
        return iban.length() <= MAX_IBAN_LENGTH;
    }

    public static boolean isCorrectCountryCode(String iban) {
        return Character.isLetter(iban.charAt(0)) && Character.isLetter(iban.charAt(1));
    }

    public static boolean isTwoDigits(String iban) {
        String ibanDigits = iban.substring(2, 4);

        return ibanDigits.matches("\\d+");
    }

    public static boolean isRemainderEquals1(String iban) {
        String rearrangedIban = iban.substring(4) + iban.substring(0, 4);
        BigDecimal ibanAsInteger = convertIbanToInteger(rearrangedIban);

        return ibanAsInteger.divideAndRemainder(MOD_97)[1].equals(BigDecimal.valueOf(1));
    }

    private static BigDecimal convertIbanToInteger(String iban) {
        for (int i = 0; i < iban.length(); i++) {
            char digit = iban.charAt(i);
            if (!Character.isDigit(digit)) {
                iban = iban.replace(String.valueOf(digit), Integer.toString((int) digit - 55));
            }
        }

        return new BigDecimal(iban);
    }
}
