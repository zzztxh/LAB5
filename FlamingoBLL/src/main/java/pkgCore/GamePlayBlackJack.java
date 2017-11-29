package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();
	
	
	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {
	
		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);	
		
		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.putHandToGame(GPH, h);
		}
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());
			
			h.AddCard(c);
			
			this.putHandToGame(GPH, h);

		}
		return c;
	}
	
	public void Draw(GamePlayerHand GPH, Card c) throws DeckException, HandException {

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			h.AddCard(c);
			this.putHandToGame(GPH, h);
		}
	}
	public void setDealerHand(Card c) throws DeckException, HandException {

		Hand h = hDealer;
		h.AddCard(c);

}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		Hand h = this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack)h.ScoreHand();
		
		for (Integer i : HSB.getNumericScores()) {
			if (i <= 21) {
				bCanPlayerDraw = true;
				break;
			}
		}

		return bCanPlayerDraw;
	}
	
	
	
	public boolean bDoesDealerHaveToDraw() throws HandException
	{
		boolean bDoesDealerHaveToDraw = true;
		
		
		HandScoreBlackJack HSB = (HandScoreBlackJack)hDealer.ScoreHand();
		
		for (Integer i : HSB.getNumericScores()) {
			if ((i >= 17)) {
				bDoesDealerHaveToDraw = false;
				break;
			}
		}
		
		
		return bDoesDealerHaveToDraw;
	}
	
	
	
	
	
	
	
	public void ScoreGame(GamePlayerHand GPH) throws HandException
	{
		HandScoreBlackJack dScore = (HandScoreBlackJack) hDealer.ScoreHand();

		Iterator it = this.getHmGameHands().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			GamePlayerHand kGPH = (GamePlayerHand) pair.getKey();

			if (kGPH.getGameID() == GPH.getGameID()) {
				HandBlackJack hPlayer = (HandBlackJack) pair.getValue();
				HandScoreBlackJack pHSP = (HandScoreBlackJack) hPlayer.ScoreHand();
				hPlayer.seteBJR(CheckScore(dScore, pHSP));
				this.putHandToGame(kGPH, hPlayer);

			}
		}

	}

	private eBlackJackResult CheckScore(HandScoreBlackJack dHSB, HandScoreBlackJack pHSB) {


		if (isBusted(pHSB)) {
			return eBlackJackResult.LOSE;
		}

		if (isBusted(dHSB)) {
			return eBlackJackResult.WIN;
		}
		
		
		Integer iHiDealerScore = GamePlayBlackJack.ValidScores(dHSB.getNumericScores()).getLast().intValue();
		Integer iHiPlayerScore = GamePlayBlackJack.ValidScores(dHSB.getNumericScores()).getLast().intValue();
		
		
		if (iHiDealerScore < iHiPlayerScore)
		{
			return eBlackJackResult.WIN;
		}
		else if (iHiDealerScore > iHiPlayerScore)
		{
			return eBlackJackResult.LOSE;
		}
		else
		{
			return eBlackJackResult.TIE;
		}

	}

	private boolean isBusted(HandScoreBlackJack HSB) {
		boolean isBusted = true;

		for (Integer i : HSB.getNumericScores()) {

			if (i <= 21) {
				isBusted = false;
				break;
			}
		}

		return isBusted;

	}

	public static LinkedList<Integer> ValidScores(LinkedList<Integer> scores) {
		LinkedList<Integer> tempScores = (LinkedList<Integer>) scores.clone();
		tempScores.removeIf(p -> p.intValue() > 21);
		return tempScores;

}

	public Player getpDealer() {
		return pDealer;
	}
}