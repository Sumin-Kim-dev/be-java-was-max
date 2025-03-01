package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.util.ResponseUtil;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            if (line == null) return;
            Request request = Request.of(line);
            logger.debug("Request line : {}", line);

            if (request.method.equals("GET") && request.requestUrl.endsWith("/user/create")) {
                User user = User.of(request.query);
                logger.debug("User : {}", user);
            }

            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("Header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(ResponseUtil.getAbsolutePath(request.requestUrl)).toPath());
            responseBody(dos, body, ResponseUtil.getType(request.requestUrl));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + type + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body, String type) {
        try {
            response200Header(dos, body.length, type);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
