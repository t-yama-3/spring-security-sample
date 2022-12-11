package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ConvertTest2 {
    @Test
    public void convUnicode() {
        // 作成した messages.properties のフルパスを指定
        String filePath = "C:\\JavaProject\\SpringSecuritySample\\SpringSecuritySample\\src\\main\\resources\\messages.properties";
        String newLine = System.getProperty("line.separator");  //改行文字を取得
        String convStr = "";  // 変換後の文字列を格納
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int startPos;
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) break;
                startPos = str.indexOf("=");  //"="の位置を取得
                if (startPos >= 0) {
                    convStr += str.substring(0, startPos + 1) + convUnicode(str.substring(startPos + 1)) + newLine;  // 変換して格納
                } else {
                    convStr += str + newLine;  // 変換せずに格納
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("ファイルが読み込めません");
        } finally {
            if (fileReader != null) {
                try {fileReader.close();} catch (IOException e) {}
            }
        }
        System.out.println(convStr);  // 結果をコンソール画面に出力
    }
    
    // 文字コード（unicode）を文字に変換するメソッド
    public String convUnicode(String unicode) {
        Pattern pattern = Pattern.compile("\\\\u[a-f0-9]{4}");  // Unicodeの文字コード（"\\u30a2"）を正規表現で取得
        Matcher matcher = pattern.matcher(unicode);
        String tmpStr, ansStr = "";
        int tmpEnd = 0;
        while (matcher.find()) {
            if (tmpEnd < matcher.start()) {
                ansStr += unicode.substring(tmpEnd, matcher.start());  // 文字コード以外のASCII文字を追加
            }
            tmpStr = unicode.substring(matcher.start() + 2, matcher.end());
            ansStr += (char)Integer.parseInt(tmpStr, 16);  // 文字コードを文字に変換して追加
            tmpEnd = matcher.end();
        }
        if (unicode.length() > tmpEnd) {
            ansStr += unicode.substring(tmpEnd, unicode.length());  // 最後に残った文字コード以外の文字列を格納
        }
        return ansStr;
    }
}
