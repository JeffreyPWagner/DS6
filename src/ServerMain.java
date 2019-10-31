import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ServerMain {
    public static void main(String[] args) throws Exception
    {
        HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8080),0);
        server.createContext("/", new Handler());
        server.start();
    }

    static class Handler implements HttpHandler
    {
        @Override
        public void handle(HttpExchange t) throws IOException
        {
            File file = new File("hello.html").getCanonicalFile();
            Headers h = t.getResponseHeaders();
            h.set("Content-Type","text/html");
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            FileInputStream fs = new FileInputStream(file);
            final byte[] buffer = new byte[0x10000];
            int count = 0;
            while ((count = fs.read(buffer)) >= 0) {
                os.write(buffer,0,count);
            }
            fs.close();
            os.close();
        }
    }
}
