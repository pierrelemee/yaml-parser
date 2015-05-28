package fr.pierrelemee.yaml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Node implements Iterable<Node> {

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
