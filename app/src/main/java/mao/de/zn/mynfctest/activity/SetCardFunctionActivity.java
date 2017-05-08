package mao.de.zn.mynfctest.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

import mao.de.zn.mynfctest.R;
import mao.de.zn.mynfctest.function.SQLFunction;
import mao.de.zn.mynfctest.function.SetFunction;
import mao.de.zn.mynfctest.nfcinfo.NfcInfo;

public class SetCardFunctionActivity extends Activity {

    private List<NfcInfo> nfcInfos;
    private ListView listview;
    private FunctionAdapter functionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_card_function);
        nfcInfos = DataSupport.findAll(NfcInfo.class);
        functionAdapter = new FunctionAdapter();
        listview = (ListView) findViewById(R.id.list_item);
        listview.setAdapter(functionAdapter);
        listview.setOnItemClickListener(new MyOnItemListenter());
        listview.setOnItemLongClickListener(new MyOnItemLongListenter());
    }

    public class FunctionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nfcInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return nfcInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(SetCardFunctionActivity.this, R.layout.list_item, null);
            TextView dec = (TextView) view.findViewById(R.id.list_dec);
            TextView function = (TextView) view.findViewById(R.id.list_function);
            dec.setText(nfcInfos.get(position).getDec() + "");
            function.setText(select(nfcInfos.get(position).getFunction()) + nfcInfos.get(position).getNumber() + "");


            return view;

        }

        public String select(int functionNumber) {
            String function = "";
            switch (functionNumber) {
                case 1:
                    function = "播放音乐";
                    break;
                case 2:
                    function = "拨打电话";
                    break;
                case 3:
                    function = "发送短信";
                    break;
                case 4:
                    function = "打开网页";
                    break;
            }
            return function;
        }

    }

    public class MyOnItemListenter implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(SetCardFunctionActivity.this, SetFeaturesActivity.class);
            intent.putExtra("NFC_info", nfcInfos.get(position));
            startActivity(intent);
        }
    }

    public class MyOnItemLongListenter implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //删除
            long dec = nfcInfos.get(position).getDec();
            int delete = SQLFunction.delete(dec);
            final int pos = position;
            AlertDialog.Builder builder = new AlertDialog.Builder(SetCardFunctionActivity.this);
            builder.setTitle("你确认删除吗");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    nfcInfos.remove(pos);
                    functionAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("取消", null);
            builder.show();
//            nfcInfos.remove(position);
//            functionAdapter.notifyDataSetChanged();
            if (delete > 0) {
                Toast.makeText(SetCardFunctionActivity.this, "已删除", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SetCardFunctionActivity.this, "删除错误", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

}
