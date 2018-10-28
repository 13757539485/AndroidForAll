package com.android.advancesettings.view;

public class ShellView {
    public static final int TYPE_EXECUTE = 0;
    public static final int TYPE_CHECK = 1;
    private int _id;
    private String name;
    private String instruction;
    private int type;
    private String path;
    private String checkOn;
    private String checkOff;

    public ShellView() {
    }

    public ShellView(int id , String name, String instruction, int type, String path, String checkOn, String checkOff) {
        this._id = id;
        this.name = name;
        this.instruction = instruction;
        this.type = type;
        this.path = path;
        this.checkOn = checkOn;
        this.checkOff = checkOff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCheckOn() {
        return checkOn;
    }

    public void setCheckOn(String checkOn) {
        this.checkOn = checkOn;
    }

    public String getCheckOff() {
        return checkOff;
    }

    public void setCheckOff(String checkOff) {
        this.checkOff = checkOff;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "ShellView{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", instruction='" + instruction + '\'' +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", checkOn='" + checkOn + '\'' +
                ", checkOff='" + checkOff + '\'' +
                '}';
    }
}
