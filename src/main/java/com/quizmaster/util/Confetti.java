package com.quizmaster.util;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Confetti {

    private final Pane pane;
    private final List<Rectangle> confetti = new ArrayList<>();
    private final Random random = new Random();

    public Confetti(Pane pane) {
        this.pane = pane;
    }

    public void start() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 100_000_000) {
                    createConfetti();
                    lastUpdate = now;
                }
                moveConfetti();
            }
        };
        timer.start();
    }

    private void createConfetti() {
        for (int i = 0; i < 10; i++) {
            Rectangle rect = new Rectangle(10, 10);
            rect.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            rect.setX(random.nextDouble() * pane.getWidth());
            rect.setY(-10);
            confetti.add(rect);
            pane.getChildren().add(rect);
        }
    }

    private void moveConfetti() {
        for (Rectangle rect : confetti) {
            rect.setY(rect.getY() + 5);
        }
    }
}