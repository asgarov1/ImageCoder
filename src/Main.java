import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            stringList.add(i, "ASCII stands for American Standard Code for Information Interchange. Computers can only understand numbers, so an ASCII code is the numerical representation of a character such as 'a' or '@' or an action of some sort. ASCII was developed a long time ago and now the non-printing characters are rarely used for their original purpose. Below is the ASCII character table and this includes descriptions of the first 32 non-printing characters. ASCII was actually designed for use with teletypes and so the descriptions are somewhat obscure. If someone says they want your CV however in ASCII format, all this means is they want 'plain' text with no formatting such as tabs, bold or underscoring - the raw format that any computer can understand. This is usually so they can easily import the file into their own applications without issues. Notepad.exe creates ASCII text, or in MS Word you can save a file as 'text only' ");
        }

        System.out.println("start: " + LocalTime.now());
        List<String> list = encrypt(stringList);
        colorEncrupt(list);

        colorDecrypt("codedImage.png");

        System.out.println("end: " + LocalTime.now());


    }

    private static void colorDecrypt(String path){
        File file = new File("codedImage.png");
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("not found the image");
        }

        List<String> list = new ArrayList<>();


        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                String result = String.valueOf(new Color(bufferedImage.getRGB(j,i)));
                int r = Integer.parseInt(result.substring(17, 18));
                int g = Integer.parseInt(result.substring(21, 22));
                int b = Integer.parseInt(result.substring(25, 26));
                int rgb = r*100+g*10+b;
                // 255 255 255 = 100

                final int ASCII_MAX_VALUE = 255;
                final int ignoreCase = 0;


                if (rgb==ignoreCase)
                    continue;
                else if(rgb<ASCII_MAX_VALUE)
                    list.add(result.substring(17, 18) + result.substring(21, 22) + result.substring(25, 26));
                else
                    list.add(result.substring(17,18) + result.substring(21,22));
            }
        }

        System.out.println("------------------------------");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            char c = (char)Integer.parseInt(list.get(i));
            sb.append(c);
        }

        System.out.println(sb);



    }

    public static List<String> encrypt(List<String> message){
        List<String> list = new ArrayList<>();

        for (String s : message) {
            char[] array = s.toCharArray();
            for (int i = 0; i < array.length; i++) {
                list.add(String.valueOf((array[i] > 99) ? array[i] : array[i] * 10));
            }
        }

        return list;
    }

    public static void colorEncrupt(List<String> list) throws IOException {
        int size=0;

        for (int i = 0; i < list.size(); i++) {
            size += list.get(i).length()/3;
        }

        int width, height;

        if(size%2==0)
            width=height=(int)(size/Math.sqrt((double)size));
        else {
            width = (int)(size/Math.sqrt((double)size))+1;
            height = (int)(size/Math.sqrt((double)size));
        }


        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int index=0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(index<size) {
                    int r = Integer.parseInt(String.valueOf(list.get(index).charAt(0)));
                    int g = Integer.parseInt(String.valueOf(list.get(index).charAt(1)));
                    int b = Integer.parseInt(String.valueOf(list.get(index).charAt(2)));
                    bufferedImage.setRGB(j, i, new Color(r,g,b).getRGB());

//                    {
//                        System.out.print("" + r + g + b + " ");
//
//                        String temp = "";
//                        temp += r;
//                        temp += g;
//                        if (b != 0)
//                            temp += b;
//
//                        char c = (char) Integer.parseInt(temp);
//                        System.out.println(c);
//                    }
                }
                    index++;

            }
        }

        File file = new File("codedImage.png");
        ImageIO.write(bufferedImage, "png", file);


    }
}
