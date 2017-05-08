package mao.de.zn.mynfctest.function;

import org.litepal.crud.DataSupport;

import java.util.List;

import mao.de.zn.mynfctest.nfcinfo.NfcInfo;

/**
 * Created by zxl96 on 2017/5/8.
 */

public class SQLFunction {
    //增
    //删
    public static int delete(Long dec) {
        int del = DataSupport.deleteAll(NfcInfo.class, "dec = ?", String.valueOf(dec));
//        int delete = DataSupport.delete(NfcInfo.class, "dec = ?", dec);
        return del;
    }
    //改
    //查
    public static NfcInfo find(Long dec) {
        NfcInfo re_nfcInfo=new NfcInfo();
        List<NfcInfo> nfcInfos = DataSupport.findAll(NfcInfo.class);
        for (NfcInfo nfcInfo : nfcInfos) {
            if (dec == nfcInfo.getDec()) {
                re_nfcInfo = nfcInfo;
            }
        }
        return re_nfcInfo;
    }
}
