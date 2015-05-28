package fr.pierrelemee.yaml;

import fr.pierrelemee.yaml.parser.YamlParser;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            YamlParser parser = new YamlParser();
            InputStream in = Main.class.getResourceAsStream("/example.yml");
            Node node = parser.parse(new InputStreamReader(in));
            node.repr();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
