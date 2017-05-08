package mao.de.zn.mynfctest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import mao.de.zn.mynfctest.function.SQLFunction;
import mao.de.zn.mynfctest.function.SetFunction;
import mao.de.zn.mynfctest.nfcinfo.NfcInfo;

import static android.app.Activity.RESULT_OK;


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
                playMusic(context);
                break;
            case 2:
//                mFunction = NFC_Function.CALL_PHONE;
                callPhone(context, nfcInfo.getDec());
                break;
            case 3:
//                mFunction = NFC_Function.SEND_MESSAGE;
                sendMeagmss(context, dec);
                break;
            case 4:
//                mFunction = NFC_Function.OPEN_WEBSITE;
                openWebsite(context,sql_nfcInfo.getNumber());
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

    private static void sendMeagmss(final Context context, Long dec) {
        Log.d(TAG, "sendMeagmss: ");
        NfcInfo nfcInfo = SQLFunction.find(dec);
        final String number = nfcInfo.getNumber();
        if (TextUtils.isEmpty(number)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请输入短信内容");
        builder.setMessage("给" + number + "发送短信");
        final EditText editText = new EditText(context);
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));

                intent.putExtra("sms_body", editText.getText().toString().trim());
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    //    public static String test(final Context context, final String number) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("请输入短信内容");
//        final EditText editText = new EditText(context);
//        builder.setView(editText);
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+number));
//
//                intent.putExtra("sms_body", editText.getText().toString().trim());
//                context.startActivity(intent);
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.show();
//        return "";
//    }
    private static void playMusic(Context context) {
        Log.d(TAG, "playMusic: ");
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        context.startActivity(intent);
    }
    private static void openWebsite(Context context,String url) {
        Log.d(TAG, "openWebsite: "+url);

        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        context.startActivity(it);
    }
}
