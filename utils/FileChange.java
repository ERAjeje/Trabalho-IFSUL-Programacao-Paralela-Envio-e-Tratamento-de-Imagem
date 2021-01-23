package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class FileChange {
    
    public byte[] imageToByteArray(String path) throws Exception {
        /**
         * cria um file buffered da imagem e 
         * um strem array de saida
         */

        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        /**
         * escreve a imagem dentro do strem
         * array de saida e retorna
         */

        ImageIO.write(image, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void arrayToImage(byte[] imageBytes, String path) throws Exception {
        /**
         * cria um outputstream para gravar a imagem, 
         * grava o array de bytes da imagem nele e
         * extrai o descritor da imagem
         */

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        fileOutputStream.write(imageBytes);
        FileDescriptor fileDescriptor = fileOutputStream.getFD();

        /**
         * salva o conteudo do buffer no arquivo de imagem
         */
        
        fileOutputStream.flush();
        fileDescriptor.sync();
        fileOutputStream.close();
    }
}
