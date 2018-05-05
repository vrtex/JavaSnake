public class GameReset
{
	public static void main(String args[])
	{
		for(String s : args)
		{
			if(s.equals("-l"))
				resetLevel();
			else if(s.equals("-s"))
				resetScores();
		}
	}
	
	private static void resetLevel()
	{
		System.out.println("reseting level");
		GameField newField = new GameField(
				Game.pieceSize.width / 2,
				Game.pieceSize.height,
				Game.width - Game.pieceSize.width,
				Game.height - Game.pieceSize.height);
		newField.save(0);
	}
	
	private static void resetScores()
	{
		System.out.println("reseting scores");
		ScoreTable newTable = new ScoreTable();
		newTable.save();
	}
}
