public record Vec2(double x, double y) {
    public Vec2(Vec2 old) {
        this(old.x, old.y);
    }
}