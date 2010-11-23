package matt;

import com.intellij.execution.CantRunException;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class MyRunnerRunConfiguration extends RunConfigurationBase implements ModuleRunConfiguration {
    private Module myModule;
    private String mainClass;
    private String args;

    public MyRunnerRunConfiguration(final Project project, Module module, final ConfigurationFactory factory, final String name, String mainClass, String args) {
        super(project, factory, name);
        this.mainClass = mainClass;
        this.args = args;
        this.myModule = module;
    }

    public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env) throws ExecutionException {

        final ModuleRootManager rootManager = ModuleRootManager.getInstance(getModule());
        final Sdk jdk = rootManager.getSdk();
        if (jdk == null) {
          throw CantRunException.noJdkForModule(getModule());
        }

        final JavaCommandLineState state = new JavaCommandLineState(env) {
            protected JavaParameters createJavaParameters() throws ExecutionException {

                final JavaParameters params = new JavaParameters();
                VirtualFile[] classesAndOutput = rootManager.getFiles(OrderRootType.CLASSES_AND_OUTPUT);
                params.setMainClass(mainClass);
                params.getClassPath().addVirtualFiles(Arrays.asList(classesAndOutput));
                params.setJdk(jdk);
                params.getProgramParametersList().add(args);
                return params;
            }
        };

        state.setConsoleBuilder(TextConsoleBuilderFactory.getInstance().createBuilder(getProject()));
        return state;
    }

    public void checkConfiguration() throws RuntimeConfigurationException {
    }


    @NotNull
    public Module[] getModules() {
        return ModuleManager.getInstance(getProject()).getModules();
    }


    @Nullable
    public Module getModule() {
      return myModule;
    }

    public void setModule(Module module) {
      myModule = module;
    }

    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return null;
    }

    public JDOMExternalizable createRunnerSettings(ConfigurationInfoProvider provider) {
        return null;
    }

    public SettingsEditor<JDOMExternalizable> getRunnerSettingsEditor(ProgramRunner runner) {
        return null;
    }


}
