package com.cjl;

import com.cjl.cluster.manager.ClusterManager;
import com.cjl.cluster.manager.SingleManager;
import com.cjl.utils.ServerPropertiesUtils;

public class HbServer {
    public static ClusterManager ClusterManager;
    public static boolean enableCluster = false;

    public static void main(String[] args) throws InterruptedException {
        enableCluster = ServerPropertiesUtils.getEnableCluster();
        if(enableCluster){
            ClusterManager = new ClusterManager();
        } else{
            SingleManager singleManager = new SingleManager();
        }
    }
}
