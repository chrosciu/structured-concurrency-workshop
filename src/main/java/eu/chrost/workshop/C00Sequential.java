package eu.chrost.workshop;

class C00Sequential {
    static <T, U> String runInSequence(Action<T> actionOne, Action<U> actionTwo) {
        return String.join(",", actionOne.run().toString(), actionTwo.run().toString());
    }
}
