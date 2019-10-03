/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jboss.onetomanyversions;

/**
 *
 * @author panos
 */
public class FileData {

    protected String fileName;
    protected String minVersion;
    protected String maxVersion;
    protected String server;
    protected String module;
    protected String packageName;

    public FileData(String fileName, String minVersion, String maxVersion, String server, String module, String packageName) {
        this.fileName = fileName;
        this.minVersion = minVersion;
        this.maxVersion = maxVersion;
        this.server = server;
        this.module = module;
        this.packageName = packageName;
    }

}
