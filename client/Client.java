import utils.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String args[]) throws Exception {

        InetAddress ip = InetAddress.getByName("127.0.0.1");
        int port = 4000;
        Socket clientSocket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(ip, port);


        /**
         * Obter o nome da imagem via input
         */

        System.out.print("Digite o nome completo da imagem: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        String imagePathSend = "D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\Trabalho Pratico 1\\" +
                "Trabalho\\client\\shared\\"+fileName;

        /**
         * Abrir a conexão com o server
         */

        clientSocket.bind(socketAddress);
        clientSocket.connect(socketAddress);

        /**
         * Envio da imagem para o server
         */

        FileOutputStream file = new FileOutputStream(imagePathSend);
        DataOutputStream dataOutputStream = new DataOutputStream(file);
        dataOutputStream.flush();
        dataOutputStream.close();
        System.out.println("imagem enviada...");

        /**
         * receber a imagem do servidor
         */


        String imagePath = "D:\\Users\\edson\\OneDrive\\BCC IFSUL\\6 Periodo\\Prog Paralela\\Trabalho Pratico 1\\Trabalho\\client\\recevide\\"+"recebida_"+fileName;

        /**
         * código de manipulação de arquivo byte - imagem e imagem - byte
         */



    }
}