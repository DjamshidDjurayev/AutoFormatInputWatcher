package co.djuraev.autoformatinputwatcher;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class InputUtils {
  private static final String NUMBER_FORMAT = "###,###,###";
  private static final String NUMBER_FORMAT_WITH_DECIMAL = "###,###.####";

  private static int minIntegers = 1;
  private static int minFractions = 2;
  private static int maxFractions = 2;

  public static String getCurrencySymbol(String currency) {
    switch (currency.toUpperCase()) {
      case "USD":
        return "$";

      default:
        return currency;
    }
  }

  public static String stripExceptNumbers(String str, boolean includePlus) {
    StringBuilder res = new StringBuilder(str);
    String phoneChars = "0123456789";
    if (includePlus) {
      phoneChars += "+";
    }
    for (int i = res.length() - 1; i >= 0; i--) {
      if (!phoneChars.contains(res.substring(i, i + 1))) {
        res.deleteCharAt(i);
      }
    }
    return res.toString();
  }

  public static String stripSpaces(String str) {
    StringBuilder res = new StringBuilder(str);
    String amountChats = "0123456789.";
    for (int i = res.length() - 1; i >= 0; i--) {
      if (!amountChats.contains(res.substring(i, i + 1))) {
        res.deleteCharAt(i);
      }
    }
    return res.toString();
  }

  public static int findDotPosition(String s) {
    char[] chars = s.toCharArray();

    for (int i = 0; i < s.length(); i++) {
      if (chars[i] == '.') {
        return i;
      }
    }

    return 0;
  }

  /**
   * Format string with space (integers).
   * */

  public static String formatWithoutDecimal(String digits) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT);
    return formatter.format(new BigDecimal(digits)).replace(",", " ");
  }

  /**
   * Format string with space (fractions).
   * */

  public static String formatWithDecimal(String digits) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT_WITH_DECIMAL);
    formatter.setMaximumFractionDigits(maxFractions);
    formatter.setMinimumFractionDigits(minFractions);
    formatter.setMinimumIntegerDigits(minIntegers);
    return formatter.format(new BigDecimal(digits)).replace(",", " ");
  }

  /**
   * Format double with space (fractions)
   * */

  public static String formatWithDecimal(double digits) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT_WITH_DECIMAL);
    formatter.setMaximumFractionDigits(maxFractions);
    formatter.setMinimumFractionDigits(minFractions);
    formatter.setMinimumIntegerDigits(minIntegers);
    return formatter.format(new BigDecimal(digits)).replace(",", " ");
  }

  public static String extractDigits(String input) {
    return input.replaceAll(" ", "");
  }

  private static int getCharOccurrence(String input, char c) {
    int occurrence = 0;
    char[] chars = input.toCharArray();
    for (char thisChar : chars) {
      if (thisChar == c) {
        occurrence++;
      }
    }
    return occurrence;
  }

  public static int getSignOccurance(String input, char sign) {
    return getCharOccurrence(input, sign);
  }
}
