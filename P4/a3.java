class BusRoute {
    String routeCode;
    String routeName;
    int priority;

    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }

    public int compareTo(BusRoute other) {
        if (this.priority != other.priority) {
            return Integer.compare(other.priority, this.priority);
        }
        int codeCmp = this.routeCode.compareToIgnoreCase(other.routeCode);
        if (codeCmp != 0) {
            return codeCmp;
        }
        return this.routeName.compareToIgnoreCase(other.routeName);
    }
}

public class a3 {
    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        for (int i = 0; i < routes.length - 1; i++) {
            for (int j = 0; j < routes.length - i - 1; j++) {
                if (routes[j].compareTo(routes[j + 1]) > 0) {
                    BusRoute temp = routes[j];
                    routes[j] = routes[j + 1];
                    routes[j + 1] = temp;
                }
            }
        }
        return routes;
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = rankRoutes(routes);

        System.out.print("[");
        for (int i = 0; i < ranked.length; i++) {
            System.out.print("\"" + ranked[i].routeCode + "\"" + (i < ranked.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}