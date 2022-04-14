

import java.io.*;
import java.net.*;

public class TCPMultiThreadEchoServer{
    private static final int BUFFLEN = 1024;
    private static final int TELNET_PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(TELNET_PORT)) {
            while(true){
                Socket clientSocket = serverSocket.accept();
                InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                System.out.println("connection from port= " + remote.getPort() + " host " + remote.getHostName());       
                new Thread(
                    () -> {
                        InputStream in = null;
                        try {
                            in = clientSocket.getInputStream();
                            OutputStream out = clientSocket.getOutputStream();
                            byte[] buffer = new byte[BUFFLEN];
                            while (clientSocket.isConnected()){
                                int len = in.read(buffer);
                                if (len > 0) {
                                    out.write(buffer, 0, len);
                                }
                            }
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                ).start();
            }
        }
    }
        
}