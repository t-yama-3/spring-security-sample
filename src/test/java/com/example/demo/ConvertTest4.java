package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ConvertTest4 {
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
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) break;
                convStr += convUnicode(str) + newLine;  // 変換して格納
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("ファイルが読み込めません");
        } finally {
            if (fileReader != null)  try { fileReader.close(); } catch (IOException e) {}
         }
        System.out.println(convStr);  // 結果をコンソール画面に出力
    }
    
    // 文字コード（unicode）部分のみ文字に変換するメソッド
    public String convUnicode(String text) {
        Pattern pattern = Pattern.compile("\\\\u[a-f0-9]{4}");  // unocodeの文字コード（"\\u30a2"）を正規表現で取得
        Matcher matcher = pattern.matcher(text);
        String convStr = "";
        int endPos = 0;
        while (matcher.find()) {
            if (endPos < matcher.start()) convStr += text.substring(endPos, matcher.start());  // 文字コード以外のASCII文字を追加
            String unicode = text.substring(matcher.start() + 2, matcher.end());  // unicodeの数値部分（"30a2"）を切り出し
            convStr += (char)Integer.parseInt(unicode, 16);  // 文字コードを文字に変換して追加
            endPos = matcher.end();
        }
        if (text.length() > endPos) convStr += text.substring(endPos, text.length());  // 最後に残った文字コード以外の文字列を格納
        return convStr;
    }
}
