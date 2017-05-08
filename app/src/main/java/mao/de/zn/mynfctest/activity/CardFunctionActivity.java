package mao.de.zn.mynfctest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import mao.de.zn.mynfctest.function.SQLFunction;
import mao.de.zn.mynfctest.nfcinfo.NfcInfo;


/**
 * Created by zxl96 on 2017/5/8.
 */

public class CardFunctionActivity {
    private static final String TAG = "CardFunctionActivity";

    public static void select(Context context, NfcInfo nfcInfo) {
        Log.d(TAG, "select: ");
        long dec = nfcInfo.getDec();
        NfcInfo sql_nfcInfo = SQLFunction.find(dec);
        int function = sql_nfcInfo.getFunction();
        Log.d(TAG, "select: " + function);
        switch (function) {
            case 1:
//                mFunction = NFC_Function.PLAY_MUSIC;

                break;
            case 2:
//                mFunction = NFC_Function.CALL_PHONE;
                callPhone(context, nfcInfo.getDec());
                break;
            case 3:
//                mFunction = NFC_Function.SEND_MESSAGE;
                break;
            case 4:
//                mFunction = NFC_Function.OPEN_WEBSITE;
                break;
        }
    }

    private static void callPhone(Context context, Long dec) {
        Log.d(TAG, "callPhone: ");
        NfcInfo nfcInfo = SQLFunction.find(dec);
        String number = nfcInfo.getNumber();
        if (TextUtils.isEmpty(number)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    private static void sendMeagmss(Context context, Long dec) {
        Log.d(TAG, "sendMeagmss: ");

    }
}
