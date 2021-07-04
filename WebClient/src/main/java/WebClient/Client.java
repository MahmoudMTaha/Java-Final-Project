package WebClient;

import org.apache.commons.io.IOUtils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Client {
    public static void testStringResource(String res)throws IOException{
        String source =String.format("http://localhost:8080%s",res);

        URL url = new URL(source);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        System.out.println(body);

    }
    public static void testImgResource(String res)throws IOException{
        String source =String.format("http://localhost:8080%s",res);
        URL url = new URL(source);
        URLConnection con = url.openConnection();
        File outputFile = new File("src/main/resources/targetFile.jpg");
        try (InputStream in = con.getInputStream()) {
            BufferedImage image = ImageIO.read(in);
            try (OutputStream os = new FileOutputStream(outputFile)) {
                ImageIO.write(image, "jpg", os);
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        Desktop.getDesktop().open(new File("src/main/resources/targetFile.jpg"));
    }

        public static void main(String[] args) throws IOException {
            testStringResource("/readcsv?limit:10");
            testStringResource("/summary");
            testStringResource("/structure");
            testStringResource("/cleandata");
            testStringResource("/popularjob");
            testImgResource("/jobbarchart");
            testStringResource("/companies");
            testImgResource("/companypiechart");
            testStringResource("/populararea");
            testImgResource("/areabarchart");
            testStringResource("/mostrequiredskills");
            testStringResource("/yearsofexp");






        }

    }
