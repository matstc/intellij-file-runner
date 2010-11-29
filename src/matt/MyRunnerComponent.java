package matt;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@State(
        name = "MyRunnerSettings",
        storages = {@Storage(id = "myrunner", file = "$APP_CONFIG$/myrunner.xml")}
)
public class MyRunnerComponent implements ApplicationComponent, Configurable, PersistentStateComponent<MyRunnerComponent> {
    private MyRunnerSettings form;

    private String mainClassName;

    private String moduleNameOfRunner;

    public MyRunnerComponent() {
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "MyRunnerComponent";
    }


    public String getDisplayName() {
        return "My Runner";
    }

    public Icon getIcon() {
        return null;
    }

    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        if (form == null) {
            form = new MyRunnerSettings();
        }
        return form.getRootComponent();
    }

    public boolean isModified() {
        return form != null && form.isModified(this);
    }

    public void apply() throws ConfigurationException {
        if (form != null) {
            form.getData(this);
        }
    }

    public void reset() {
        if (form != null) {
            form.setData(this);
        }
    }

    public void disposeUIResources() {
        form = null;
    }

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(final String mainClassName) {
        this.mainClassName = mainClassName;
    }

    public String getModuleNameOfRunner() {
        return moduleNameOfRunner;
    }

    public void setModuleNameOfRunner(String moduleNameOfRunner) {
        this.moduleNameOfRunner = moduleNameOfRunner;
    }

    public MyRunnerComponent getState() {
        return this;
    }

    public void loadState(MyRunnerComponent state) {
        XmlSerializerUtil.copyBean(state,this);
    }
}

