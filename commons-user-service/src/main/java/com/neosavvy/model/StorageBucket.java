package com.neosavvy.model;

import java.io.File;

/**
 * This class is a derivative work of Fineline via Tommy Odom.
 * The code herein cannot be distributed without first establishing prior written consent
 * from tommy.odom@gmail.com
 */
public class StorageBucket
{

    public StorageBucket(String name, StorageBucketType type, File directory) {
        this.name = name;
        this.type = type;
        this.directory = directory;
    }

    private String name;
    private StorageBucketType type;
    private File directory;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public File getDirectory() {
        return directory;
    }
    public void setDirectory(File directory) {
        this.directory = directory;
    }
    
    public StorageBucketType getType() {
        return type;
    }
    public void setType(StorageBucketType type) {
        this.type = type;
    }
    
    
}
