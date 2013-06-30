package net.javainthebox.dukeanimation;

import com.sun.scenario.animation.shared.ClipInterpolator;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.javainthebox.caraibe.svg.SVGContent;
import net.javainthebox.caraibe.svg.SVGLoader;

public class Duke007 extends Application {
    private final static String SVG_FILE = "duke007.svg";

    private SVGContent svgContent;
    private Group walkingDuke;

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        svgContent = SVGLoader.load(getClass().getResource(SVG_FILE).toString());

        Node background = svgContent.getNode("background");
        root.getChildren().add(background);

        starAnimation(root);

        Scene scene = new Scene(root, 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    private void starAnimation(Group root) {
        Circle circle1 = new Circle(-100.0, 384.0, 50.0);
        circle1.setFill(Color.WHITE);
        root.getChildren().add(circle1);

        Circle circle2 = new Circle(-100.0, 384.0, 50.0);
        circle2.setFill(Color.WHITE);
        root.getChildren().add(circle2);

        Node barrelHole = svgContent.getNode("barrelhole");
        Node duke = svgContent.getNode("dukestand");
        duke.setOpacity(0.0);
        Node riffle = svgContent.getNode("riffle");
        Node blood = svgContent.getNode("blood");

        Animation walkingAnimation = prepareWalking(root);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(  500),
                        new KeyValue(circle1.translateXProperty(), 200.0),
                        new KeyValue(circle2.translateXProperty(), 200.0, Interpolator.DISCRETE)),
                new KeyFrame(Duration.millis(1_000),
                        new KeyValue(circle1.translateXProperty(), 400.0),
                        new KeyValue(circle2.translateXProperty(), 400.0, Interpolator.DISCRETE)),
                new KeyFrame(Duration.millis(1_500),
                        new KeyValue(circle1.translateXProperty(), 600.0),
                        new KeyValue(circle2.translateXProperty(), 600.0, Interpolator.DISCRETE)),
                new KeyFrame(Duration.millis(2_000),
                        new KeyValue(circle1.translateXProperty(), 800.0),
                        new KeyValue(circle2.translateXProperty(), 800.0, Interpolator.DISCRETE)),
                new KeyFrame(Duration.millis(2_500),
                        new KeyValue(circle1.translateXProperty(), 1000.0),
                        new KeyValue(circle2.translateXProperty(), 1000.0, Interpolator.DISCRETE)),
                new KeyFrame(Duration.millis(3_000),
                        e -> {
                            root.getChildren().add(barrelHole);
                            root.getChildren().add(walkingDuke);
                            root.getChildren().add(duke);
                            root.getChildren().add(riffle);
                            root.getChildren().add(blood);

                            walkingAnimation.play();
                        },
                        new KeyValue(circle1.translateXProperty(), 1200.0),
                        new KeyValue(circle2.translateXProperty(), 1200.0, Interpolator.DISCRETE),
                        new KeyValue(barrelHole.translateXProperty(), 1400.0),
                        new KeyValue(walkingDuke.translateXProperty(), 1400.0),
                        new KeyValue(riffle.translateXProperty(), 1400.0)),
                new KeyFrame(Duration.millis(7_000),
                        e -> {
                            walkingAnimation.stop();
                        },
                        new KeyValue(walkingDuke.opacityProperty(), 0.0, Interpolator.DISCRETE),
                        new KeyValue(duke.opacityProperty(), 1.0, Interpolator.DISCRETE),
                        new KeyValue(barrelHole.translateXProperty(), 0.0),
                        new KeyValue(walkingDuke.translateXProperty(), 0.0),
                        new KeyValue(riffle.translateXProperty(), 0.0)),
                new KeyFrame(Duration.millis(7_500),
                        e -> {
                            AudioClip clip = new AudioClip(getClass().getResource("OMT004_02S005.wav").toString());
                            clip.play();
                        }),
                new KeyFrame(Duration.millis(8_000),
                        new KeyValue(barrelHole.translateXProperty(), 0.0),
                        new KeyValue(barrelHole.translateYProperty(), 0.0),
                        new KeyValue(riffle.translateXProperty(), 0.0),
                        new KeyValue(riffle.translateYProperty(), 0.0),
                        new KeyValue(blood.translateYProperty(), 0.0)),
                new KeyFrame(Duration.millis(9_000),
                        new KeyValue(barrelHole.translateXProperty(), -200.0),
                        new KeyValue(barrelHole.translateYProperty(), 100.0),
                        new KeyValue(riffle.translateXProperty(), -200.0),
                        new KeyValue(riffle.translateYProperty(), 100.0)),
                new KeyFrame(Duration.millis(10_000),
                        new KeyValue(barrelHole.translateXProperty(), -100.0),
                        new KeyValue(barrelHole.translateYProperty(), 200.0),
                        new KeyValue(riffle.translateXProperty(), -100.0),
                        new KeyValue(riffle.translateYProperty(), 200.0)),
                new KeyFrame(Duration.millis(11_000),
                        new KeyValue(barrelHole.translateXProperty(), 100.0),
                        new KeyValue(barrelHole.translateYProperty(), 400.0),
                        new KeyValue(riffle.translateXProperty(), 100.0),
                        new KeyValue(riffle.translateYProperty(), 400.0)),
                new KeyFrame(Duration.millis(12_000),
                        new KeyValue(barrelHole.translateXProperty(), 0.0),
                        new KeyValue(barrelHole.translateYProperty(), 900.0),
                        new KeyValue(riffle.translateXProperty(), 0.0),
                        new KeyValue(riffle.translateYProperty(), 900.0)),
                new KeyFrame(Duration.millis(15_000),
                        new KeyValue(blood.translateYProperty(), 1700.0))
        );

        timeline.play();
    }

    private Animation prepareWalking(Group root) {
        walkingDuke = new Group();

        // 足踏みしているイメージを読み込み、
        // 透明にしておく
        for (int index = 0; index < 5; index++) {
            Node duke = svgContent.getNode(String.format("walk%02d", index));
            duke.setOpacity(0.0);
            walkingDuke.getChildren().add(duke);
        }

        Timeline walkingAnimation = new Timeline();

        // 一定時間ごとに、透明度を変化させて、表示させるイメージを切り替える
        KeyFrame keyFrame0 = new KeyFrame(Duration.millis(0),
                new KeyValue(walkingDuke.getChildren().get(0).opacityProperty(), 1.0, Interpolator.DISCRETE),
                new KeyValue(walkingDuke.getChildren().get(4).opacityProperty(), 0.0, Interpolator.DISCRETE));
        walkingAnimation.getKeyFrames().add(keyFrame0);

        for (int i = 1; i < 5; i++) {
            KeyFrame keyFrame = new KeyFrame(Duration.millis(200*i),
                    new KeyValue(walkingDuke.getChildren().get(i).opacityProperty(), 1.0, Interpolator.DISCRETE),
                    new KeyValue(walkingDuke.getChildren().get(i-1).opacityProperty(), 0.0, Interpolator.DISCRETE));
            walkingAnimation.getKeyFrames().add(keyFrame);
        }

        // 無限に繰り返し
        walkingAnimation.setCycleCount(Timeline.INDEFINITE);

        return walkingAnimation;
    }

    public static void main(String... args) {
        launch(args);
    }
}
