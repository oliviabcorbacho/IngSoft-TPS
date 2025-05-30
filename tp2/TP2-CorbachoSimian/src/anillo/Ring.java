package anillo;

import java.util.Stack;

public class Ring {

    private Node current = new EmptyNode();
    private static Stack<NodeFunction> pila = new Stack<>();


    private abstract static class Node {
        abstract Node add(Object cargo);
        abstract Node remove();
        abstract Node next();
        abstract Object current();
    }

    private abstract static class NodeFunction {
        abstract Node apply(Node node);
    }
    private static class FinalFunction extends NodeFunction {
        Node apply(Node node) {
            return new EmptyNode();
        }
    }
    private static class RegularFunction extends NodeFunction {
        Node apply(Node node) {
            Node aux = node;
            while (((MultiNode) node).next != aux) {
                node = ((MultiNode) node).next;
            }
            ((MultiNode) node).next = aux.next();
            return node.next();
        }
    }

    private static class EmptyNode extends Node {
        Node add(Object cargo) {
            MultiNode curr =  new MultiNode(cargo);
            curr.next = curr;
            NodeFunction f = new FinalFunction();
            pila.push(f);

            return curr;
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

            NodeFunction f = new RegularFunction();

            pila.push(f);

            return newNode;
        }

        Node remove() {
            Node actualNode = this;
            NodeFunction func = pila.pop();
            return func.apply(this);

        }
        Node next() {
            return next;
        }
        Object current() {
            return cargo;
        }
    }

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
