import utils.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws Exception {

        String imagePath = "D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\Trabalho Pratico 1\\Trabalho\\server\\receivide\\";

        int port = 4000;
//        DatagramSocket serverDatagramSocket = new DatagramSocket(port);
        Socket serverSocket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(port);
        System.out.println("Server ok");

        /**
         * receber o tamanho da imagem do cliente
         */

        byte[] letterToReceive = new byte[8];
        DatagramPacket envelopeToReceive = new DatagramPacket(letterToReceive, letterToReceive.length);
        serverDatagramSocket.receive(envelopeToReceive);
        letterToReceive = envelopeToReceive.getData();
        String message = new String(letterToReceive);
        int imageSize = Integer.parseInt(message.trim());

        /**
         * extrair os dados do remetente pelo envelope
         */

        InetAddress ipClient = envelopeToReceive.getAddress();
        int clientPort = envelopeToReceive.getPort();


        FileChange fileChange = new FileChange();

        if(imageSize <= 42912){

            /**
             * receber a imagem
             */

            byte[] letterWithImage = new byte[imageSize];
            DatagramPacket envelopeWithImage = new DatagramPacket(letterWithImage, letterWithImage.length);
            serverDatagramSocket.receive(envelopeWithImage);

            /**
             * código de manipulação de arquivo byte - imagem e imagem - byte
             */

            fileChange.arrayToImage(envelopeWithImage.getData(), imagePath+"recebida.jpg");

        }else {
            byte[] letterWithAmount = new byte[8];
            DatagramPacket envelopeWithAmount = new DatagramPacket(letterWithAmount, letterWithAmount.length);
            serverDatagramSocket.receive(envelopeWithAmount);
            letterWithAmount = envelopeWithAmount.getData();
            String message1 = new String(letterWithAmount);
            int tam = Integer.parseInt(message1.trim());
            for (int i = 0; i < tam; i++) {
                byte[] letterWithImage = new byte[imageSize];
                DatagramPacket envelopeWithImage = new DatagramPacket(letterWithImage, letterWithImage.length);
                serverDatagramSocket.receive(envelopeWithImage);

                /**
                 * código de manipulação de arquivo byte - imagem e imagem - byte
                 */

                fileChange.arrayToImage(envelopeWithImage.getData(), imagePath+i+"recebida.jpg");
            }
            ImageDivider.groupImages(imagePath);
        }


        /**
         * manipulação da imagem para escala de cinza
         */

        // divide a imagem em quatro partes
        ArrayList<BufferedImage> images = new ArrayList<>();
        ImageDivider.imageDivider(imagePath+"recebida.jpg", images);

        // prepara as partes para iniciar as threads
        Grayscale grayscale = new Grayscale(images.get(0));
        Grayscale grayscale1 = new Grayscale(images.get(1));
        Grayscale grayscale2 = new Grayscale(images.get(2));
        Grayscale grayscale3 = new Grayscale(images.get(3));

        // inicializa as threads
        grayscale.start();
        grayscale1.start();
        grayscale2.start();
        grayscale3.start();
        ImageDivider.groupImages("D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\Trabalho Pratico 1\\Trabalho\\server\\grayscale\\");
        
        /**
         * enviar a imagem
         */
        File file = new File(imagePath);
        byte[] letterWithImageToSend = new byte[(int)file.length()];
        
        /**
         * código de manipulação de arquivo byte - imagem e imagem - byte
         */

        imagePath = "D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\Trabalho Pratico 1\\Trabalho\\server\\grayscale\\combined\\combined.jpg";

        letterWithImageToSend = fileChange.imageToByteArray(imagePath);

        DatagramPacket envelopeWithImageToSend = new DatagramPacket(letterWithImageToSend, letterWithImageToSend.length, ipClient, clientPort);
        serverDatagramSocket.send(envelopeWithImageToSend);

        serverDatagramSocket.close();
    }
}
