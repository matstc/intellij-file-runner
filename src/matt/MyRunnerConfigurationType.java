package matt;

import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyRunnerConfigurationType extends ApplicationConfigurationType {
    public MyRunnerConfigurationType() {
        super();
    }

    public String getDisplayName() {
        return "My Runner";
    }

    public String getConfigurationTypeDescription() {
        return "Runs a file";
    }

    public Icon getIcon() {
        return IconLoader.getIcon("myrunner.gif");
    }

    @NotNull
    public String getId() {
        return "#myrunner";
    }

}