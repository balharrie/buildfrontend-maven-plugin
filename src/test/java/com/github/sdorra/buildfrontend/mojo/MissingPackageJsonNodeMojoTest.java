package com.github.sdorra.buildfrontend.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MissingPackageJsonNodeMojoTest extends AbstractPackageManagerMojoTestBase {

    private InstallDependenciesMojo mojo;

    @Before
    public void setUp() throws IOException {
        mojo = new InstallDependenciesMojo();
        prepareDirectoryMojo(mojo);
        deletePackageJson(mojo);
    }

    @Test(expected = MojoFailureException.class)
    public void testExecuteWithException() throws MojoFailureException, MojoExecutionException {
        mojo.setFailOnMissingPackageJson(true);
        mojo.execute();
    }

    @Test
    public void testExecuteAndSkip() throws MojoFailureException, MojoExecutionException {
        mojo.setFailOnMissingPackageJson(false);
        mojo.execute();

        verify(packageManager, never()).install();
    }

}
