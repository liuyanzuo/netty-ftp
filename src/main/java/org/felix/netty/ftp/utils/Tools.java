package org.felix.netty.ftp.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.net.URL;

/**
 * @author HeiFengRen
 */
public class Tools {

    public static int CORE_COUNT = Runtime.getRuntime().availableProcessors();

    public static final String SPLIT = " ";

    public static final String LINE = "\r\n";

    public static final int MAX_CMD_LEN = 128;

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static URL getResource(String path) {
        return getClassLoader().getResource(path);
    }

    public static String msgToString(ByteBuf byteBuf) {
        int readableCount = byteBuf.readableBytes();
        byte[] readContent = new byte[readableCount];
        byteBuf.readBytes(readContent);
        return new String(readContent);
    }

    public static ByteBuf strToMessage(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("UTF-8");
        ByteBuf ret = Unpooled.buffer(bytes.length);
        ret.writeBytes(bytes);
        return ret;
    }

    public static String enumToMessageString(RetEnum retEnum) {
        StringBuilder retBuilder = new StringBuilder();
        retBuilder.append(retEnum.getCode());
        retBuilder.append(SPLIT);
        retBuilder.append(retEnum.getMessage());
        return retBuilder.toString();
    }

    public static String attachStrings(String[] strArr, boolean isFirstJoin) {
        StringBuilder retBuilder = new StringBuilder();
        if (isFirstJoin) {
            for (int i = 0; i < strArr.length; i++) {
                retBuilder.append(strArr[i]);
            }
            return retBuilder.toString();
        }
        for (int i = 1; i < strArr.length; i++) {
            retBuilder.append(strArr[i]);
        }
        return retBuilder.toString();
    }

    public static String addressToStr(SocketAddress socketAddress){
        return socketAddress.toString();
    }

    public static String fixIpAddressFormat(String address){
        if (address.startsWith("/")) {
            return address.substring(1, address.length());
        }
        return address;
    }

}
