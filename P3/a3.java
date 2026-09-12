class F3HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public F3HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
            System.out.println(name + " allotted to room " + roomNo);
        }
    }
}

public class a3 {
    public static F3HostelRoom findAvailableRoom(F3HostelRoom[] rooms) {
        if (rooms == null) return null;
        for (F3HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(F3HostelRoom[] rooms, String studentName) {
        F3HostelRoom available = findAvailableRoom(rooms);
        if (available != null) {
            available.allot(studentName);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        F3HostelRoom[] availableRooms = {
            new F3HostelRoom("C-214", 3, 2),
            new F3HostelRoom("C-507", 2, 2)
        };
        safeAllot(availableRooms, "Divya");

        F3HostelRoom[] fullRooms = {
            new F3HostelRoom("C-214", 3, 3),
            new F3HostelRoom("C-507", 2, 2)
        };
        safeAllot(fullRooms, "Divya");
    }
}