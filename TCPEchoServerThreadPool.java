import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPEchoServerThreadPool {
        private static final int TELNET_PORT = 8080;

        public static void main(String[] args) throws IOException{
            try (ServerSocket serverSocket = new ServerSocket(TELNET_PORT)) {
                final ExecutorService service = Executors.newFixedThreadPool(2);
                while (true){
                    final Socket clientSocket = serverSocket.accept();
                    final InetSocketAddress remote = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
                    System.out.println("connection from port " + remote.getPort() + "host= " + remote.getHostName());
                    service.submit(
                        () -> {
                            try (final InputStream in = clientSocket.getInputStream();
                            final OutputStream out = clientSocket.getOutputStream()){
                                while (clientSocket.isConnected() && in.transferTo(out) > 0);
                                System.out.println("disconnection from port= " + remote.getPort() + " host= " + remote.getHostName());
                                } catch (IOException e){
                                    e.printStackTrace();
                                }

                        }
                    );
                }
            }
            
        }
}