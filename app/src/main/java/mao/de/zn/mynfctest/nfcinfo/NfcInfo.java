package mao.de.zn.mynfctest.nfcinfo;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by zxl96 on 2017/5/4.
 */

public class NfcInfo extends DataSupport implements Serializable{

    private String hex;
    private long dec;
    private long reversed;
    private String technologies;
    private int function;
    private String number;

    public NfcInfo() {
    }

    public NfcInfo(String hex, long dec, long reversed, String technologies, int function, String number) {
        this.hex = hex;
        this.dec = dec;
        this.reversed = reversed;
        this.technologies = technologies;
        this.function = function;
        this.number = number;
    }

    public NfcInfo(String hex, long dec, long reversed, String technologies) {
        this.hex = hex;
        this.dec = dec;
        this.reversed = reversed;
        this.technologies = technologies;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public long getDec() {
        return dec;
    }

    public void setDec(long dec) {
        this.dec = dec;
    }

    public long getReversed() {
        return reversed;
    }

    public void setReversed(long reversed) {
        this.reversed = reversed;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
