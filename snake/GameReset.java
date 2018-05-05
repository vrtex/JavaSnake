public class GameReset
{
	public static void main(String args[])
	{
		
		GameField newField = new GameField(
				Game.pieceSize.width / 2,
				Game.pieceSize.height,
				Game.width - Game.pieceSize.width,
				Game.height - Game.pieceSize.height);
		newField.save(0);
	}
}
