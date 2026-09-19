// Problem 1: Basic Drawing Canvas

public class a1 {

    abstract static class Shape {
        private static int counter = 1000;      // shared across every subclass
        private final String shapeId;
        protected double scaleX = 1.0;          // growth factors kept in the parent,
        protected double scaleY = 1.0;          // so both scale() overloads work for every subclass

        protected Shape() {
            shapeId = "SH-" + (++counter);      // assigned once, in one place
        }

        public abstract double calculateArea();

        // Overload 1: grows the shape equally (delegates to the two-argument version)
        void scale(double factor) {
            scale(factor, factor);
        }

        // Overload 2: grows the shape unevenly
        void scale(double xFactor, double yFactor) {
            scaleX *= xFactor;
            scaleY *= yFactor;
        }

        String getShapeId() {
            return shapeId;
        }
    }

    static class CircleShape extends Shape {
        private final double radius;

        public CircleShape(double radius) {
            super();
            this.radius = radius;
        }

        @Override
        public double calculateArea() {
            return Math.PI * radius * radius * scaleX * scaleY;
        }
    }

    static class SquareShape extends Shape {
        private final double side;

        public SquareShape(double side) {
            super();
            this.side = side;
        }

        @Override
        public double calculateArea() {
            return side * side * scaleX * scaleY;
        }
    }

    // Works with any Shape; never checks the actual subclass
    static void printArea(Shape s) {
        System.out.println(s.getShapeId() + " area: " + s.calculateArea());
    }

    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        System.out.printf("%.2f%n", c.calculateArea());   // ~78.54

        SquareShape sq = new SquareShape(4.0);
        System.out.println(sq.calculateArea());           // 16.0

        sq.scale(2.0);                                    // one-argument overload
        System.out.println(sq.calculateArea());           // 64.0

        printArea(c);
        printArea(sq);

        // Shape s = new Shape(); // COMPILE ERROR: Shape is abstract, cannot be instantiated
    }
}