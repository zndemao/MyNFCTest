package mao.de.zn.mynfctest.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mao.de.zn.mynfctest.R;
import mao.de.zn.mynfctest.function.SetFunction;
import mao.de.zn.mynfctest.nfcinfo.NfcInfo;

public class SetFeaturesActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SetFeaturesActivity";
    public NfcInfo nfc_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_set_features_);
        Log.d(TAG, "onCreate: ");
        nfc_info = (NfcInfo) getIntent().getSerializableExtra("NFC_info");
        Button playMusic = (Button) findViewById(R.id.play_music);
        playMusic.setOnClickListener(this);
        Button call_phone = (Button) findViewById(R.id.call_phone);
        call_phone.setOnClickListener(this);
        Button send_message = (Button) findViewById(R.id.send_message);
        send_message.setOnClickListener(this);
        Button open_Website = (Button) findViewById(R.id.open_Website);
        open_Website.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
//        SetFunction setFunction = new SetFunction();
        switch (v.getId()) {
            case R.id.play_music:
                Log.d(TAG, "onClick: +++++++++++++++++++++++");
                queren("播放音乐", 1);
                break;
            case R.id.call_phone:
                queren(2);
                break;
            case R.id.send_message:
                queren(3);
                break;
            case R.id.open_Website:
                queren(4);
                break;
            case R.id.daitianjia:
                break;
        }
    }

    private void queren(String message, final int hao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定");
        builder.setMessage("确定设置为播放" + message);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new SetFunction().setCardFunction(nfc_info, hao, null);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    public void queren(final int hao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择发送人");
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                NfcInfo nfc_info = SetFeaturesActivity.this.nfc_info;
                new SetFunction().setCardFunction(nfc_info, hao, editText.getText().toString().trim());
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
