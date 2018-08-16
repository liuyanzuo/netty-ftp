package org.felix.netty.ftp.domain;

public class FTPCommand {

    private String command;

    private String param;

    public FTPCommand(String command) {
        this.command = command;
    }

    public FTPCommand(String command, String param) {
        this.command = command;
        this.param = param;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "[" + command + " " + param + "]";
    }
}
