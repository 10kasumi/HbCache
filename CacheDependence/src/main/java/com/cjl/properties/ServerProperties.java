package com.cjl.properties;

import lombok.Data;

@Data
public class ServerProperties {
    private int port;

    private String backupPath;

    private int fileSize;

    private String username;

    private String password;

    private String nodes;

    private String serializerAlgorithm;
}
