package datatypes;

import java.util.*;

public class ConnectionData {

    private final ArrayList<Triplet<LineName, StopName, Time>> connection;

    public ConnectionData() {
        connection = new ArrayList<>();
    }

    public void addToConnection(LineName lineName, StopName stopName, Time time) {
        Optional<LineName> newLineName = Optional.ofNullable(lineName);
        if (newLineName.isPresent()) {
            connection.add(new Triplet<>(lineName, stopName, time));
        }
        else connection.add(new Triplet<>(null, stopName, time));
    }

    public List<Triplet<LineName, StopName, Time>> getConnection() {
        List<Triplet<LineName, StopName, Time>> returnList = new ArrayList<>(connection);
        //has to be reversed, because the first triplet is at the bottom
        Collections.reverse(returnList);
        return Collections.unmodifiableList(returnList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = connection.size();
        while (n --> 0) {
            //connection.get(n) is triplet and append on stringBuilder uses toString() method implemented in Triplet
            stringBuilder.append(connection.get(n));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}