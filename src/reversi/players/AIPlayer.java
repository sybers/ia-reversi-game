package reversi.players;

import reversi.MovePosition;
import reversi.ReversiGame;

import reversi.players.ia.AbstractHeuristic;
import reversi.players.ia.heuristics.MaximiseScoreHeuristic;
import reversi.players.ia.MoveCandidate;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

public class AIPlayer extends AbstractPlayer {

    private AbstractHeuristic mHeuristic;

    public AIPlayer(AbstractHeuristic heuristic) {
        super();

        if(heuristic == null)
            throw new IllegalArgumentException("Heuristic cannot be null value");

        mHeuristic = heuristic;
    }

    @Override
    public MovePosition playTurn(ReversiGame game, List<MovePosition> possibleMoves) {
        List<MoveCandidate> candidates = new ArrayList<>();

        System.out.println(game.toString());

        System.out.println("Il y a " + possibleMoves.size() + " coups possibles pour l'IA");

        for (MovePosition move : possibleMoves) {
            ReversiGame virtualgame = new ReversiGame(game);
            virtualgame.performMove(move.getRow(), move.getColumn(), mColor);

            double score = mHeuristic.evaluate(virtualgame, mColor);
            System.out.println("Coup : " + move.toString() + ", score : " + score);

            candidates.add(new MoveCandidate(move, score));
        }

        candidates.sort(Collections.reverseOrder());

        // on filtre les meilleurs mouvements
        List<MoveCandidate> bestMoves = candidates.stream().filter(p -> p.getScore() == candidates.get(0).getScore()).collect(Collectors.toList());

        // on prend un mouvement au hasard parmi les meilleurs mouvements
        MovePosition choseMove = bestMoves.get((new Random()).nextInt(bestMoves.size())).getPosition();

        System.out.println("Joueur " + mColor + " joue en " + choseMove.toString());

        return choseMove;
    }

    /**
     * Recopie du joueur
     * @return copie
     */
    @Override
    public AbstractPlayer copy() {
        AIPlayer copy = new AIPlayer(mHeuristic);
        copy.setScore(mScore);
        copy.setColor(mColor);
        return copy;
    }
}
