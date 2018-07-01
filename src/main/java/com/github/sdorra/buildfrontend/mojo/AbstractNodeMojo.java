package com.github.sdorra.buildfrontend.mojo;

import com.github.sdorra.buildfrontend.Directories;
import com.github.sdorra.buildfrontend.Node;
import com.github.sdorra.buildfrontend.NodeConfiguration;
import com.github.sdorra.buildfrontend.NodeFactory;
import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public abstract class AbstractNodeMojo extends AbstractDirectoryMojo {

    @VisibleForTesting
    static final String PACKAGE_JSON = "package.json";

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNodeMojo.class);

    @Parameter(alias = "node")
    private NodeConfiguration nodeConfiguration;

    @Parameter(defaultValue = "true")
    private boolean failOnMissingPackageJson = true;

    @Component
    private NodeFactory nodeFactory;

    @Parameter
    private boolean skip;

    @VisibleForTesting
    void setNodeConfiguration(NodeConfiguration nodeConfiguration) {
        this.nodeConfiguration = nodeConfiguration;
    }

    @VisibleForTesting
    void setNodeFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    @VisibleForTesting
    void setSkip(boolean skip) {
        this.skip = skip;
    }

    @VisibleForTesting
    void setFailOnMissingPackageJson(boolean failOnMissingPackageJson) {
        this.failOnMissingPackageJson = failOnMissingPackageJson;
    }

    @Override
    public void execute(Directories directories) throws MojoExecutionException, MojoFailureException {
        if (skip) {
            LOG.warn("execution skipped, because of skip flag is set");
            return;
        }

        if (!existsPackageJson(directories)) {
            if (failOnMissingPackageJson) {
                throw new MojoFailureException(PACKAGE_JSON + " is missing");
            } else {
                LOG.warn("execution skipped, because no {} found", PACKAGE_JSON);
                return;
            }
        }

        try {
            Node node = nodeFactory.create(nodeConfiguration);
            execute(node);
        } catch (IOException e) {
            throw new MojoExecutionException("failed to create node", e);
        }
    }

    private boolean existsPackageJson(Directories directories) {
        return new File(directories.getWorkingDirectory(), PACKAGE_JSON).exists();
    }

    protected abstract void execute(Node node) throws MojoExecutionException, MojoFailureException;
}
