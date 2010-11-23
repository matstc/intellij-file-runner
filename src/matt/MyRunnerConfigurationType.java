package matt;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyRunnerConfigurationType implements ConfigurationType {
    private final ConfigurationFactory myFactory;

    MyRunnerConfigurationType(final Module module, final String mainClass, final String args) {
        myFactory = new ConfigurationFactory(this) {
            public RunConfiguration createTemplateConfiguration(Project project) {
                final MyRunnerRunConfiguration runConfiguration = new MyRunnerRunConfiguration(project, module, this, "", mainClass, args);
                return runConfiguration;
            }

            public RunConfiguration createConfiguration(String name, RunConfiguration template) {
                final MyRunnerRunConfiguration pluginRunConfiguration = (MyRunnerRunConfiguration) template;
                if (pluginRunConfiguration.getModule() == null) {
                    pluginRunConfiguration.setModule(module);
                }

                return super.createConfiguration(name, pluginRunConfiguration);
            }
        };
    }

    public String getDisplayName() {
        return "My Runner";
    }

    public String getConfigurationTypeDescription() {
        return "Runs a file";
    }

    public Icon getIcon() {
        return null;
    }

    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{myFactory};
    }

    @NotNull
    public String getId() {
        return "#myrunner";
    }

}