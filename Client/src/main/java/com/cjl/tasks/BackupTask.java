package com.cjl.tasks;

import com.cjl.utils.ServerPropertiesUtils;
import lombok.SneakyThrows;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BackupTask implements Runnable {
    private String command;
    private String filePath;
    private static String fileName = "hbCache_backup_";
    private Integer fileSize;
    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    {
        fileSize = ServerPropertiesUtils.getFileSize();
        filePath = ServerPropertiesUtils.getBackupPath();
        if(filePath == null || "".equals(filePath)){
            filePath = "D://backup//";
        }
    }

    public BackupTask() {

    }

    public BackupTask(String command) {
        this.command = command;
    }

    @SneakyThrows
    @Override
    public void run() {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try{
            File folder = new File(filePath);
            if(!folder.exists() && !folder.isDirectory()){
                folder.setWritable(true, false);
                folder.mkdirs();
            }

            File file = new File(filePath, fileName + atomicInteger.get());
            if(file.exists()){
                if(file.length() >= fileSize * 1024 * 1024){
                    File newFile = new File(filePath,  fileName + atomicInteger.getAndIncrement());
                    newFile.createNewFile();
                    fos = new FileOutputStream(newFile, true);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(command);
                    bw.newLine();
                } else{
                    fos = new FileOutputStream(file, true);
                    bw = new BufferedWriter(new OutputStreamWriter(fos));
                    bw.write(command);
                    bw.newLine();
                }
            } else{
                file.createNewFile();
                fos = new FileOutputStream(file, true);
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                bw.write(command);
                bw.newLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            bw.close();
        }
    }
}
