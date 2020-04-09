package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class writeStringToDisk {

    public static void  writeHtml(String content, String name) throws IOException {


        File file =new File("D:/spider/html/"+name+".html");
        if(!file.exists()){
            file.createNewFile();
        }

        //使用true，即进行append file
        FileOutputStream outputStream = new FileOutputStream(file);

        outputStream.write(content.getBytes());
        outputStream.close();


    }

}
