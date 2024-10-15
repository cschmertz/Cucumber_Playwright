package com.listeners;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.*;


import java.io.File;
import java.util.List;

public class CodeLinter {

    public static void lintFile(String filePath, String configPath) throws CheckstyleException {
        Configuration config = ConfigurationLoader.loadConfiguration(
                configPath,
                new PropertiesExpander(System.getProperties()));

        Checker checker = new Checker();
        checker.setModuleClassLoader(CodeLinter.class.getClassLoader());
        checker.configure(config);

        AuditListener listener = new SeverityLevelCounter(SeverityLevel.ERROR);
        checker.addListener(listener);

        List<File> files = List.of(new File(filePath));
        int errors = checker.process(files);

        checker.destroy();

        if (errors > 0) {
            System.out.println("Found " + errors + " style errors");
        } else {
            System.out.println("No style errors found");
        }
    }
}