package procedrualmap;

import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MapTile extends Application
{
	int xindex;
	int yindex;
	public int width = 256;
	public int height = 256;
	Canvas canvas;
	Random r;
  private long seed = 23;
  int scale;
  
  double xphase = Math.PI/12;
  double yphase = 0;
  double xfrequency = 5;
  double yfrequency = 0.05;
  
  WritableImage background;
  int cellSize = 20;
	
  GraphicsContext gfx;
  

//	public MapTile(int w, int h)
//	{
//	  canvas = new Canvas(width, height);
//    gfx = canvas.getGraphicsContext2D();
//    r = new Random(seed);
//    scale = 1;
////    draw();
//	}
	
	public WritableImage getImage()
	{
	  WritableImage image = canvas.snapshot(null, null);
	  return image;
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
	  
	  canvas = new Canvas(width, height);
    gfx = canvas.getGraphicsContext2D();
    r = new Random(seed);
    scale = 1;
    
//		MapTile tile = new MapTile(width, height);
		
		StackPane sPane = new StackPane(canvas);
		AnchorPane aPane = new AnchorPane();
		VBox sliderBox = new VBox();
		
		
		Slider sliderScale = new Slider();
		sliderScale.valueProperty().addListener(new ChangeListener<Number>()
	    {
	      public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
	      {
	        scale = new_val.intValue()+1;
	        System.out.println(scale);
	      }
	    });
		sliderScale.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
		  @Override
      public void handle(MouseEvent event)
		  {
		    draw();
      }
  });
		sliderBox.getChildren().add(sliderScale);
		
		Slider sliderX = new Slider();
		sliderX.valueProperty().addListener(new ChangeListener<Number>()
		{
		  public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
		  {
		    xfrequency = new_val.doubleValue();
		    System.out.println("xfrequency "+xfrequency);
		  }
		});
		sliderX.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event)
		  {
		    draw();
		  }
		});
		sliderX.setMax(2*Math.PI);
		sliderBox.getChildren().add(sliderX);
		
		
		Slider sliderY = new Slider();
		sliderY.valueProperty().addListener(new ChangeListener<Number>()
		{
		  public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
		  {
		    yfrequency = new_val.doubleValue();
		    System.out.println("yfrequency "+yfrequency);
		  }
		});
		sliderY.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event)
		  {
		    draw();
		  }
		});
		sliderY.setMax(2*Math.PI);
		sliderBox.getChildren().add(sliderY);
		
		
		Slider sliderShiftX = new Slider();
		sliderShiftX.valueProperty().addListener(new ChangeListener<Number>()
		{
		  public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
		  {
		    xphase = new_val.doubleValue();
		    System.out.println("xphase "+xphase);
		  }
		});
		
		sliderShiftX.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event)
		  {
		    draw();
		  }
		});
		sliderShiftX.setMax(2*Math.PI);
		sliderBox.getChildren().add(sliderShiftX);
		
		Slider sliderShiftY = new Slider();
		sliderShiftY.valueProperty().addListener(new ChangeListener<Number>()
		{
		  public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) 
		  {
		    yphase = new_val.doubleValue();
		    System.out.println("yphase "+yphase);
		  }
		});
		sliderShiftY.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
		  @Override
		  public void handle(MouseEvent event)
		  {
		    draw();
		  }
		});
		sliderShiftY.setMax(2*Math.PI);
		
		sliderBox.getChildren().add(sliderShiftY);
		
		aPane.getChildren().add(sliderBox);
		AnchorPane.setBottomAnchor(sliderBox, 0.0);
		
		sPane.getChildren().add(aPane);
		
		draw();
		WritableImage test = getImage();
		System.out.println(test.getHeight());
		Scene scene = new Scene(sPane, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public double noiseFunction(int x, int y, double d)
	{
//	  double dubx = x;
//	  double duby = y;
//	  double sum = 0;
//	  
//	  int [][] points = new int[][]{
//	    { x,         y },
//	    { width - x, y },
//	    { x,         height - y },
//	    { width - x, height - y },
////	    { x - width / 2, y - height / 2 }
//	    { 0, 0 }
//	  };
//	  
//	  
////	  System.out.println("points.length "+points.length);
////	  System.out.println("points[0].length "+points[0].length);
////	  for(int i = 0; i < points.length-1; i++)
////	  {
////	    for(int j = 0; j < points[0].length; j++)
////	    {
////	      System.out.printf("[%3d]", points[i][j]);
////	    }
////	    System.out.print("\n");
////	  }
//	  for(int i = 0; i < points.length-1; i++)
//	  {
//	      sum += (points[i][0] * points[4][0]) + (points[i][1] * points[4][1]);
//	  }
//	  int temp = r.nextInt() % 2;
//	  int temp = (int) noiseFreq(12.0, x, y);
//	  return Math.abs(temp*255);
//	  double tempX = (int) (Math.sin(x*Math.PI/12)*127+128);
//	  double tempY = (int) (Math.sin(y*Math.PI/12)*127+128);
//	  return (tempY+tempX)/2;
//	  Interesting Pattern
//	  System.out.println(sum/width);
//	  return (sum/4)/width;
	  return Math.sin(((x*y)/64*Math.PI*2/180+d))*127+128;
	}
	
	private void draw()
	{
	  for(int i = 1; i < width; i += scale)
    {
      for(int j = 1; j < height; j += scale)
      {
        int num = (int) noiseFunction(i,j, 0);
//        gfx.setFill(Color.rgb(num,num,num));
        gfx.setFill(Color.rgb((int) noiseFunction(i,j, 0), (int) noiseFunction(i,j, Math.PI*2/3), (int) noiseFunction(i,j, Math.PI*4/3)));
        gfx.fillRect(i,j,scale,scale);
//        pix.setColor(i, j, Color.rgb(num, num, num)); 
      }     
    }
	}
	
	public void lerp()
	{
	  
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
