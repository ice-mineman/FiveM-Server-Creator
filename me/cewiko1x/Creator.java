package me.cewiko1x;


import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Creator implements ActionListener {

    public static final Creator INSTANCE = new Creator();

    public static URL artifactsURL;
    public static URL cfxURL;
    File fiveMServer = null;
    String appData = System.getenv("APPDATA");
    String cache = appData + "/SC";
    File cacheFile = new File(cache);
    File artifactFile;
    File cfxFile;
    File serverCfg;
    File starter;
    File runCmd;
    public static ZipUtility zipUtility = new ZipUtility();

    public void init() throws IOException {
        artifactsURL = new URL("https://runtime.fivem.net/artifacts/fivem/build_server_windows/master/2315-c593b6e56bad578a3dda57454640c970276ada54/server.zip");
        cfxURL = new URL("https://github.com/citizenfx/cfx-server-data/archive/master.zip");

        JFrame frame = new JFrame("FiveM Server");
        JFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton installButton = new JButton("Start Install");
        JButton directoryButton = new JButton("Select FiveM Server Directory");
        JButton githubButton = new JButton("Github");
        JButton clearCacheButton = new JButton("Clear Cache");
        JLabel title = new JLabel("FXServer Creator");
        title.setFont(new Font("Verdana", Font.PLAIN, 18));
        title.setVisible(true);
        title.setBounds(10,0,300,40);
        installButton.setBounds(10,50,200,30);
        installButton.setFont(new Font("Verdana", Font.PLAIN, 14));
        githubButton.setVisible(true);
        githubButton.setBounds(10, 160, 200, 30);
        clearCacheButton.setVisible(true);
        clearCacheButton.setBounds(10,200, 200,30);

        clearCacheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
if(cacheFile.exists()) {
    cacheFile.delete();
} else {
    
}
            }
        });

        installButton.addActionListener(this);

        directoryButton.setBounds(10,100, 200, 50);
        directoryButton.setFont(new Font("Verdana", Font.PLAIN, 10));

        githubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/cewiko1x"));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        directoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(cacheFile.exists()) {
                    System.out.println("Cache already exists");
                } else {
                    cacheFile.mkdir();
                }


                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Select your FiveM Server Directory");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    fiveMServer = chooser.getSelectedFile();
                    System.out.println("FiveM Server Directory is: " + fiveMServer.getAbsolutePath());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });

        frame.add(installButton);
        frame.add(githubButton);
        frame.add(directoryButton);
        frame.add(title);
        frame.add(clearCacheButton);
        frame.setSize(400,400);
        frame.setLayout(null);
        frame.setVisible(true);



    }

    public static Creator getInstance() {
        return INSTANCE;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(fiveMServer == null) {
            return;
        }

        try {
            artifactFile = new File(cacheFile.getAbsolutePath() + "/artifacts.zip");
            cfxFile = new File(cacheFile.getAbsolutePath() + "/cfx.zip");
            FileUtils.copyURLToFile(artifactsURL, artifactFile);
            FileUtils.copyURLToFile(cfxURL, cfxFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
          zipUtility.unzip(cfxFile.getAbsolutePath(), fiveMServer.getAbsolutePath());
          zipUtility.unzip(artifactFile.getAbsolutePath(), fiveMServer.getAbsolutePath());
            System.out.println("Unzipped CFX And Artifacts");
            serverCfg = new File(fiveMServer.getAbsolutePath() + "/cfx-server-data-master/server.cfg");
            starter = new File(fiveMServer.getAbsolutePath() + "/starter.bat");
            runCmd = new File(fiveMServer.getAbsolutePath() + "/run.cmd");

            PrintWriter runCmdWriter = new PrintWriter(runCmd, "UTF-8");
            runCmdWriter.print("@echo off\n" +
                    "%~dp0\\FXServer +set citizen_dir %~dp0\\citizen\\ %*");
            runCmdWriter.close();

            PrintWriter starterWriter = new PrintWriter(starter, "UTF-8");
            starterWriter.println("cd/d " + fiveMServer.getAbsolutePath() + "/cfx-server-data-master/ ");
            starterWriter.println(fiveMServer.getAbsolutePath() + "/run.cmd +exec server.cfg");
            System.out.println("Writing to starter.bat");
            starterWriter.close();

            PrintWriter cfgWriter = new PrintWriter(serverCfg, "UTF-8");
          cfgWriter.print(MainClass.serverCfgText);
            System.out.println("Writing to server.cfg");
            cfgWriter.close();
            String license = "";

            if(serverCfg != null) {
               license = String.valueOf(
                        JOptionPane.showInputDialog(this, "License key (keymaster.fivem.net:"));
            }

            ZipUtility.modifyFile(serverCfg.getAbsolutePath(), "changeme", license);
            System.out.println(serverCfg.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
