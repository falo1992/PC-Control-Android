package com.znajde.gdzie.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ServerService extends Service {


    private static SSLSocket socket;;
    private static ArrayList<String> availableAddresses = new ArrayList<>();

    public ServerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public static boolean checkIfRunning(SocketAddress socketAddress){
        try{
            Socket testSocket = new Socket();
            testSocket.connect(socketAddress, 50);
            testSocket.close();
            return true;
        }catch(IOException e){
        }
        return false;
    }

    public static void checkForDevices() throws IOException {
        ArrayList<String> hostAddresses = new ArrayList<>();
        String subnet;
        int start;
        int end;
        InetAddress inet = InetAddress.getLocalHost();
        InetAddress[] allHostAddresses = InetAddress.getAllByName(inet.getCanonicalHostName());
        for (InetAddress address: allHostAddresses) {
            if(address instanceof Inet4Address){
                hostAddresses.add(address.toString());
            }
        }
        for(String hostAddress: hostAddresses){
            start = hostAddress.indexOf("/");
            end = hostAddress.lastIndexOf(".");
            subnet = hostAddress.substring(start + 1, end);
            for(int i=1;i<255;i++){
                String host = subnet+"."+i;
                if(checkIfRunning(new InetSocketAddress(host, 2000))){
                    availableAddresses.add(host);
                    System.out.println(host+" jest dostępny.");
                }
            }
        }
        System.out.println("Sprawdzono");
    }


}
