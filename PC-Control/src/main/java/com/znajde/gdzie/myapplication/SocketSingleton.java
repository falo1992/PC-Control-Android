package com.znajde.gdzie.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.*;
import javax.net.ssl.TrustManagerFactory;

public class SocketSingleton {

    private static SSLSocketFactory socketFactory;
    private static InetSocketAddress serverAddress;
    private static SSLSocket socket;
    private static SocketSingleton instance;
    private static Context context;
    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private static Object msgObj;
    private static boolean mute;
    private static String msg;

    public void SocketSingleton(){

    }

    public static void init(Context appContext, String hostAddress, int hostPort) {
        if (instance == null) {
            serverAddress = new InetSocketAddress(hostAddress, hostPort);
            context = appContext;
            initSingleton();
        }
    }

    public static void initSingleton(){
        instance = new SocketSingleton();
        setTrustStore();
        try{
            socket = (SSLSocket) socketFactory.createSocket(serverAddress.getHostName(), serverAddress.getPort());
            socket.startHandshake();
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void setTrustStore(){
        InputStream stream = context.getResources().openRawResource(R.raw.client);
        KeyStore trustStore;
        try {
            trustStore = KeyStore.getInstance("BKS");
            trustStore.load(stream, "changeit".toCharArray());
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(trustStore, "changeit".toCharArray());
            KeyManager[] keymanagers =  kmfactory.getKeyManagers();

            TrustManagerFactory tmf=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext=SSLContext.getInstance("TLS");

            sslContext.init(keymanagers, tmf.getTrustManagers(), new SecureRandom());

            socketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static SocketSingleton getInstance() {
        if (instance == null ){
            return null;
        }
        return instance;
    }

    public static boolean shutdown(){
        try {
            objectOutputStream.writeObject(new String("shutdown"));
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean restart(){
        try {
            objectOutputStream.writeObject(new String("restart"));
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean download(String path){

        return true;
    }

    public static boolean send(String path){

        return true;
    }

    public static boolean volumeUp() {
        try {
            objectOutputStream.writeObject(new String("volume up"));
        //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean volumeDown() {
        try {
            objectOutputStream.writeObject(new String("volume down"));
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean volumeMute() {
        try {
            objectOutputStream.writeObject(new String("mute"));
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean volumeUnmute() {
        try {
            objectOutputStream.writeObject(new String("unmute"));
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static boolean volumeGetMute() {
        try {
            objectOutputStream.writeObject(new String("get mute"));
            msgObj = objectInputStream.readObject();
            mute = msgObj instanceof Boolean ? (Boolean) msgObj : null;

            return mute;
        }catch(Exception e) {
        }
        return false;
    }

    public static boolean volumeSet(double volume) {
        try {
            objectOutputStream.writeObject(new String("set volume"));
            objectOutputStream.writeObject(volume);
            //close();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static double volumeGet(){
        try {
            objectOutputStream.writeObject(new String("get volume"));
            msgObj = objectInputStream.readObject();
            return msgObj instanceof Double ? ((Double) msgObj) * 100 : null;
        }catch(Exception e) {
        }
        return 0;
    }

    public static void search(int port){
        ArrayList<String> availableAddresses = new ArrayList<>();
        String subnet;
        String hostAddress = getIPAddress();
        subnet = hostAddress.substring(0, hostAddress.lastIndexOf("."));
        for(int i=1;i<255;i++){
            String host = subnet+"."+i;
            if(checkIfRunning(new InetSocketAddress(host, port))){
                availableAddresses.add(host);
                Search.addListElement(host);
            }
        }
    }

    public static String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;
                        if (isIPv4)
                            return sAddr;
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
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

    public static boolean isConnected(){
        return socket.isConnected();
    }

    public static void close(){
        try{
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public SSLSocket getSocket(){
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }
}
