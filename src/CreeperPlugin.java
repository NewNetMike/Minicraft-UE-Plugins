
import javax.imageio.ImageIO;


import net.xeoh.plugins.base.annotations.PluginImplementation;
import studio.bytesize.ld22.Game;
import studio.bytesize.ld22.MinicraftPlugin;
import studio.bytesize.ld22.gfx.SpriteSheet;
import studio.bytesize.ld22.level.Level;
import studio.bytesize.ld22.sound.Sound;

@PluginImplementation
public class CreeperPlugin implements MinicraftPlugin
{
	public static SpriteSheet creeperSheet;

	public void onLoad(Game game)
	{
		try
		{
			creeperSheet = new SpriteSheet(ImageIO.read(CreeperPlugin.class.getResource("/creeper.png")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Sound.load("fuse", this.getClass().getResource("/fuse.wav"));
		Level.addMobToLevelSpawner(Creeper.class);
	}

	public String getName()
	{
		return "Creeper Plugin";
	}

	@Override
	public boolean autoEnabled()
	{
		// TODO Auto-generated method stub
		return true;
	}
}
