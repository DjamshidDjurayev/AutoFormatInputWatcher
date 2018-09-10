package co.djuraev.autoformatinputwatcher;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;

class NumberAutoFormatWatcher implements TextWatcher {
  private int decimalFractions = 2; // by default 2
  private int integerFractions = 9; // by default 9
  private boolean isFormatting;
  private InputFilter filter;

  public int getIntegerFractions() {
    return integerFractions;
  }

  public void setIntegerFractions(int integerFractions) {
    this.integerFractions = integerFractions;
  }

  private int getDecimalFractions() {
    return decimalFractions;
  }

  public void setDecimalFractions(int decimalFractions) {
    this.decimalFractions = decimalFractions;
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
    input.setFilters(new InputFilter[] { filter });

    if (input.toString().startsWith(".") && input.toString().length() == 1) {
      input.clear();
      input.append("0.");
      return;
    }

    if (input.toString().contains(".")) {

      String[] splicedText = input.toString().split("\\.");
      if (splicedText.length == 0) {
        return;
      }

      String strippedIntegerText = InputUtils.stripSpaces(splicedText[0]);

      // set limit for integer input with decimals
      if (strippedIntegerText.length() > integerFractions) {
        strippedIntegerText = strippedIntegerText.substring(0, integerFractions);
        strippedIntegerText = InputUtils.formatWithoutDecimal(strippedIntegerText);

        try {
          input.replace(0, splicedText[0].length(), strippedIntegerText);
        } catch (NumberFormatException nfe) {
          input.clear();
        }

        // when pasting value like 1 000 000 000.999 we should check for digits limitations
        // both integers and decimals
        if (!(splicedText.length > 1 && splicedText[1].length() > decimalFractions)) {
          return;
        }
      }

      String strippedDecimalText = "";

      if (splicedText.length > 1) {
        strippedDecimalText = InputUtils.stripSpaces(splicedText[1]);
      }

      // set limit for decimal input
      if (strippedDecimalText.length() > decimalFractions) {
        int dotPosition = InputUtils.findDotPosition(input.toString());
        strippedDecimalText = strippedDecimalText.substring(0, decimalFractions);

        try {
          input.replace(dotPosition + 1, input.length(), strippedDecimalText);
        } catch (NumberFormatException nfe) {
          input.clear();
        }

        return;
      } else {
        int newDotPosition = InputUtils.findDotPosition(input.toString());
        input.replace(newDotPosition + 1, input.length(), strippedDecimalText);
      }

      String digits = InputUtils.stripSpaces(splicedText[0]);
      int dotPosition = InputUtils.findDotPosition(input.toString());
      try {
        String formatted = InputUtils.formatWithoutDecimal(digits);
        input.replace(0, dotPosition, formatted);
      } catch (NumberFormatException nfe) {
        input.clear();
      }
    } else {
      String digits = InputUtils.stripSpaces(input.toString());

      // set limit for integer input without decimals
      if (digits.length() > integerFractions) {
        digits = digits.substring(0, integerFractions);
        digits = InputUtils.formatWithoutDecimal(digits);

        try {
          input.replace(0, input.length(), digits);
        } catch (NumberFormatException nfe) {
          input.clear();
        }
        return;
      }

      try {
        String formatted = InputUtils.formatWithoutDecimal(digits);
        input.replace(0, input.length(), formatted);
      } catch (NumberFormatException nfe) {
        input.clear();
      }
    }
  }

}