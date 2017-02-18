package util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IllegalEventMonitor implements ChangeListener<String> {

    private TextField textField;

    public IllegalEventMonitor(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue.length() > 0) {
            char c = newValue.charAt(newValue.length() - 1);
            if (Character.isDigit(c)) {
                textField.setText(oldValue);
            }
        }
    }
}
