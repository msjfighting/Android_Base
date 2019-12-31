package untils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtils {

    /**
     * @return 读取资源文件中的数据
     */
    public static String getJsonFromAssets(Context context,String fileName){
        // StringBuilder存放读取出的数据
        StringBuilder stringBuilder = new StringBuilder();

        // AssetManager 资源管理器,Open方法打开指定的资源文件,返回InputStream输入流
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // InputStreamReader 字节到字符的桥接器,BufferedReader存放读取字符的缓冲器
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String  line;
            // 循环利用 BufferedReader 的readLine方法读取每一行数据 放入到StringBuilder中 返回数据
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
