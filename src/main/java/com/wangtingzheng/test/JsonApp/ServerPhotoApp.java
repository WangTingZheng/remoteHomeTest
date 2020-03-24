package com.wangtingzheng.test.JsonApp;

import org.wangtingzheng.remotehome.Api.PhotoApi;
import org.wangtingzheng.remotehome.Api.tools.PhotoClient;
import org.wangtingzheng.remotehome.utils.Json;

public class ServerPhotoApp {

    // TODO: 2020/3/24 :remove values, get from key.json
    public static Json json = new Json();
    public static String filePath = System.getProperty("user.dir") + "/key.json";

    public static String productKey = json.getValue(filePath, "nodes", "productKey") ;
    public static String deviceName = json.getValue(filePath, "nodes", "deviceName");
    public static String deviceSecret = json.getValue(filePath, "nodes", "deviceSecret");

    public static String accessKey = json.getValue(filePath, "access","accessKey");
    public static String accessSecret = json.getValue(filePath, "access","accessSecret");

    static PhotoClient photoClient = new PhotoClient(productKey, deviceName, deviceSecret, accessKey, accessSecret);
    static PhotoApi photoApi =  new PhotoApi();

    public static void main(String args[])
    {
        System.out.println("the size is "+photoApi.getUrl(photoClient).get(0));
    }
}
