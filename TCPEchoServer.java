import java.io.*;
import java.net.*;
// import java.io.IOExeption;
// import java.io.InputStream;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;
// import java.net.ServerSocket;
// import java.net.socket;


public class TCPEchoServer {
    private static final int TELNET_port = 8080;
    private static final int BUFFLEN = 1024;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(TELNET_port)) {
            Socket clientSocket = serverSocket.accept();
            InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
            System.out.println("connection from port" + remote.getPort() + " host= " + remote.getHostName());
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            byte[] buffer = new byte[BUFFLEN];
            while (clientSocket.isConnected()) {
                int len = in.read(buffer);
                if (len > 0) {
                    out.write(buffer, 0, len);
                }
            }
        }
    }
}