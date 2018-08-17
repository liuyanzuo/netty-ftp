package org.felix.netty.ftp.utils;

public enum RetEnum {
    CONN_SUCCESS_WC("220", "Power by HeiFengRen,V1.0"),
    PORT_OK("200", "Port is ok"),
    USER_LOGGED("230", "User logged"),
    USER_NOT_IDENTIFY("332", "Need user name"),
    UNIDENTIFY_CONNECTION("521", "Connection not exists,close 2 second later"),
    NOT_LOGIN("530", "Not Login"),
    USER_NEED_PASSWORD("331", "User name ok,need password"),
    IVALID_COMMAND("500", "Ivalid command"),
    FILE_NOT_VALID("550", "File not valid");

    RetEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
