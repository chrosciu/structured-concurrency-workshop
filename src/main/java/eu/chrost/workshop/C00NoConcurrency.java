package eu.chrost.workshop;

class C00NoConcurrency {
    static String book(Resource resourceOne, Resource resourceTwo) {
        return String.join(",", resourceOne.book(), resourceTwo.book());
    }
}
