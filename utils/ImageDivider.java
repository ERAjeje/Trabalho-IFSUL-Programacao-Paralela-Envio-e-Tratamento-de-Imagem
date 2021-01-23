package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ImageDivider {

    public static ArrayList<BufferedImage> imageDivider(String path, ArrayList<BufferedImage> image) throws Exception {

        BufferedImage original = ImageIO.read(new File(path));
        int height = original.getHeight();
        int width = original.getWidth();
        int middleHeight = height / 2;
        int middleWidth = width / 2;

        image.add(original.getSubimage(0, middleHeight, middleWidth, middleHeight));
        image.add(original.getSubimage(0,0, middleWidth, middleHeight));
        image.add(original.getSubimage(middleWidth, middleHeight, middleWidth, middleHeight));
        image.add(original.getSubimage(middleWidth, 0, middleWidth, middleHeight));

        return image;
    }

    public static ArrayList<BufferedImage> imageDivideToSend (String path, ArrayList<BufferedImage> image) throws Exception {

        int base = 499;
        BufferedImage original = ImageIO.read(new File(path));
        int height = original.getHeight();
        int width = original.getWidth();
        int baseHeight = height / 3;
        int baseWidth = width / base;
        System.out.println(baseWidth);

        for (int i = 0; i < 3; i+=base) {
            System.out.println(i+"i "+(i+base)+" "+width);
            for (int j = 0; j < 3; j+=base) {
                System.out.println(j+"j "+(j+base)+" "+height);
                image.add(original.getSubimage(
                        i < width ? i : width,
                        j < height ? j : height,
                        i+base < width ? i+base : width,
                        j+base < height ? j+base : height
                ));
            }
        }

        /*
        image.add(original.getSubimage(0, middleHeight, middleWidth, middleHeight));
        image.add(original.getSubimage(0,0, middleWidth, middleHeight));
        image.add(original.getSubimage(middleWidth, middleHeight, middleWidth, middleHeight));
        image.add(original.getSubimage(middleWidth, 0, middleWidth, middleHeight));
         */

        return image;
    }

    static public void groupImages(String newPath) throws Exception {

        /**
         * verifica se o arquivo com as imagens combinadas existe
         * e se existir, deleta o arquivo.
         */
        Boolean success = true;
        if(new File(newPath+ "combined/combined.jpg").isFile()){
            success = new File(newPath+ "combined/combined.jpg").delete();
        }
        if(success){
            /**
             * foi preciso incluir esse tempo de um segundo para conseguir realizar as operações
             * de leitura e escrita no disco. Como as imagens são pequenas e os processos ficam
             * rápidos com as threads, estava acontecendo um erro de enviar a imagem do teste anterior.
             * isso resolveu o problema dando um tempo pro IO.
             */
            TimeUnit.SECONDS.sleep(1);

            /**
             * faz a leitura de cada uma das partes da imagem já em escala de cinza
             */
            BufferedImage image0 = ImageIO.read(new File(newPath+"gayscaleThread[Thread-0,5,main].jpg"));
            BufferedImage image1 = ImageIO.read(new File(newPath+"gayscaleThread[Thread-1,5,main].jpg"));
            BufferedImage image2 = ImageIO.read(new File(newPath+"gayscaleThread[Thread-2,5,main].jpg"));
            BufferedImage image3 = ImageIO.read(new File(newPath+"gayscaleThread[Thread-3,5,main].jpg"));

            /**
             * cria uma BufferedImage no tamano da imagem original e com as mesmas configurações
             */
            BufferedImage combined = new BufferedImage(image0.getWidth()*2,image0.getHeight()*2, BufferedImage.TYPE_INT_RGB);

            /**
             * combina as quatro partes em uma nova imagem inteira
             */
            Graphics graphics = combined.getGraphics();
            graphics.drawImage(image1, 0, 0, null);
            graphics.drawImage(image0, 0, image0.getHeight(), null);
            graphics.drawImage(image2, image0.getWidth(), image0.getHeight(), null);
            graphics.drawImage(image3, image0.getWidth(), 0, null);
            graphics.dispose();

            /**
             * faz a gravação da imagem no disco
             */
            ImageIO.write(combined, "jpg", new File(newPath+"combined/combined.jpg"));
        } else {
            System.out.println("Erro ao deletar o arquivo");
        }
    }
}
