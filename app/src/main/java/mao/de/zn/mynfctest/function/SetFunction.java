package mao.de.zn.mynfctest.function;

import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.List;

import mao.de.zn.mynfctest.nfcinfo.NfcInfo;

/**
 * Created by zxl96 on 2017/5/7.
 */

public class SetFunction {
    private static final String TAG = "SetFunction";
    NFC_Function mFunction;

    public boolean setCardFunction(NfcInfo nfcinfo, int functionNumber, String phone) {
        //存数据库
        if (phone == null) {
            phone = " ";
        }
        long dec = nfcinfo.getDec();
        String hex = nfcinfo.getHex();
        long reversed = nfcinfo.getReversed();
        String technologies = nfcinfo.getTechnologies();

        DataSupport.deleteAll(NfcInfo.class, "dec = ?", String.valueOf(dec));

        NfcInfo sqlnfcinfo = new NfcInfo();
        sqlnfcinfo.setDec(dec);
        sqlnfcinfo.setHex(hex);
        sqlnfcinfo.setReversed(reversed);
        sqlnfcinfo.setTechnologies(technologies);
        sqlnfcinfo.setFunction(functionNumber);
        sqlnfcinfo.setNumber(phone);
        sqlnfcinfo.save();


        List<NfcInfo> nfc = DataSupport.findAll(NfcInfo.class);

        Log.d(TAG, "setCardFunction: save__________________________");
        for (NfcInfo nfcInfo : nfc) {
            Log.d(TAG, "setCardFunction: "+nfcInfo.getDec());
            Log.d(TAG, "setCardFunction: "+nfcInfo.getNumber());
            Log.d(TAG, "setCardFunction: "+nfcInfo.getFunction());
        }
        return true;
    }




}
