package com.cjl.utils;

import com.cjl.protocol.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

public class ServerPropertiesUtils {

    static ObjectMapper mapper;

    public static HbCacheProperties hbCacheProperties;

    static {
        mapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream in = ServerPropertiesUtils.class.getClassLoader().getResourceAsStream("server.yml");
            hbCacheProperties = mapper.readValue(in, HbCacheProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getServerPort() {
        int port = hbCacheProperties.getServerProperties().getPort();
        return port == 0 ? 8080 : port;
    }

    public static String getBackupPath(){
        String path = hbCacheProperties.getServerProperties().getBackupPath();
        return path == null ? "D://hbCache//backup" : path;
    }

    public static int getFileSize(){
        int fileSize = hbCacheProperties.getServerProperties().getFileSize();
        return fileSize == 0 ? 100 : fileSize;
    }

    public static String getUsername(){
        String username = hbCacheProperties.getServerProperties().getUsername();
        return username;
    }

    public static String getPassword(){
        String password = hbCacheProperties.getServerProperties().getPassword();
        return password;
    }

    public static String getNodes(){
        String nodes = hbCacheProperties.getServerProperties().getNodes();
        return nodes;
    }

    public static Serializer.Algorithm getSerializerAlgorithm() {
        String value = hbCacheProperties.getServerProperties().getSerializerAlgorithm();
        if (value == null) {
            return Serializer.Algorithm.Java;
        } else {
            return Serializer.Algorithm.valueOf(value);
        }
    }



    public static boolean getEnableCluster(){
        ClusterProperties clusterProperties = hbCacheProperties.getClusterProperties();
        if(clusterProperties == null){
            return false;
        }
        boolean res = clusterProperties.isEnable();
        return res;
    }

    public static String getClientUrls(){
        ClusterProperties clusterProperties = hbCacheProperties.getClusterProperties();
        if(clusterProperties == null){
            return "";
        }
        return hbCacheProperties.getClusterProperties().getClientUrls();
    }
}
