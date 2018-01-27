import studio.bytesize.ld22.entity.Furniture;
import studio.bytesize.ld22.gfx.Color;

import java.util.Random;

public class JackOLantern extends Furniture
{
	int count = 0;
	int brightness = 5;
	private static final Random random = new Random();

	public JackOLantern()
	{
		super("Jack");
		col = Color.get(-1, 210, 530, 550);
		sprite = 5;
		xr = 3;
		yr = 2;
		sheet = UltimatePlugin.icons2;
	}

	public int getLightRadius()
	{
		// Flickering light??
		count++;
		if (count >= 180)
		{
			int jackpot = random.nextInt(2);
			if (jackpot == 0) brightness = 6;
			else brightness = 5;
			count = 0;
		}

		return 5;
	}

}
