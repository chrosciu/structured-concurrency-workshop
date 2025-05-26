package eu.chrost.workshop;

class C00NoConcurrency {
    static String book(Resource<String> resourceOne, Resource<String> resourceTwo) {
        return String.join(",", resourceOne.book(), resourceTwo.book());
    }
}
