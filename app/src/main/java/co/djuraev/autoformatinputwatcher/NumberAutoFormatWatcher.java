package co.djuraev.autoformatinputwatcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

class NumberAutoFormatWatcher implements TextWatcher {
  private int decimalNumbers = 2; // by default 2
  private boolean isFormatting;
  private InputFilter filter;

  private int getDecimalNumbers() {
    return decimalNumbers;
  }

  public void setDecimalNumbers(int decimalNumbers) {
    this.decimalNumbers = decimalNumbers;
  }

  private InputFilter getFilter() {
    return filter;
  }

  public void setFilter(InputFilter filter) {
    this.filter = filter;
  }

  NumberAutoFormatWatcher() {
    isFormatting = false;
  }

  public synchronized void afterTextChanged(Editable s) {
    if (isFormatting) {
      return;
    }

    if (s.length() > 0) {
      isFormatting = true;
      inputFormat(s);
      isFormatting = false;
    }
  }

  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  private void inputFormat(Editable input) {

    input.setFilters(new InputFilter[] { getFilter() });

    // if input starts with dot insert 0.
    if (input.toString().startsWith(".") && input.toString().length() == 1) {
      input.clear();
      input.append("0.");
      return;
    }

    if (input.toString().contains(".")) {

      // split amount with the dot
      String[] splicedText = input.toString().split("\\.");
      if (splicedText.length == 0) {
        return;
      }

      // limit for decimal digits input
      if (splicedText.length > 1 && splicedText[1].length() >= (getDecimalNumbers() + 1)) {
        int dotPosition = InputUtils.findDotPosition(input.toString());
        input.delete(dotPosition + (getDecimalNumbers() + 1), input.toString().length());
        return;
      }

      // formatting digits with dot
      String digits = InputUtils.extractDigits(splicedText[0]);
      int dotPosition = InputUtils.findDotPosition(input.toString());
      try {
        String formatted = InputUtils.formatWithoutDecimal(digits);
        input.replace(0, dotPosition, formatted);
      } catch (NumberFormatException nfe) {
        input.clear();
      }
    } else {
      // formatting digits without dot
      String digits = InputUtils.extractDigits(input.toString());
      try {
        String formatted = InputUtils.formatWithoutDecimal(digits);
        input.replace(0, input.length(), formatted);
      } catch (NumberFormatException nfe) {
        input.clear();
      }
    }
  }
}