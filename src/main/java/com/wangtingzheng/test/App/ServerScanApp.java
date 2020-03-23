package com.wangtingzheng.test.App;

import org.wangtingzheng.remotehome.Api.ScanApi;
import org.wangtingzheng.remotehome.Api.tools.QrcodeCheck;

public class ServerScanApp {
    /*-下面的参数需要您自己修改，这是有关服务端节点的信息-*/
    public static String productKey = "a1*******6q";
    public static String deviceName = "server";
    public static String deviceSecret = "ioH******************1d8";

    /*------------需要配置的信息结束------------------*/

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
