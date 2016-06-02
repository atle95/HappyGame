package procedrualmap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class mapTile extends Application
{
	int xindex;
	int yindex;
	public int width = 800;
	public int height = 800;
	Image tileImage;
	Canvas canvas;

	public mapTile()
	{
//		tileImage = new Image("", width, height, false, false);
		canvas = new Canvas(width, height);
		GraphicsContext gfx = canvas.getGraphicsContext2D();
		gfx.setFill(Color.GREEN);
		gfx.fillRect(10, -10, 400, 200);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
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
