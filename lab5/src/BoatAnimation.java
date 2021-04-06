import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class BoatAnimation implements ActionListener, KeyListener {
  private TransformGroup boat;
  private Transform3D transform3D = new Transform3D();
  private float x = 0;
  private float y = 0;
  private boolean w = false;
  private boolean s = false;
  private boolean a = false;
  private boolean d = false;
  private boolean e = false;
  private boolean q = false;
  BoatAnimation(TransformGroup boat) {
    this.boat = boat;
    this.boat.getTransform(this.transform3D);
    Timer timer = new Timer(20, this);
    timer.start();
  }
  private void Move() {
    if (w) {
      y += 0.02f;
      if (y > 0.4f) y = 0.4f;
    }
    if (s) {
      y -= 0.02f;
      if (y < -0.47f) y = -0.47f;
    }
    if (a) {
      x -= 0.02f;
      if (x < -0.8f) x = -0.8f;
    }
    if (d) {
      x += 0.02f;
      if (x > 0.79f) x = 0.79f;
    }
    transform3D.setTranslation(new Vector3f(x, y, 0));
    if (e) {
      Transform3D rotation = new Transform3D();
      rotation.rotY(0.05f);
      transform3D.mul(rotation);
    }
    if (q) {
      Transform3D rotation = new Transform3D();
      rotation.rotY(-0.05f);
      transform3D.mul(rotation);
    }
    boat.setTransform(transform3D);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Move();
  }

  @Override
  public void keyPressed(KeyEvent ev) {
    switch (ev.getKeyChar()) {
      case 'w':
        w = true;
        break;
      case 's':
        s = true;
        break;
      case 'a':
        a = true;
        break;
      case 'd':
        d = true;
        break;
      case 'e':
        e = true;
        break;
      case 'q':
        q = true;
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyReleased(KeyEvent ev) {
    switch (ev.getKeyChar()) {
      case 'w':
        w = false;
        break;
      case 's':
        s = false;
        break;
      case 'a':
        a = false;
        break;
      case 'd':
        d = false;
        break;
      case 'e':
        e = false;
        break;
      case 'q':
        q = false;
        break;
    }
  }
}