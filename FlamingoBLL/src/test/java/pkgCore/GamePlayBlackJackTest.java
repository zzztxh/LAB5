package pkgCore;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import pkgEnum.eBlackJackResult;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWin() throws DeckException, HandException {

		Table t = new Table();
		Player player1 = new Player("Greg", 1);
		Player player2 = new Player("Mike", 2);
		
		t.AddPlayerToTable(player1);
		t.AddPlayerToTable(player2);
		
		Deck d = new Deck();
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);
		Iterator it = gpBJ.getHmGameHands().entrySet().iterator();
		
		GamePlayerHand Test = null;
		HandBlackJack PH = null;
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack HBJ = (HandBlackJack) pair.getValue();
			GamePlayerHand GPH = (GamePlayerHand) pair.getKey();
			
			if (GPH.getPlayerID() == player1.getPlayerID()) {
				gpBJ.putHandToGame(GPH, HBJ);
				gpBJ.Draw(GPH, new Card(eSuit.CLUBS, eRank.KING));
				gpBJ.Draw(GPH, new Card(eSuit.DIAMONDS, eRank.KING));
				gpBJ.putHandToGame(GPH, HBJ);
				Test = GPH;
				
				PH = HBJ;
			}

			if (GPH.getPlayerID() == player2.getPlayerID()) {
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.SIX));
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.SIX));
				gpBJ.putHandToGame(GPH, HBJ);
			}

		}
		gpBJ.setDealerHand(new Card(eSuit.SPADES, eRank.TWO));
		gpBJ.setDealerHand(new Card(eSuit.SPADES, eRank.TWO));
		
		gpBJ.ScoreGame(Test);
		assertEquals(PH.geteBJR(), eBlackJackResult.WIN);
	}

	@Test
	public void TestPlayerLosing() throws DeckException, HandException {
		
		Table t = new Table();
		Player p1 = new Player("Greg", 1);
		Player p2 = new Player("Mike", 2);
		Deck d = new Deck();
		
		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);
		
		
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);
		Iterator it = gpBJ.getHmGameHands().entrySet().iterator();
		
		GamePlayerHand Test = null;
		HandBlackJack PH = null;
		
		while (it.hasNext()) {
			
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack HBJ = (HandBlackJack) pair.getValue();
			GamePlayerHand GPH = (GamePlayerHand) pair.getKey();
			
			if (GPH.getPlayerID() == p1.getPlayerID()) {
				gpBJ.putHandToGame(GPH, HBJ);
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.THREE));
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.THREE));
				gpBJ.putHandToGame(GPH, HBJ);
				Test = GPH;
				PH = HBJ;
			}

			if (GPH.getPlayerID() == p2.getPlayerID()) {
				
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.FIVE));
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.FIVE));
				gpBJ.putHandToGame(GPH, HBJ);
			}

		}
		gpBJ.setDealerHand(new Card(eSuit.SPADES, eRank.EIGHT));
		gpBJ.setDealerHand(new Card(eSuit.SPADES, eRank.EIGHT));
		gpBJ.ScoreGame(Test);
		assertEquals(PH.geteBJR(), eBlackJackResult.LOSE);
		
	}

	@Test
	public void TestPlayerPush() throws DeckException, HandException {
		
		Table t = new Table();
		Player p1 = new Player("Greg", 1);
		Player p2 = new Player("Mike", 2);
		Deck d = new Deck();
		
		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);
		
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);
		Iterator it = gpBJ.getHmGameHands().entrySet().iterator();
		
		GamePlayerHand Test = null;
		HandBlackJack PH = null;
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack HBJ = (HandBlackJack) pair.getValue();
			GamePlayerHand GPH = (GamePlayerHand) pair.getKey();
			
			if (GPH.getPlayerID() == p1.getPlayerID()) {
				gpBJ.putHandToGame(GPH, HBJ);
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.SEVEN));
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.SEVEN));
				gpBJ.putHandToGame(GPH, HBJ);
				Test = GPH;
				PH = HBJ;
			}

			if (GPH.getPlayerID() == p2.getPlayerID()) {
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.FIVE));
				gpBJ.Draw(GPH, new Card(eSuit.HEARTS, eRank.NINE));
				gpBJ.putHandToGame(GPH, HBJ);
			}

		}
			gpBJ.setDealerHand(new Card(eSuit.HEARTS, eRank.SEVEN));
			gpBJ.setDealerHand(new Card(eSuit.HEARTS, eRank.SEVEN));
			gpBJ.ScoreGame(Test);
			assertEquals(PH.geteBJR(), eBlackJackResult.TIE);
	}

	@Test
	public void TestTwoPlayersWinning() throws DeckException, HandException {
		
		Table t = new Table();
		Deck d = new Deck();
		
		Player player1 = new Player("Greg", 1);
		Player player2 = new Player("Mike", 2);
		
		t.AddPlayerToTable(player1);
		t.AddPlayerToTable(player2);
		
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(), d);
		Iterator it = gpBJ.getHmGameHands().entrySet().iterator();
		
		GamePlayerHand Test = null;
		GamePlayerHand Test2 = null;
		HandBlackJack PH = null;
		HandBlackJack JH = null;
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			HandBlackJack HBJ = (HandBlackJack) pair.getValue();
			GamePlayerHand GPH = (GamePlayerHand) pair.getKey();
			
			if (GPH.getPlayerID() == player1.getPlayerID()) {
				gpBJ.putHandToGame(GPH, HBJ);
				gpBJ.Draw(GPH, new Card(eSuit.CLUBS, eRank.EIGHT));
				gpBJ.Draw(GPH, new Card(eSuit.CLUBS, eRank.EIGHT));
				gpBJ.putHandToGame(GPH, HBJ);
				Test = GPH;
				PH = HBJ;
			}

			if (GPH.getPlayerID() == player2.getPlayerID()) {
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.TEN));
				gpBJ.Draw(GPH, new Card(eSuit.SPADES, eRank.TEN));
				gpBJ.putHandToGame(GPH, HBJ);
				Test2 = GPH;
				JH = HBJ;
			}

		}
			gpBJ.setDealerHand(new Card(eSuit.HEARTS, eRank.NINE));
			gpBJ.setDealerHand(new Card(eSuit.HEARTS, eRank.NINE));
			gpBJ.ScoreGame(Test);
			assertEquals(PH.geteBJR(), eBlackJackResult.WIN);
			gpBJ.ScoreGame(Test2);
			assertEquals(JH.geteBJR(), eBlackJackResult.LOSE);
	}
}
