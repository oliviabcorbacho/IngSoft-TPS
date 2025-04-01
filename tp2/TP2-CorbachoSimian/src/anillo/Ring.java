package anillo;

public class Ring {
    private static class Node {
        Object cargo;
        Node next;

        Node(Object cargo) {
            this.cargo = cargo;
            this.next = this;
        }
    }

    private Node current;

    public Ring next() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        current = current.next;
        return this;
    }

    public Object current() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        return current.cargo;
    }

    public Ring add(Object cargo) {
        Node newNode = new Node(cargo);
        if (current == null) {
            current = newNode;
        } else {
            newNode.next = current;
            Node aux = current;
            while (aux.next != current) { //osea meto el nodo nuevo atras del curr
                aux = aux.next;
            }
            aux.next = newNode;
            current = newNode; // ajusto el curr
        }
        return this;
    }

    public Ring remove() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }

        if (current.next == current) { // Only one element
            current = null;
        } else {
            Node prev = current;
            while (prev.next != current) {
                prev = prev.next;
            }
            prev.next = current.next;
            current = current.next;
        }
        return this;
    }
}

