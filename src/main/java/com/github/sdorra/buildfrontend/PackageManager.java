package com.github.sdorra.buildfrontend;

/**
 * Interface for a node package manager (yarn or npm).
 */
public interface PackageManager {

    /**
     * Install everything which is defined in the package.json file of the package manager.
     */
    void install();

    /**
     * Runs a script which is defined in the package.json.
     *
     * @param script name of the script
     */
    void run(String script);
}
