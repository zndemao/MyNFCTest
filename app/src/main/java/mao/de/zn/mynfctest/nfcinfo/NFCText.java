package mao.de.zn.mynfctest.nfcinfo;

import android.nfc.NdefRecord;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by zxl96 on 2017/5/4.
 */

public class NFCText {
    /**
     * 新的文本记录
     *
     * @param text
     * @param locale
     * @param encodeInUtf8
     * @return
     */
    public static NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        //语言字节
        byte[] languageBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        //utf编码
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + languageBytes.length);
        byte[] data = new byte[1 + languageBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(languageBytes, 0, data, 1, languageBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + languageBytes.length,
                textBytes.length);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }
}
