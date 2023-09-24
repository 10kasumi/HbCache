package com.cjl.server.service;

import com.cjl.utils.ServerPropertiesUtils;

public class UserServiceMemoryImpl implements UserService{

    private String provideUsername = null;
    private String providePassword = null;

    {
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("server.properties");
//        Properties properties = new Properties();
//        try {
//            properties.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        provideUsername = properties.getProperty("server.username");
//        providePassword = properties.getProperty("server.password");
        provideUsername = ServerPropertiesUtils.getUsername();
        providePassword = ServerPropertiesUtils.getPassword();
    }

    @Override
    public boolean login(String username, String password){
        if((provideUsername == null || "".equals(provideUsername)) && (providePassword == null || "".equals(providePassword))){
            return true;
        }
        return username.equals(provideUsername) && password.equals(providePassword);
    }
}
