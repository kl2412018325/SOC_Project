class IncidentInfo {
    String id, type, date;
    int severity, time;
    double cost;

    public IncidentInfo(String id, String type, int severity, String date, int time, double cost) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.date = date;
        this.time = time;
        this.cost = cost;
    }

    public String toString() {
        return id + " | " + type + " | Severity: " + severity + " | RM" + cost;
    }
}