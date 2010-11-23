package matt;

import javax.swing.*;

public class MyRunnerSettings {
    private JPanel rootComponent;
    private JTextField mainClass;
    private JTextField moduleNameOfTheRunner;

    public void setData(MyRunnerComponent data) {
        mainClass.setText(data.getMainClassName());
        moduleNameOfTheRunner.setText(data.getModuleNameOfRunner());
    }

    public void getData(MyRunnerComponent data) {
        data.setMainClassName(mainClass.getText());
        data.setModuleNameOfRunner(moduleNameOfTheRunner.getText());
    }

    public JComponent getRootComponent() {
        return rootComponent;
    }

    public boolean isModified(MyRunnerComponent data) {
        return isModified(mainClass, data.getMainClassName()) || isModified(moduleNameOfTheRunner, data.getModuleNameOfRunner());

    }

    private boolean isModified(JTextField field, String value) {
        return field.getText() != null ?
                !field.getText().equals(value) :
                value != null;
    }
}
