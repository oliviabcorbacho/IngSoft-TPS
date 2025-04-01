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

    private void emptyRingException() { //auxiliar
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
    }

    private void previous( Node desired){ //auxiliar
        Node aux = current;
        while (aux.next != current) {
            aux = aux.next;
        }
        aux.next = desired;
        current = desired;
    }

    public Ring next() {
        emptyRingException();
        current = current.next;
        return this;
    }

    public Object current() {
        emptyRingException();
        return current.cargo;
    }

    public Ring add(Object cargo) {
        Node newNode = new Node(cargo);

        if (current == null) {
            current = newNode;
        } else {
            newNode.next = current;
            previous(newNode);
        }
        return this;
    }

    public Ring remove() {
        emptyRingException();

        if (current.next == current) { // Only one element
            current = null;
        } else {
            previous(current.next);
        }
        return this;
    }
}

