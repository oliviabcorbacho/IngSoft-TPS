package anillo;

import java.util.Stack;

public class Ring {

    private Node current = new EmptyNode();
    private Stack<Node> pila = new Stack<>();

    private Node lastPop (Node n){
        return new EmptyNode();
    }

    private Node regPop (Node actualNode){
        Node aux = actualNode;
        while (((MultiNode) actualNode).next != aux) {
            actualNode = ((MultiNode) actualNode).next;
        }
        ((MultiNode) actualNode).next = aux.next();
        return aux;
    }

    private abstract static class Node {
        abstract Node add(Object cargo);
        abstract Node remove();
        abstract Node next();
        abstract Object current();
    }

    private static class EmptyNode extends Node { //NULL OBJECT PATTERN
        Node add(Object cargo) {
            MultiNode curr =  new MultiNode(cargo);
            curr.next = curr;
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
            return newNode;
        }

        Node remove() {
            Node actualNode = this;
            void callFunc = pila.pop();
            return callFunc(actualNode);
//            if (next == actualNode) {
//                return new SingleNode(((MultiNode) next).cargo);
//            } else {
//                return next;
//            }
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

//IDEA PARA DELETE
// anillo.cant_elementos = []
// nodo.add --> anillo.cant_elementos.append(1)
//nodo.rmv --> while anillo.cant_elementos not empty: tengo caso de muchos anillos. OTHERWISE NULL CASE
