
import java.util.LinkedList;

class AnalystInfo {
    String id, name, expertise;
    LinkedList<IncidentInfo> incidents = new LinkedList<>();

    public AnalystInfo(String id, String name, String expertise) {
        this.id = id;
        this.name = name;
        this.expertise = expertise;
    }

    public void addIncident(IncidentInfo i) {
        incidents.add(i);
    }

    public double totalCost() {
        double total = 0;
        for (IncidentInfo i : incidents) total += i.cost;
        return total;
    }

    // ✅ EXACT MATCH WITH REPORT FORMAT
    public String fullDisplay() {
        String s = name + " (" + expertise + ")\n";
        for (IncidentInfo i : incidents) {
            s += " - " + i + "\n";
        }
        s += " Total Cost: RM" + totalCost() + "\n\n";
        return s;
    }
}