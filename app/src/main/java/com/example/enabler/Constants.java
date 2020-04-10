package com.example.enabler;

public final class Constants {
    public static final String LOGIN_PAGE = "http://192.168.1.2/login.stm";
    public static final String WIRELESS_PAGE = "http://192.168.1.2/wireless_id.stm";
    public static final String INDEX_PAGE = "http://192.168.1.2/index.htm";
    public static final String JSCheckFirmwareVersion = "javascript:(function() { " +
                                                        "return checkfwVersion()" +
                                                        "})()";
    public static final String JSWifiIsOnOrOff = "javascript:(isOnIsOff() { " +
                                                 "myobj = document.getElementsByName(\"wbr\");" +
                                                 "if (myobj[0].selectedIndex != 11) {" + // wifi is on
                                                 "return \"on\"" +
                                                 "} else {"+
                                                 "return \"off\""+
                                                 "}"+
                                                 "})()";
    public static final String JSCallbackWifiState = "javascript:android.onData(isOnIsOff)";
    public static final String JSTurnWifiOff = "javascript:(function() { " +
                                                "myobj = document.getElementsByName(\"wbr\");" +
                                                "myobj[0].selectedIndex = 11;" + // 11 is off?
                                                "document.forms[0].submit();" +
                                                "return evaltF();" +
                                                "})()";
    public static final String JSTurnWifiOn = "javascript:(function() { " +
                                              "myobj = document.getElementsByName(\"wbr\");" +
                                              "myobj[0].selectedIndex = 2;" +
                                              "document.forms[0].submit();" +
                                              "return evaltF();" +
                                              "})()";
}
