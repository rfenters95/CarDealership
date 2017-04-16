package components;

import javafx.scene.control.TextField;

public class TextOnlyTextField extends TextField {

    public TextOnlyTextField() {
        super();
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[A-Za-z]*")) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        if (replacement.matches("[A-Za-z]*")) {
            super.replaceSelection(replacement);
        }
    }

    public boolean isEmpty() {
        return getText().isEmpty();
    }

}
