import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Grayscale extends Thread {

    private String newPath = "D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\" +
            "Trabalho Pratico 1\\Trabalho\\server\\grayscale\\";

    private BufferedImage bufferedImage;

    public Grayscale(BufferedImage x) {
        this.bufferedImage = x;
    }

    public void grayscaleImage(BufferedImage image) throws Exception {

        /**
         * carrega a imagem para dentro do programa
         */

        BufferedImage grayImage = image;

        /**
         * Faz dois laços for para percorrer os pixels da imagem,
         * pegar os valores, transforma-los em escala de cinza e
         * adiciona-los na BufferedImage grayImage construindo a imagem
         * em escala de cinza que será enviada
         */

        int x, y = 0;
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < height; i++) {
            x=0;
            for (int j = 0; j < width; j++) {
                Color color = new Color(image.getRGB(x,y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int gray = (red + green + blue)/3;
                Color newColor = new Color(gray, gray, gray);
                grayImage.setRGB(x,y,newColor.getRGB());
                x++;
            }
            y++;
        }

        /**
         * utiliza as informações da thread para gerar arquivos com nomes diferentes
         * serão 4 arquivos, um para cada parte da imagem.
         */

        ImageIO.write(grayImage, "jpg", new File(newPath+"gayscale"+Thread.currentThread()+".jpg"));
    }

    @Override
    public void run() {
        super.run();
        try {
            grayscaleImage(this.bufferedImage);
        } catch (Exception err) {
            System.out.println(err);
        }
    }
}
