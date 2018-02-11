package com.github.sdorra.buildfrontend.mojo;

import com.github.sdorra.buildfrontend.Node;
import com.github.sdorra.buildfrontend.PackageManager;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "run", defaultPhase = LifecyclePhase.COMPILE)
public class ScriptMojo extends AbstractPackageManagerMojo {

    @Parameter
    private String script;

    @Override
    protected void execute(Node node, PackageManager packageManager) {
        packageManager.run(script);
    }
}
