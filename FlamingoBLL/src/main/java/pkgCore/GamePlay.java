package pkgCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgEnum.eGameType;
import pkgException.DeckException;
import pkgException.HandException;

public abstract class GamePlay {

	private UUID GameID;
	private eGameType eGameType;
	private HashMap<UUID, Player> hmGamePlayers = new HashMap<UUID, Player>();
	private HashMap<GamePlayerHand, Hand> hmGameHands = new HashMap<GamePlayerHand, Hand>();
	private Deck dGameDeck;
	public GamePlay(eGameType eGameType, HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {

		
		this.GameID = UUID.randomUUID();

		
		this.eGameType = eGameType;
		
		
		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();

			
		}

		switch (eGameType) {
		case BLACKJACK:
			
			break;
		case POKER:
			
			break;
		}
	}

	public UUID getGameID() {
		return GameID;
	}

	public eGameType geteGameType() {
		return eGameType;
	}

	public HashMap<UUID, Player> getHmGamePlayers() {
		return hmGamePlayers;
	}

	protected HashMap<GamePlayerHand, Hand> getHmGameHands() {
		return hmGameHands;
	}

	protected Hand gethmGameHand(GamePlayerHand GPH) {
		return this.hmGameHands.get(GPH.getGPH());
	}

	public void setHmGameHands(HashMap<GamePlayerHand, Hand> hmGameHands) {
		this.hmGameHands = hmGameHands;
	}

	protected void putHandToGame(GamePlayerHand GPH, Hand h) {
		this.hmGameHands.put(GPH, h);
	}



	public Deck getdGameDeck() {
		return dGameDeck;
	}

	protected abstract Card Draw(GamePlayerHand GPH) throws DeckException, HandException;

	private void AddPlayerToGame(Player p) {
		hmGamePlayers.put(p.getPlayerID(), p);
	}

	protected void RemovePlayerFromGame(Player p) {
		hmGamePlayers.remove(p.getPlayerID());
	}

	protected Player GetPlayerInGame(Player p) {
		return (Player)hmGamePlayers.get(p.getPlayerID());

	}
}
