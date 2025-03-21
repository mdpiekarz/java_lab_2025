import java.util.Locale;

public class TransformationDecorator extends ShapeDecorator {
    //String translate, rotate, scale;
    private String transform;  //"translate rotate scale"

    public TransformationDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public String toSvg(String parameters) {
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "transform=\"%s\" %s", transform, parameters));
    }

    public static class Builder {
        private boolean translate = false, rotate = false, scale = false;
        private Vec2 translateVector, rotateCenter, scaleVector;
        private double rotateAngle;
        private Shape shape;

        public Builder translate(Vec2 translateVector) {
            translate = true;
            this.translateVector = translateVector;
            return this;
        }

        public Builder rotate(Vec2 rotateCenter, double rotateAngle) {
            rotate = true;
            this.rotateCenter = rotateCenter;
            this.rotateAngle = rotateAngle;
            return this;
        }

        public Builder scale(Vec2 scaleVector) {
            scale = true;
            this.scaleVector = scaleVector;
            return this;
        }

        public TransformationDecorator build(Shape shape) {
            TransformationDecorator obj = new TransformationDecorator(shape);
            obj.transform = "";
            if (translate) {
                obj.transform = String.format(Locale.ENGLISH, "translate(%f %f) ",
                        translateVector.x(), translateVector.y());
            }
            if (rotate) {
                obj.transform = String.format(Locale.ENGLISH, "%s rotate(%f %f %f) ",
                        obj.transform, rotateAngle, rotateCenter.x(), rotateCenter.y());
            }
            if (scale) {
                obj.transform = String.format(Locale.ENGLISH, "%s scale(%f %f) ",
                        obj.transform, scaleVector.x(), scaleVector.y());
            }
            return obj;
        }
    }
}
