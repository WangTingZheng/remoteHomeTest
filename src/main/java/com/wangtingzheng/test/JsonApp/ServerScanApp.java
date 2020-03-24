package com.wangtingzheng.test.JsonApp;

import org.wangtingzheng.remotehome.Api.ScanApi;
import org.wangtingzheng.remotehome.Api.tools.QrcodeCheck;
import org.wangtingzheng.remotehome.utils.Json;

public class ServerScanApp {
    // TODO: 2020/3/24: remove values, get from key.json
    public static String filePath = System.getProperty("user.dir") + "/key.json";
    public static Json json = new Json();
    public static String productKey = json.getValue(filePath, "server", "productKey") ;
    public static String deviceName = json.getValue(filePath, "server", "deviceName");
    public static String deviceSecret = json.getValue(filePath, "server", "deviceSecret");

    public static void main(String[] args)
    {
        ScanApi scanApi = new ScanApi(productKey, deviceName, deviceSecret, new QrcodeCheck() {
            @Override
            public boolean shouldOpenDoor(String payload) {
                if (payload.equals("http://weixin.qq.com/r/VjssNIPE1N6rrcdX925E")) {
                    return true;
                }
                return false;
            }
        });

        scanApi.startServer();
    }
}
