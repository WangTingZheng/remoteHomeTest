package com.wangtingzheng.test.App;

import org.wangtingzheng.remotehome.Api.PhotoApi;
import org.wangtingzheng.remotehome.Api.tools.PhotoClient;

public class ServerPhotoApp {

    /*-下面的参数需要您自己修改，这是有关要操作节点的信息-*/
    public static String productKey = "a1****6q";
    public static String deviceName = "raspberrypiQR_1";
    public static String deviceSecret = "W9bI****************RwjFO";

    public static String accessKey = "LTA********************6oQ";
    public static String accessSecret = "usm1X******************BV3NM";
    /*------------需要配置的信息结束------------------*/
    static PhotoClient photoClient = new PhotoClient(productKey, deviceName, deviceSecret, accessKey, accessSecret);
    static PhotoApi photoApi =  new PhotoApi();

    public static void main(String[] args)
    {
        System.out.println("the size is "+photoApi.getUrl(photoClient).get(0));
    }
}
