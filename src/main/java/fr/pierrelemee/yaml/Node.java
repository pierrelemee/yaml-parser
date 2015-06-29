package fr.pierrelemee.yaml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Node implements Iterable<Node> {

    private static final Character NAME_SEPARATOR = '.';

    protected String name;
    protected String value;
    protected List<Node> children;

    public Node() {
        this(null);
    }

    public Node(String name) {
        this(name, null);
    }

    public Node(String name, String value) {
        this.name = name;
        this.value = value;
        this.children = new LinkedList<Node>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public boolean hasChild(String name) {
        for(Node child: this.children) {
            if(child.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find child node by path.
     * Paths are defined by the combination of dots separated names.
     * @param path String
     * @return Node the children node
     */
    public Node get(String path) {
        if (path.indexOf('.') != -1) {
            return this.getChild(path.substring(0, path.indexOf('.'))).get(path.substring(path.indexOf(NAME_SEPARATOR) + 1));
        } else {
            return this.getChild(path);
        }
    }

    public Node getChild(String name) {
        if (!name.equals("")) {
            for (Node child : this.children) {
                if (child.name.equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return this.value;
    }

    public Iterator<Node> iterator() {
        return this.children.iterator();
    }

    public void forEach(Consumer<? super Node> action) {
        this.children.forEach(action);
    }

    public Spliterator<Node> spliterator() {
        return this.children.spliterator();
    }

    public void repr() {
        this.repr(0);
    }

    public void repr(int depth) {
        StringBuilder builde = new StringBuilder();
        if(this.name == null) {
            for (Node child : this.children) {
                child.repr(0);
            }
        } else {
            for (int i = 0; i < depth; i++) {
                builde.append("\t");
            }
            builde.append(this.name + ":");
            if (this.value != null) {
                builde.append(" " + this.value);
            }
            System.out.println(builde.toString());
            for (Node child : this.children) {
                child.repr(depth + 1);
            }
        }
    }


}
