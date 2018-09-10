package co.djuraev.autoformatinputwatcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
  protected EditText input;
  protected NumberAutoFormatWatcher numberAutoFormatWatcher;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    input = findViewById(R.id.input);

    numberAutoFormatWatcher = new NumberAutoFormatWatcher();
    numberAutoFormatWatcher.setFilter(filter);
    numberAutoFormatWatcher.setDecimalFractions(2);
    numberAutoFormatWatcher.setIntegerFractions(9);

    input.setFilters(new InputFilter[] { filter });
    input.addTextChangedListener(numberAutoFormatWatcher);
  }

  InputFilter filter = new InputFilter() {
    @Override
    public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2,
        int i3) {

      String fullString = input.getText().toString() + source.toString();

      int dotAmount = InputUtils.getSignOccurance(fullString, '.');

      // preventing multiple dots

      if (dotAmount > 1) {
        return "";
      }

      return source;
    }
  };
}
