package com.cjl.utils;

import com.cjl.properties.ServerProperties;
import lombok.Data;

@Data
public class HbCacheProperties {
    private ServerProperties serverProperties;

    private ClusterProperties clusterProperties;
}
