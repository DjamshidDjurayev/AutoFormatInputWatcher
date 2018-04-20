package co.djuraev.autoformatinputwatcher;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class InputUtils {
  private static final String NUMBER_FORMAT = "###,###,###";
  private static final String NUMBER_FORMAT_WITH_DECIMAL = "###,###.####";
  private static final String SEPARATE_SIGN = " ";

  public static int findDotPosition(String s) {
    char[] chars = s.toCharArray();

    for (int i = 0; i < s.length(); i++) {
      if (chars[i] == '.') {
        return i;
      }
    }

    return 0;
  }

  public static String formatWithoutDecimal(String digits) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT);
    return formatter.format(new BigDecimal(digits)).replace(",", " ");
  }

  public static String formatWithDecimal(String digits, int minInt, int minFrac, int maxFrac) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT_WITH_DECIMAL);
    formatter.setMaximumFractionDigits(maxFrac);
    formatter.setMinimumFractionDigits(minFrac);
    formatter.setMinimumIntegerDigits(minInt);
    return formatter.format(new BigDecimal(digits)).replace(",", SEPARATE_SIGN);
  }

  public static String formatWithDecimal(double digits, int minInt, int minFrac, int maxFrac) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT_WITH_DECIMAL);
    formatter.setMaximumFractionDigits(maxFrac);
    formatter.setMinimumFractionDigits(minFrac);
    formatter.setMinimumIntegerDigits(minInt);
    return formatter.format(new BigDecimal(digits)).replace(",", SEPARATE_SIGN);
  }

  public static String formatWithoutDecimal(double digits) {
    NumberFormat nf = NumberFormat.getInstance(Locale.US);
    DecimalFormat formatter = (DecimalFormat) nf;
    formatter.applyPattern(NUMBER_FORMAT);
    formatter.setMaximumFractionDigits(4);
    formatter.setMinimumFractionDigits(2);
    formatter.setMinimumIntegerDigits(1);
    return formatter.format(new BigDecimal(digits)).replace(",", SEPARATE_SIGN);
  }

  public static String extractDigits(String input) {
    return input.replaceAll(" ", "");
  }

  public static int getSignCount(String input, char sign) {
    int occurrence = 0;
    char[] chars = input.toCharArray();
    for (char thisChar : chars) {
      if (thisChar == sign) {
        occurrence++;
      }
    }
    return occurrence;
  }
}
