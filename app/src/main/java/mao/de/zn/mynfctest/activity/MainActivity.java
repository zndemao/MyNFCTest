package mao.de.zn.mynfctest.activity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.Manifest;

import mao.de.zn.mynfctest.R;
import mao.de.zn.mynfctest.nfcinfo.NFCText;
import mao.de.zn.mynfctest.nfcinfo.NfcInfo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private TextView nfc_status;//nfc状态
    private TextView hex;
    private TextView dec;
    private TextView reversed;
    private TextView technologies;
    private Button set_features;//设置nfc功能
    private Button scan_notes;//扫描记录
    private Button set_card_function;//修改nfc卡功能
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private NdefMessage ndefMessage;
    private NfcInfo mNFCInfo = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //安卓的权限问题
        insertDummyContactWrapper();
        initUI();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //拦截其他NFC扫描（例如QQ，支付宝，蓝牙）
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        ndefMessage = new NdefMessage(new NdefRecord[]{NFCText.newTextRecord("", Locale.ENGLISH, true)});
    }

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("Read Contacts");
//        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
//            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
//            if (permissionsNeeded.size() > 0) {
//                // Need Rationale
//                String message = "You need to grant access to " + permissionsNeeded.get(0);
//                for (int i = 1; i < permissionsNeeded.size(); i++)
//                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message,
//                        new DialogInterface.OnClickListener() {
//                            @RequiresApi(api = Build.VERSION_CODES.M)
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
//                            }
//                        });
//                return;
//            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }

//        insertDummyContact();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(MainActivity.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
//                    insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

//    private void authority() {
//        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
//        } else {
//            return;
//        }
////        ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE)
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    return;
//                } else {
//                    Toast.makeText(this, "拒绝权限有些功能无法使用", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter == null) {
            nfc_status.setText("设备不支持NFC");
            finish();
            return;
        }
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            nfc_status.setText("请在设置中将NFC打开");
            finish();
            return;
        }
        if (nfcAdapter != null) {
            //隐式意图启动
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
            nfcAdapter.enableForegroundNdefPush(this, ndefMessage);
        }
    }

    //活动不可见
    @Override
    protected void onPause() {
        super.onPause();
        //隐式意图启动
        if (nfcAdapter != null) {
            //隐式启动
            nfcAdapter.disableForegroundDispatch(this);
            nfcAdapter.disableForegroundNdefPush(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        Log.d(TAG, "onNewIntent: 证明nfc在此时被启动");
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "resolveIntent: ACTION_TAG_DISCOVERED    " + NfcAdapter.ACTION_TAG_DISCOVERED.equals(action));
        Log.d(TAG, "resolveIntent: ACTION_TECH_DISCOVERED   " + NfcAdapter.ACTION_TECH_DISCOVERED.equals(action));
        Log.d(TAG, "resolveIntent: ACTION_NDEF_DISCOVERED   " + NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action));
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)//逻辑预算或
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            Log.d(TAG, "resolveIntent: " + rawMsgs);
            NdefMessage[] msgs;
            if (rawMsgs != null) {//此方法无法实现我的卡 rawMsgs 一直为空
                Log.d(TAG, "resolveIntent: ");
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // 未知的标签类型;Unknown tag type
                Log.d(TAG, "resolveIntent: 未知的标签类型;");
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Parcelable tag = intent
                        .getParcelableExtra(NfcAdapter.EXTRA_TAG);
                NfcInfo nfcInfo = dumpTagData(tag);
                //将nfc里的内容显示
                mNFCInfo = nfcInfo;
                showViews(nfcInfo);
            }
        }
    }

    //一般公家卡，扫描的信息
    private NfcInfo dumpTagData(Parcelable p) {
        Tag tag = (Tag) p;
        byte[] id = tag.getId();
        String hex_s = getHex(id);
        long dec_s = getDec(id);
        long reversed_s = getReversed(id);
        Log.d(TAG, "Tag ID (hex): " + hex_s);
        Log.d(TAG, "Tag ID (dec): " + dec_s);
        Log.d(TAG, "ID (reversed): " + reversed_s);
        String prefix = "android.nfc.tech.";
        StringBuilder technologies_sb = new StringBuilder();
        for (String tech : tag.getTechList()) {
            technologies_sb.append(tech.substring(prefix.length()) + " ");
        }
        String technologies_s = technologies_sb.toString();
        Log.d(TAG, "Technologies: " + technologies_s);
        return new NfcInfo(hex_s, dec_s, reversed_s, technologies_s);
    }

    /**
     * Tag ID (hex)
     *
     * @param bytes
     * @return
     */
    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * Tag ID (dec)
     *
     * @param bytes
     * @return
     */
    private long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    /**
     * ID (reversed)
     *
     * @param bytes
     * @return
     */
    private long getReversed(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private void initUI() {
        nfc_status = (TextView) findViewById(R.id.nfc_status);
        hex = (TextView) findViewById(R.id.hex);
        dec = (TextView) findViewById(R.id.dec);
        reversed = (TextView) findViewById(R.id.reversed);
        technologies = (TextView) findViewById(R.id.technologies);
        set_features = (Button) findViewById(R.id.set_features);//设置卡功能
        scan_notes = (Button) findViewById(R.id.scan_notes);//扫描记录
        set_card_function = (Button) findViewById(R.id.set_card_function);//修改卡功能
        set_features.setOnClickListener(this);
        scan_notes.setOnClickListener(this);
        set_card_function.setOnClickListener(this);
    }

    private void showViews(NfcInfo nfcInfo) {
        hex.setText(nfcInfo.getHex());
        dec.setText(nfcInfo.getDec() + "");
        reversed.setText(nfcInfo.getReversed() + "");
        technologies.setText(nfcInfo.getTechnologies());
        //使用意图拨打电话，发短信
        Log.d(TAG, "showViews: 使用意图拨打电话，发短信");
//        CardFunctionActivity.callPhone(MainActivity.this,nfcInfo.getDec());
        CardFunctionActivity.select(MainActivity.this, nfcInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_features:
                if (mNFCInfo == null) {
                    return;
                }
                Intent intent = new Intent(MainActivity.this, SetFeaturesActivity.class);
                intent.putExtra("NFC_info", mNFCInfo);
                startActivity(intent);
                break;
            case R.id.set_card_function:
                Intent set_card_function = new Intent(MainActivity.this, SetCardFunctionActivity.class);
                startActivity(set_card_function);
                break;
        }
    }

}
