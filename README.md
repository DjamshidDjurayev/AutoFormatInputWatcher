# AutoFormatInputWatcher
This repository contains an exampe project with input watcher for auto formatting digits in edit text. 
#### It's fast and simpe in use, supports fast delete and insert. Enjoy using it. ####

# Demo with comma: 

![alt text][logo]

[logo]: https://github.com/DjamshidDjurayev/AutoFormatInputWatcher/blob/master/format_with_coma.gif

# Demo with space: 

![alt text][logo2]

[logo2]: https://github.com/DjamshidDjurayev/AutoFormatInputWatcher/blob/master/format_with_space.gif

# How to use it: 
``` javaScript
    EditText input = findViewById(R.id.input);

    NumberAutoFormatWatcher numberAutoFormatWatcher = new NumberAutoFormatWatcher();
    numberAutoFormatWatcher.setFilter(filter);
    numberAutoFormatWatcher.setDecimalFractions(2); // set decimal fractions
    numberAutoFormatWatcher.setIntegerFractions(9); // set integer fractions

    input.setFilters(new InputFilter[] { filter });
    input.addTextChangedListener(numberAutoFormatWatcher);
    
    InputFilter filter = new InputFilter() {
    @Override
    public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2,
        int i3) {

      String fullString = input.getText().toString() + source.toString();

      int dotAmount = InputUtils.getSignCount(fullString, '.');

      // preventing multiple dots

      if (dotAmount > 1) {
        return "";
      }

      return source;
    }
  };
```

``` xml
  <EditText
      android:id="@+id/input"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:inputType="numberDecimal"
      android:singleLine="true"
      />
```

# What's new: 
``` 
-prevent symbols entering (while paste)
-fixed crash (while paste symbols)
-set limit for integer fractions
-set limit for decimal fractions
```
