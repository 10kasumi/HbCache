package com.cjl.tasks;

import com.cjl.handler.CheckCommandHandler;
import com.cjl.handler.HandlerManager;
import com.cjl.message.Message;
import com.cjl.utils.ServerPropertiesUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class RecoveryTask implements Runnable{

    private String filePath;

    private CheckCommandHandler checkCommandHandler;

    public RecoveryTask() {
        checkCommandHandler = new CheckCommandHandler();
        filePath = ServerPropertiesUtils.getBackupPath();
    }


    @Override
    public void run() {
        File dir = new File(filePath);
        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for(File file : files){
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String command = reader.readLine();
                    while(command != null){
                        Message message = checkCommandHandler.checkCommand(command, null);
                        try {
                            HandlerManager.process(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        command = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
