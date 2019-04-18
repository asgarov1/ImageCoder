import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        List<String> stringList = new ArrayList<String>();
        stringList.add("Java is the best");
        List<String> list = encrypt(stringList);
        colorEncrupt(list);

        colorDecrypt("codedImage.png");

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
                String result = String.valueOf(new Color(bufferedImage.getRGB(i,j)));

                if(!result.substring(25,26).equals("0"))
                    list.add(result.substring(17,18) + result.substring(21,22) +result.substring(25,26));
                else
                    list.add(result.substring(17,18) + result.substring(21,22));
            }
        }

        System.out.println("------------------------------");
        for (String s : list) {
            System.out.print(s );
            char c = (char)Integer.parseInt(s);
            System.out.print(c);
            System.out.println();
        }

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

                    {
                        System.out.print("" + r + g + b + " ");

                        String temp = "";
                        temp += r;
                        temp += g;
                        if (b != 0)
                            temp += b;

                        char c = (char) Integer.parseInt(temp);
                        System.out.println(c);
                    }
                }
                    index++;

            }
        }

        File file = new File("codedImage.png");
        ImageIO.write(bufferedImage, "png", file);


    }
}
