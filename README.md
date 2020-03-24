## 硬件端操控指南
本篇文章主要讲解如何在服务端控制硬件来实现一些功能
### 功能描述
在本节中，我将简要介绍一下硬件所能实现的功能：二维码门禁和家庭监控
#### 二维码门禁
硬件可以扫描来自服务端的二维码，当然这个二维码是由客人带来的，接收到二维码时，硬件会把二维码的内容发送至服务端，由服务端来判断此二维码是否合法，再将结果返回至硬件，当硬件接收到结果时，执行相应的开关门操作。

下面是一个二维码实例，有些二维码的格式可能不支持，请尽量选择和下面二维码相类似格式的二维码。
![qrcode](./Image/qrcode.jpg)
#### 家庭监控
当服务端需要获取家庭的实时画面时，它可以通过给特定的硬件发送一条指令来获取图片，实际上是获取此硬件曾经拍摄过的图片的网络地址，服务端可以直接下载来使用。
### Api的使用
在使用之前，您需要把`lib`下的jar放在您项目根目录的`lib`下，当然您也可以在本例程上进行修改，如果您选择引入包，您还需要下载[发布页面](https://github.com/WangTingZheng/remoteHomeTest/releases) 的jar包，再将以下代码添加到您的pom.xml文件下：
```xml
<dependency>
    <groupId>org.wangtingzheng.remoteHome</groupId>
    <artifactId>photo</artifactId>
    <version>1.1.1</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/remoteHomePhoto-1.1.1.jar</systemPath>
</dependency>

<dependency>
    <groupId>org.wangtingzheng.remoteHome.Api</groupId>
    <artifactId>scan</artifactId>
    <version>1.1.1</version>
    <scope>system</scope>
    <systemPath>${basedir}/lib/remoteHomeScan-1.1.1.jar</systemPath>
</dependency>
```
这样，您就可以在您的项目中使用它们了
#### 配置信息填写
除了直接填写配置信息以外，您还可以通过文件自动导入数据，再次之前，您需要把配置信息导入到一个json文件里，您可以这么写json：

```json
{
    "nodes":
    {
          "productKey": "",
          "deviceName" : "",
          "deviceSecret" : ""
    },
    "access":
    {
          "accessKey" : "",
          "accessSecret" : ""
    },
    "server":
    {
          "productKey" :"",
          "deviceName" : "",
          "deviceSecret" : ""
    }
}
```

上面每一组数据的意义由你定义，比如说我这里的nodes保存的是某一个硬件的设备信息，access保存的是阿里云访问信息，server是服务端信息，那当你想初始化一个服务端的时候，你需要提供服务端的设备信息和访问信息，那你可以这样获取服务端设备名称：
```java
String filePath = "your json file path";
Json json = new Json();
private String deviceName = json.getValue(filePath, "server", "deviceName");
```
具体的例子您可以前往`com.wangtingzheng.JsonApp`下的两个Java文件查看
#### 二维码门禁
对于服务端，要实现二维码门禁，只需要开启监听服务即可，但是在此之前，还得配置一下服务端的节点的信息，来让服务端连接到阿里云的服务器

你可以通过
```java
 ScanApi scanApi = new ScanApi(productKey, deviceName, deviceSecret, new QrcodeCheck() {
     @Override
     public boolean shouldOpenDoor(String payload) {
         if (your code here) {
             return true;
         }
         return false;
     }
 });
```
来创建一个服务端，在初始化的过程中，除了填写必要的访问信息，你还得定义验证二维码合法性的函数，定义成功后，当硬件有二维码内容发送到服务端时，程序将根据这个函数来判断二维码是否合法，其中：

- payload：硬件发送过来待验证的二维码内容
- return：如果不合法，返回false，如果合法，返回true，如果不Override此方法，默认返回false

您可以在`org.wangtingzheng.remotehome.App`下的`ServerScanApp.java`查看示例

#### 家庭监控
对于家庭监控功能，服务端要主动命令硬件执行一些操作，由于上传需要时间，阿里云的服务器貌似无法支持这么长时间的等待，所以我把上传操作和获取链接操作分开了。如果你需要立刻获取当前画面，您需要在执行上传命令后大约20s再执行获取链接操作。

在发送命令之前，您需要定义一个硬件客户端，这个操作通过创建一个硬件对象并传入硬件的一些参数来实现：
```java
PhotoClient photoClient = new PhotoClient(productKey, deviceName, deviceSecret, accessKey, accessSecret);
```
接下来你需要实例化一个操作对象来执行一些命令：
```java
PhotoApi photoApi =  new PhotoApi();
```
最后，你可以执行下面的语句来控制你刚定义的硬件：
```java
photoApi.takeAphoto(photoClient);  //拍一张照片并上传
photoApi.getUrl(photoClient); //获取拍照历史链接
```
值得注意的是，`getUrl`函数将返回一个`List<String>`，里面每一条内容都是一张照片的链接。

您可以在`org.wangtingzheng.remotehome.App`下的`ServerPhotoApp.java`查看示例