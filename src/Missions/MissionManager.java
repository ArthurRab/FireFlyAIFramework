package Missions;

import Unit.UnitWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

public class MissionManager {

    public MissionManager() {

    }

    public void clearAllMissions(Iterable<UnitWrapper> units) {
        for (UnitWrapper u : units) {
            u.deleteMission();
        }
        pendingMissions.clear();
        saveForLater.clear();
    }

    public void update() {
        pendingMissions = saveForLater;
        saveForLater = new PriorityQueue<Mission>(1, new MissionPriorityComparator());
    }

    public PriorityQueue<Mission> pendingMissions = new PriorityQueue<>(1, new MissionPriorityComparator());
    public PriorityQueue<Mission> saveForLater = new PriorityQueue<>(1, new MissionPriorityComparator());

    public void addMission(Mission m) {
        pendingMissions.add(m);
    }

    public void distributeMissions(Collection<UnitWrapper> unitCollection) {
        ArrayList<UnitWrapper> units = new ArrayList<UnitWrapper>(unitCollection);
        Collections.sort(units, (o1, o2) -> Float.compare(o1.getMission().getPriority(), o2.getMission().getPriority()));
        int maxIndex = units.size();


        Mission m;
        while (!pendingMissions.isEmpty()) {
            m = pendingMissions.poll();
            ArrayList<UnitWrapper> applicants = new ArrayList<>();
            for (int i = 0; i < maxIndex; i++) {
                if (m.isAllowedToAcceptThisMission((units.get(i))) && units.get(i).willTakeMission(m)) {
                    applicants.add(units.get(i));
                }
                if (units.get(i).getMission().getPriority() > m.getPriority()) {
                    maxIndex = i;
                    break;
                }
            }

            if (applicants.isEmpty()) {
                saveForLater.add(m);

                saveForLater.addAll(pendingMissions);
                pendingMissions.clear();
            } else {

                UnitWrapper chosenOne = m.chooseUnit(applicants);

                chosenOne.setMission(m);
                m.onStart();
            }
        }
    }

}
