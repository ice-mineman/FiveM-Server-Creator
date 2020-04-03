package io.github.cewiko1x;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainClass {

    public static String serverCfgText =
            "# Only change the IP if you're using a server with multiple network interfaces, otherwise change the port only.\n" +
                    "endpoint_add_tcp \"0.0.0.0:30120\"\n" +
                    "endpoint_add_udp \"0.0.0.0:30120\"\n" +
                    "\n" +
                    "# These resources will start by default.\n" +
                    "ensure mapmanager\n" +
                    "ensure chat\n" +
                    "ensure spawnmanager\n" +
                    "ensure sessionmanager\n" +
                    "ensure fivem\n" +
                    "ensure hardcap\n" +
                    "ensure rconlog\n" +
                    "ensure scoreboard\n" +
                    "\n" +
                    "# This allows players to use scripthook-based plugins such as the legacy Lambda Menu.\n" +
                    "# Set this to 1 to allow scripthook. Do note that this does _not_ guarantee players won't be able to use external plugins.\n" +
                    "sv_scriptHookAllowed 0\n" +
                    "\n" +
                    "# Uncomment this and set a password to enable RCON. Make sure to change the password - it should look like rcon_password \"YOURPASSWORD\"\n" +
                    "#rcon_password \"\"\n" +
                    "\n" +
                    "# A comma-separated list of tags for your server.\n" +
                    "# For example:\n" +
                    "# - sets tags \"drifting, cars, racing\"\n" +
                    "# Or:\n" +
                    "# - sets tags \"roleplay, military, tanks\"\n" +
                    "sets tags \"default\"\n" +
                    "\n" +
                    "# A valid locale identifier for your server's primary language.\n" +
                    "# For example \"en-US\", \"fr-CA\", \"nl-NL\", \"de-DE\", \"en-GB\", \"pt-BR\"\n" +
                    "sets locale \"root-AQ\" \n" +
                    "# please DO replace root-AQ on the line ABOVE with a real language! :)\n" +
                    "\n" +
                    "# Set an optional server info and connecting banner image url.\n" +
                    "# Size doesn't matter, any banner sized image will be fine.\n" +
                    "#sets banner_detail \"https://url.to/image.png\"\n" +
                    "#sets banner_connecting \"https://url.to/image.png\"\n" +
                    "\n" +
                    "# Set your server's hostname\n" +
                    "sv_hostname \"FXServer, but unconfigured\"\n" +
                    "\n" +
                    "# Nested configs!\n" +
                    "#exec server_internal.cfg\n" +
                    "\n" +
                    "# Loading a server icon (96x96 PNG file)\n" +
                    "#load_server_icon myLogo.png\n" +
                    "\n" +
                    "# convars which can be used in scripts\n" +
                    "set temp_convar \"hey world!\"\n" +
                    "\n" +
                    "# Uncomment this line if you do not want your server to be listed in the server browser.\n" +
                    "# Do not edit it if you *do* want your server listed.\n" +
                    "#sv_master1 \"\"\n" +
                    "\n" +
                    "# Add system admins\n" +
                    "add_ace group.admin command allow # allow all commands\n" +
                    "add_ace group.admin command.quit deny # but don't allow quit\n" +
                    "add_principal identifier.fivem:1 group.admin # add the admin to the group\n" +
                    "\n" +
                    "# Hide player endpoints in external log output.\n" +
                    "sv_endpointprivacy true\n" +
                    "\n" +
                    "# enable OneSync with default configuration (required for server-side state awareness)\n" +
                    "onesync_enabled true\n" +
                    "\n" +
                    "# Server player slot limit (must be between 1 and 32, unless using OneSync)\n" +
                    "sv_maxclients 32\n" +
                    "\n" +
                    "# Steam Web API key, if you want to use Steam authentication (https://steamcommunity.com/dev/apikey)\n" +
                    "# -> replace \"\" with the key\n" +
                    "set steam_webApiKey \"\"\n" +
                    "\n" +
                    "# License key for your server (https://keymaster.fivem.net)\n" +
                    "sv_licenseKey changeme";

    public static void main(String[] args) {
        UIManager.put( "control", new Color( 128, 128, 128) );
        UIManager.put( "info", new Color(128,128,128) );
        UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
        UIManager.put( "nimbusFocus", new Color(115,164,209) );
        UIManager.put( "nimbusGreen", new Color(176,179,50) );
        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
        UIManager.put( "nimbusOrange", new Color(191,98,4) );
        UIManager.put( "nimbusRed", new Color(169,46,34) );
        UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
        UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
        UIManager.put( "text", new Color( 230, 230, 230) );
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Creator.getInstance().init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(serverCfgText);
    }
}
