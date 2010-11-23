package matt;

import com.intellij.execution.*;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.StringUtils;

public class MyRunner extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        System.out.println("Invoking My Runner");

        Application application = ApplicationManager.getApplication();
        MyRunnerComponent component = application.getComponent(MyRunnerComponent.class);
        String mainClassName = component.getMainClassName();
        if (mainClassName == null || mainClassName.isEmpty()) {
            say("Set the class of the runner in the My Runner settings.");
            return;
        }


        Project project = e.getData(PlatformDataKeys.PROJECT);
        String moduleNameOfRunner = component.getModuleNameOfRunner();
        if (moduleNameOfRunner == null || moduleNameOfRunner.isEmpty()) {
            say("Specify the name of the module where the runner class can be found. Check the settings.");
        }

        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        if (file == null) {
            say("Select a file or focus on the file being edited to feed it to My Runner.");
            return;
        }

        RunManager runManager = RunManager.getInstance(project);
        Module module = ModuleManager.getInstance(project).findModuleByName(moduleNameOfRunner);

        if (module == null) {
            say("Could not find the module of the runner with name '" + moduleNameOfRunner + "'. Check settings." +
                    "\n\nHere is the list of modules that were found:\n   " + StringUtils.join(ModuleManager.getInstance(project).getModules(), "\n   ")); 
            return;
        }

        String filename = file.getPath();
        MyRunnerConfigurationType type = new MyRunnerConfigurationType(module, mainClassName, filename);
        RunConfiguration runConfiguration = type.getConfigurationFactories()[0].createTemplateConfiguration(project);

        RunnerAndConfigurationSettings runnerAndConfigurationSettings = runManager.createRunConfiguration("My Runner", type.getConfigurationFactories()[0]);

        Executor executor = DefaultRunExecutor.getRunExecutorInstance();
        ProgramRunner runner = RunnerRegistry.getInstance().getRunner(executor.getId(), runConfiguration);
        ExecutionEnvironment environment = new ExecutionEnvironment(runner, runnerAndConfigurationSettings, e.getDataContext());

        try {
            runner.execute(executor, environment);
        } catch (ExecutionException e1) {
            JavaExecutionUtil.showExecutionErrorMessage(e1, "Error", project);
        }
    }

    public void say(String message) {
        Messages.showMessageDialog(message, "Info", Messages.getInformationIcon());
    }


}
