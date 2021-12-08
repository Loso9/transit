package tests;

import exceptions.NegativeSegmentIndexException;
import main.StopInterface;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.*;
import datatypes.*;
import database.*;
import exceptions.NegativeCapacityException;

public class StopsFactoriesTest {

    private MemoryStopsFactory msf;
    private FileStopsFactory fsf;
    private List<StopName> stopNames;

    public void setUp() throws NegativeCapacityException, NegativeSegmentIndexException {
        MemoryDatabase mdb = new MemoryDatabase();
        msf = new MemoryStopsFactory(mdb);
        fsf = new FileStopsFactory();
        stopNames = new ArrayList<>();
        stopNames.add(new StopName("Trnava"));
        stopNames.add(new StopName("Bratislava"));
        stopNames.add(new StopName("Kosice"));
        stopNames.add(new StopName("Martin"));
        stopNames.add(new StopName("Presov"));
        stopNames.add(new StopName("Brezno"));
        stopNames.add(new StopName("Partizanske"));
    }

    @Test
    public void createStopTest() throws NegativeCapacityException, FileNotFoundException, NegativeSegmentIndexException {
        setUp();
        //test cities which arent in the databases, hence cant be created
        //memoryStopsFactory
        assertEquals(Optional.empty(), msf.createStop(new StopName("Piestany")));
        assertEquals(Optional.empty(), msf.createStop(new StopName("Zilina")));
        assertEquals(Optional.empty(), msf.createStop(new StopName("Brno")));
        assertEquals(Optional.empty(), msf.createStop(new StopName("Praha")));
        assertEquals(Optional.empty(), msf.createStop(new StopName("Nitra")));

        //fileStopsFactory
        assertEquals(Optional.empty(), fsf.createStop(new StopName("Piestany")));
        assertEquals(Optional.empty(), fsf.createStop(new StopName("Zilina")));
        assertEquals(Optional.empty(), fsf.createStop(new StopName("Brno")));
        assertEquals(Optional.empty(), fsf.createStop(new StopName("Praha")));
        assertEquals(Optional.empty(), fsf.createStop(new StopName("Nitra")));

        //test cities which are in the database
        //memoryStopsFactory
        Optional<StopInterface> trnavaStopMemory = msf.createStop(new StopName("Trnava"));
        assertTrue(trnavaStopMemory.isPresent());
        assertEquals(new StopName("Trnava"), msf.createStop(new StopName("Trnava")).get().getStopName());

        Optional<StopInterface> bratislavaStopMemory = msf.createStop(new StopName("Bratislava"));
        assertTrue(bratislavaStopMemory.isPresent());
        assertEquals(new StopName("Bratislava"), msf.createStop(new StopName("Bratislava")).get().getStopName());

        Optional<StopInterface> kosiceStopMemory = msf.createStop(new StopName("Kosice"));
        assertTrue(kosiceStopMemory.isPresent());
        assertEquals(new StopName("Kosice"), msf.createStop(new StopName("Kosice")).get().getStopName());

        Optional<StopInterface> martinStopMemory = msf.createStop(new StopName("Martin"));
        assertTrue(martinStopMemory.isPresent());
        assertEquals(new StopName("Martin"), msf.createStop(new StopName("Martin")).get().getStopName());

        Optional<StopInterface> presovStopMemory = msf.createStop(new StopName("Presov"));
        assertTrue(presovStopMemory.isPresent());
        assertEquals(new StopName("Presov"), msf.createStop(new StopName("Presov")).get().getStopName());

        Optional<StopInterface> breznoStopMemory = msf.createStop(new StopName("Brezno"));
        assertTrue(breznoStopMemory.isPresent());
        assertEquals(new StopName("Brezno"), msf.createStop(new StopName("Brezno")).get().getStopName());

        Optional<StopInterface> partizanskeStopMemory = msf.createStop(new StopName("Partizanske"));
        assertTrue(partizanskeStopMemory.isPresent());
        assertEquals(new StopName("Partizanske"), msf.createStop(new StopName("Partizanske")).get().getStopName());

        setUp();
        //fileStopsFactory
        Optional<StopInterface> trnavaStopFile = fsf.createStop(new StopName("Trnava"));
        assertTrue(trnavaStopFile.isPresent());
        assertEquals(new StopName("Trnava"), fsf.createStop(new StopName("Trnava")).get().getStopName());

        Optional<StopInterface> bratislavaStopFile = fsf.createStop(new StopName("Bratislava"));
        assertTrue(bratislavaStopFile.isPresent());
        assertEquals(new StopName("Bratislava"), fsf.createStop(new StopName("Bratislava")).get().getStopName());

        Optional<StopInterface> kosiceStopFile = fsf.createStop(new StopName("Kosice"));
        assertTrue(kosiceStopFile.isPresent());
        assertEquals(new StopName("Kosice"), fsf.createStop(new StopName("Kosice")).get().getStopName());

        Optional<StopInterface> martinStopFile = fsf.createStop(new StopName("Martin"));
        assertTrue(martinStopFile.isPresent());
        assertEquals(new StopName("Martin"), fsf.createStop(new StopName("Martin")).get().getStopName());

        Optional<StopInterface> presovStopFile = fsf.createStop(new StopName("Presov"));
        assertTrue(presovStopFile.isPresent());
        assertEquals(new StopName("Presov"), fsf.createStop(new StopName("Presov")).get().getStopName());

        Optional<StopInterface> breznoStopFile = fsf.createStop(new StopName("Brezno"));
        assertTrue(breznoStopFile.isPresent());
        assertEquals(new StopName("Brezno"), fsf.createStop(new StopName("Brezno")).get().getStopName());

        Optional<StopInterface> partizanskeStopFile = fsf.createStop(new StopName("Partizanske"));
        assertTrue(partizanskeStopFile.isPresent());
        assertEquals(new StopName("Partizanske"), fsf.createStop(new StopName("Partizanske")).get().getStopName());
    }

    @Test
    public void getStopsTest() throws Exception {
        setUp();
        //expected number of stops in database is 7
        assertEquals(7, msf.getStops().size());
        //will try to iterate through getStops list and check whether it contains something else or doesnt contain expected stopName
        //if getStops returns expected stops, exception shouldnt be thrown
        List<StopInterface> stopsMemory = msf.getStops();
        for (StopInterface stop : stopsMemory) {
            boolean stopNameFound = false;
            for (StopName stopName : stopNames) {
                if (stop.getStopName().equals(stopName)) {
                    stopNameFound = true;
                }
            }
            if (!stopNameFound) {
                throw new Exception();
            }
        }

        List<StopInterface> stopsFile = fsf.getStops();
        for (StopInterface stop : stopsFile) {
            boolean stopNameFound = false;
            for (StopName stopName : stopNames) {
                if (stop.getStopName().equals(stopName)) {
                    stopNameFound = true;
                }
            }
            if (!stopNameFound) {
                throw new Exception();
            }
        }
    }

}
