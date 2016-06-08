package procedrualmap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MapTile extends Application
{
	int xindex;
	int yindex;
	public int width = 256;
	public int height = 256;
	Image tileImage;
	Canvas canvas;
	

	public MapTile()
	{
//		tileImage = new Image("", width, height, false, false);
		
		
//		gfx.setFill(Color.GREEN);
//		gfx.fillRect(10, -10, 400, 200);
		
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
//		GraphicsContext gfx;
//		canvas = new Canvas(width, height);
//		gfx = canvas.getGraphicsContext2D();
//		for(int i = 0; i< width;i++)
//		{
//			for(int j = 0; j< height;j++)
//			{
//				gfx.setFill(Color.rgb(i, j, 100));
//				gfx.fillRect(i,j,2,2);
//			}			
//		}
		StackPane pane = new StackPane(canvas);
		Scene scene = new Scene(pane, width, height);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
//		Image tileImage = new Image(width, height);
		launch(args);
	}
}
