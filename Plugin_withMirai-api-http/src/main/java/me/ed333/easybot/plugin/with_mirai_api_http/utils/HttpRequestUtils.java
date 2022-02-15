package me.ed333.easybot.plugin.with_mirai_api_http.utils;



import com.google.gson.JsonObject;
import me.ed333.easybot.api.messages.DEBUG;
import me.ed333.easybot.plugin.with_mirai_api_http.Configs;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * https://blog.csdn.net/u012513972/article/details/79569888
 */
public class HttpRequestUtils {
    public static boolean canConnect() {
        return !doGet(String.format("http://%s/about", Configs.HOST.getString())).equals("null");
    }

    public static String doGet(String httpUrl) {
        String result = "null";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                }
                result = sb.toString();
                is.close();
                br.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println(String.format("请求无响应：%s", e.getMessage()).replace("\n", ""));
        }
        return result;
    }

    public static String doPost(String httpUrl, @NotNull JsonObject param) {
        String result = "null";
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream os = connection.getOutputStream();
            os.write(param.toString().getBytes(StandardCharsets.UTF_8));
            os.close();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                }
                result = sb.toString();
                is.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println(String.format("请求无响应：%s", e.getMessage()).replace("\n", ""));
        }
        return result;
    }
}