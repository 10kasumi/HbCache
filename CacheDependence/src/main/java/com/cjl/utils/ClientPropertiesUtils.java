package com.cjl.utils;

import com.cjl.properties.ClientProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

public class ClientPropertiesUtils {
    static ObjectMapper mapper;

    public static ClientProperties clientProperties;

    static {
        mapper = new ObjectMapper(new YAMLFactory());
        try {
            InputStream in = ServerPropertiesUtils.class.getClassLoader().getResourceAsStream("client.yml");
            clientProperties = mapper.readValue(in, ClientProperties.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getServerPort() {
        int port = clientProperties.getServerPort();
        return port == 0 ? 8080 : port;
    }

    public static String getServerHost(){
        String host = clientProperties.getServerHost();
        return host == null ? "127.0.0.1" : host;
    }
}
