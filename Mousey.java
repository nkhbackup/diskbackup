/* CS 0401 Fall 2017
   Demonstration of mouse abilities in Java, plus a simple use of a
   JTextArea and a RectangularShape.
*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

// Note that in this program, rather than having a JFrame object within the main class,
// I am making the main class a subclass of JFrame.  This is also how the text sets up
// most of its graphical examples.  This means that Mousey ISA JFrame so it has all of
// the abilities of a JFrame.
public class Mousey extends JFrame
{
     private JTextArea info;
     private JScrollPane scroller;
     private RectangularShape shape;
	 private boolean selected;
	 private double offsetX, offsetY;

     public Mousey()
     {
            super("Java Mouse Example");
            setLayout(new FlowLayout());
            info = new JTextArea("Beginning Mouse Demo\n",15,30);
                                 // integer arguments are rows and columns
            // Add the JTextArea to a JScrollPane so that we can see new
            // rows past the size of the JTextArea
            scroller = new JScrollPane(info);
            add(scroller);

			// Add a MouseListener and a MouseMotionListener
            addMouseListener(new MyMouseListener());
            addMouseMotionListener(new MyMotionListener());

			// Make an Ellipse2D.Double within the JFrame
			shape = new Ellipse2D.Double(580, 400, 30, 50);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(640, 480);
            setVisible(true);
     }

     public static void main(String [] args)
     {
            Mousey win = new Mousey();
     }

	 // Add new String to JTextArea, then move to the bottom of the text
	 // so you can see the new String without scrolling
     public void appendToText(String t)
     {
     		info.append(t);
     		info.setCaretPosition(info.getDocument().getLength());
     }	
     		
     // Note the various methods in the MouseListener.  Experiment with this
     // to see when each fires.
     private class MyMouseListener implements MouseListener
     {
            public void mousePressed(MouseEvent e)
            {
            	double X = e.getX();
            	double Y = e.getY();
                if (e.getButton() == 1)
                    appendToText("Left Mouse Button Pressed at (" + 
                      				X + "," + Y + ") \n");
                else if (e.getButton() == 3)
                	appendToText("Right Mouse Button Pressed at (" + 
                      				X + "," + Y + ") \n");
                // If (X,Y) is within the shape, calculate the offset of this point
				// from the upper left corner of the shape.  This will be used to
				// move the shape (see mouseDragged)
				if (shape.contains(X, Y))
				{
					 selected = true;  // Mark shape as selected so that we can move it
					 offsetX = X - shape.getX();
					 offsetY = Y - shape.getY();
                 	 appendToText("(" + X + "," + Y + ") is inside the shape \n");
				}
			}
            public void mouseReleased(MouseEvent e)
            {
                if (e.getButton() == 1)
                      appendToText("Left Mouse Button Released at (" + 
                      				e.getX() + "," + e.getY() + ") \n");
                else if (e.getButton() == 3)
                 	  appendToText("Right Mouse Button Released at (" + 
                      				e.getX() + "," + e.getY() + ") \n");
				
				// Unselect the shape since mouse has been released
				if (selected)
					selected = false;
            }
            public void mouseClicked(MouseEvent e)
            {
                 appendToText("Mouse Clicked for the " + e.getClickCount() +
                              " time\n");
            }
            public void mouseEntered(MouseEvent e)
            {
                 appendToText("Mouse Entering Window\n");
                 setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // other cursors
            }                                               // are also available
            public void mouseExited(MouseEvent e)
            {
                 info.append("Mouse Exiting Window\n");
            }
     }

	 // MouseMotionListener also receives MouseEvents.  This listener handles the
	 // mouse moving, either with the button unpressed (mouseMoved) or pressed
	 // (mouseDragged)
     private class MyMotionListener implements MouseMotionListener
     {
            public void mouseMoved(MouseEvent e)
            {
            	 double X = e.getX();
            	 	double Y = e.getY();
                 appendToText("Moving Through (" + X + "," + Y + ")\n"); 
                 if (shape.contains(X, Y))
                 	  appendToText("Moving through the shape \n");
            }

            public void mouseDragged(MouseEvent e)
            {
				double X = e.getX();
				double Y = e.getY();
				
				// If shape is selected, calculate the new upper left corner based
				// on the current (X,Y) and the offset.  Then move the shape to that
				// location.  Finally call repaint() to request that the window be 
				// redrawn
				if (selected)
				{
					double newX = X - offsetX;
					double newY = Y - offsetY;
					shape.setFrame(newX, newY, shape.getWidth(), shape.getHeight());
					repaint();
				}
				
                appendToText("Dragging Through (" +
                               X + "," + Y + ")\n");

                 // Experiment with this method to see how it works when a
                 // component is exited and/or entered
            }
     }
     
     // Paint the JFrame contents (in this case the single Ellipse2D.Double).  Note
     // that you do not have to paint predefined components such as JButtons and
     // JLabels -- these are drawn implicitly.  However, graphics that you are 
     // creating yourself must be drawn within the paint() method [Note: If you are
     // drawing in a JPanel rather than a JFrame, the method is paintComponent()].
     public void paint(Graphics g)
	 {
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			// The Graphics2D class has two very useful methods: fill() and draw().
			// These will fill or draw any object that implements the Shape
			// interface.  Ellipse2D.Double implements this interface so it can be
			// rendered by the call to fill below.
			g2d.fill(shape);
	 }
}