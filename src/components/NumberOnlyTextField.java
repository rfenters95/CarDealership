package components;

import javafx.scene.control.TextField;

public class NumberOnlyTextField extends TextField {

    public NumberOnlyTextField() {
        super();
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]*")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        if (replacement.matches("[0-9]*")) {
            super.replaceSelection(replacement);
        }
    }

    public boolean isEmpty() {
        return getText().isEmpty();
    }

}
