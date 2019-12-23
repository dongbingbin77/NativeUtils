package com.dongbingbin.nativeutils.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketServer {
	
	//private final static Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);

    public void startActionAsync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                startAction();
            }
        }).start();
    }

    private void startAction(){
        ServerSocket serverSocket=null;
        try {  
            serverSocket=new ServerSocket(7755);  //端口号
            //LOGGER.info("服务端服务启动监听：");
            //通过死循环开启长连接，开启线程去处理消息
            while(true){
                Socket socket=serverSocket.accept();
                new Thread(new MyRuns(socket)).start();
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (serverSocket!=null) {  
                    serverSocket.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
    }  
      
    class MyRuns implements Runnable{
          
        Socket socket;  
        BufferedReader reader;
        BufferedWriter writer;
  
        public MyRuns(Socket socket) {  
            super();  
            this.socket = socket;  
        }  
  
        public void run() {  
            try {  
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//读取客户端消息
                writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//向客户端写消息
                String lineString="";  
               
                while(!(lineString=reader.readLine()).equals("bye")){
                	//LOGGER.info("收到来自客户端的发送的消息是：" + lineString);
                    writer.write("服务器返回："+lineString+"\n");  
                    writer.flush();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    if (reader!=null) {  
                        reader.close();  
                    }  
                    if (writer!=null) {  
                        writer.close();  
                    }  
                    if (socket!=null) {  
                        socket.close();  
                    }  
                } catch (Exception e2) {  
                    e2.printStackTrace();  
                }  
            }  
        }  
          
    }  
}