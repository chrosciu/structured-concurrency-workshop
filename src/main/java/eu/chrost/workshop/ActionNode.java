package eu.chrost.workshop;

import java.util.List;

record ActionNode<T>(String name, List<Action<T>> actions, List<ActionNode<T>> childNodes) {
    public ActionNode(String name) {
        this(name, List.of(), List.of());
    }
    public ActionNode(String name, List<Action<T>> actions) {
        this(name, actions, List.of());
    }
}


