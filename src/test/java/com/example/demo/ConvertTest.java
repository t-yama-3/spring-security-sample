package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class ConvertTest {
    @Test
    public void convUnicode() throws IOException {
        String filePath = "C:\\JavaProject\\SpringSecuritySample\\SpringSecuritySample\\src\\main\\resources\\messages.properties";  // 作成した messages.properties のフルパスを指定
        Path file = Paths.get(filePath);
        String convStr = "";  // 変換後の文字列を格納
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String str;
            while ((str = br.readLine()) != null) convStr += convUnicode(str) + "\r\n";  // 変換して格納
         }
        System.out.println(convStr);  // 結果をコンソール画面に出力
    }
    
    // 文字コード部分のみを文字に変換するメソッド
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
