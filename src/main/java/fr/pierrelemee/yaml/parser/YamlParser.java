package fr.pierrelemee.yaml.parser;

import fr.pierrelemee.yaml.Node;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class YamlParser {

    public Node parse(Reader reader) throws Exception {
        ArrayList<Node> hierarchy = new ArrayList<Node>();
        Node root, node;
        root = new Node();
        int cursor;
        char character;
        int nbSpaces = 0;
        StringBuilder nameBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();
        int width = 4;
        boolean colonFound = false;
        while ((cursor = reader.read()) != -1) {
            character = (char) cursor;
            switch(character) {
                case ' ':
                    if (!colonFound) {
                        if (nameBuffer.length() == 0) {
                            nbSpaces++;
                        }
                    } else {
                        if (valueBuffer.length() > 0) {
                            valueBuffer.append(character);
                        }
                    }
                    break;
                case '\n':
                    if (nameBuffer.length() > 0) {
                        node = valueBuffer.length() > 0 ? new Node(nameBuffer.toString(), valueBuffer.toString()) :new Node(nameBuffer.toString());
                        if (nbSpaces / width == 0) {
                            root.addChild(node);
                        } else {
                            hierarchy.get(nbSpaces/width - 1).addChild(node);
                        }
                        for(int i = hierarchy.size() - 1;i >= (nbSpaces / width); i--) {
                            hierarchy.remove(i);
                        }
                        hierarchy.add(nbSpaces / width, node);
                    }
                    nbSpaces = 0;
                    nameBuffer.delete(0, nameBuffer.length());
                    valueBuffer.delete(0, valueBuffer.length());
                    colonFound = false;
                    break;
                case ':':
                    colonFound = true;
                    break;
                default:
                    if(!colonFound) {
                        nameBuffer.append(character);
                    } else {
                        valueBuffer.append(character);
                    }
            }
        }
        if (nameBuffer.length() > 0) {
            node = valueBuffer.length() > 0 ? new Node(nameBuffer.toString(), valueBuffer.toString()) :new Node(nameBuffer.toString());
            if (nbSpaces / width == 0) {
                root.addChild(node);
            } else {
                hierarchy.get(nbSpaces/width - 1).addChild(node);
            }
            for(int i = hierarchy.size() - 1;i >= (nbSpaces / width); i--) {
                hierarchy.remove(i);
            }
            hierarchy.add(nbSpaces / width, node);
        }

        return root;
    }

    public Node parse(File yaml) throws Exception{
        return this.parse(new FileReader(yaml));
    }

}
