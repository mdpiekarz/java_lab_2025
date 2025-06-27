package com.example.guichatclient;

import java.io.*;
import java.net.Socket;

public class ClientReciver extends Thread{
    private Socket socket;
    private PrintWriter writer;
    private HelloController controller;

    //private ClientReciver clientReciver;

    private Boolean isLogged=false;

    public Boolean getIsLogged(){
        return isLogged;
    }

    public ClientReciver(String address, int port) {
        try {
            socket = new Socket(address, port);
            this.controller=controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null){
                String prefix = message.substring(0,2);
                String postfix = message.substring(2);
                switch(prefix) {
                    case "BR"-> controller.broadcast(postfix);
                    case "ON" -> controller.online(postfix);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String message){
        writer.println(message);
    }

    public void login(String name) {
        writer.println("LO "+name);
        isLogged=true;
    }

    public void broadcast(String message) {
        writer.println("BR "+message);
    }

    public void whisper(String message) {
        writer.println("WH "+message);
    }

    public void online() {
        writer.println("ON ");
    }

    public void setController(HelloController controller){
        this.controller=controller;
    }

}
