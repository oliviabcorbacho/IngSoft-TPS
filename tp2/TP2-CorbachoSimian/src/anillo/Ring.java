package anillo;

public class Ring {

    private abstract static class Node {
        abstract Node add(Object cargo);
        abstract Node remove();
        abstract Node next();
        abstract Object current();
    }

    private static class EmptyNode extends Node { //NULL OBJECT PATTERN
        Node add(Object cargo) {
            return new SingleNode(cargo);
        }
        Node remove() {
            throw new RuntimeException("Ring is empty");
        }
        Node next() {
            throw new RuntimeException("Ring is empty");
        }
        Object current() {
            throw new RuntimeException("Ring is empty");
        }
    }

    //JAJA EMILIO DIJO QUE ESTO NO ES VALIDO, tengo que hacer que singleNode y multiNode sean la misma clase
    //Resuelvo estableciendo el elemento NEUTRO
    private static class SingleNode extends Node {
        private  Object cargo;

        SingleNode(Object cargo) {
            this.cargo = cargo;
        }

        Node add(Object cargo) {
            MultiNode first = new MultiNode(cargo);
            MultiNode second = new MultiNode(this.cargo);
            first.next = second;
            second.next = first;
            return first;
        }
        Node remove() {
            return new EmptyNode();
        }
        Node next() {
            return this;
        }
        Object current() {
            return cargo;
        }
    }

    private static class MultiNode extends Node {
        private Object cargo;
        private Node next;

        MultiNode(Object cargo) {
            this.cargo = cargo;
            this.next = null;
        }

        Node add(Object newCargo) {
            MultiNode newNode = new MultiNode(newCargo);
            newNode.next = this;
            Node prev = this;
            while (((MultiNode) prev).next != this) {
                prev = ((MultiNode) prev).next;
            }
            ((MultiNode) prev).next = newNode;
            return newNode;
        }

        Node remove() {
            Node prev = this;
            while (((MultiNode) prev).next != this) {
                prev = ((MultiNode) prev).next;
            }
            ((MultiNode) prev).next = next;

            if (next == prev) {
                return new SingleNode(((MultiNode) next).cargo);
            } else {
                return next;
            }
        }
        Node next() {
            return next;
        }
        Object current() {
            return cargo;
        }
    }

    private Node current = new EmptyNode();

    public Ring next() {
        current = current.next();
        return this;
    }

    public Object current() {
        return current.current();
    }

    public Ring add(Object cargo) {
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }
}
