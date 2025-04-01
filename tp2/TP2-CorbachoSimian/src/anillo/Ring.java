//package anillo;
//
//public class Ring {
//    public Ring next() {
//        return null;
//    }
//
//    public Object current() {
//        return null;
//    }
//
//    public Ring add( Object cargo ) {
//        return null;
//    }
//
//    public Ring remove() {
//        return null;
//    }
//}

package anillo;

public class Ring {
    private static class Node {
        Object cargo;
        Node next;

        Node(Object cargo) {
            this.cargo = cargo;
            this.next = this; // Initially points to itself (circular)
        }
    }

    private Node current;

    public Ring next() {
        if (current == null) {
            throw new IllegalStateException("Ring is empty");
        }
        current = current.next;
        return this;
    }

    public Object current() {
        if (current == null) {
            throw new IllegalStateException("Ring is empty");
        }
        return current.cargo;
    }

    public Ring add(Object cargo) {
        Node newNode = new Node(cargo);
        if (current == null) {
            current = newNode;
        } else {
            newNode.next = current.next;
            current.next = newNode;
            current = newNode; // Move to the new node
        }
        return this;
    }

    public Ring remove() {
        if (current == null) {
            throw new IllegalStateException("Ring is empty");
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

