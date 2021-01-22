import random
import math


BOT_NAME = "Ultron 2.0"


class RandomAgent:
    """Agent that picks a random available move.  You should be able to beat it."""
    def get_move(self, state, depth=None):
        return random.choice(state.successors())


class HumanAgent:
    """Prompts user to supply a valid move."""
    def get_move(self, state, depth=None):
        move__state = dict(state.successors())
        prompt = "Kindly enter your move {}: ".format(sorted(move__state.keys()))
        move = None
        while move not in move__state:
            try:
                move = int(input(prompt))
            except ValueError:
                continue
        return move, move__state[move]


class MinimaxAgent:
    """Artificially intelligent agent that uses minimax to optimally select the best move."""

    def get_move(self, state, depth=None):
        """Select the best available move, based on minimax value."""
        nextp = state.next_player()
        best_util = -math.inf if nextp == 1 else math.inf
        best_move = None
        best_state = None

        for move, state in state.successors():
            util = self.minimax(state, depth)
            if ((nextp == 1) and (util > best_util)) or ((nextp == -1) and (util < best_util)):
                best_util, best_move, best_state = util, move, state
        return best_move, best_state

    def minimax(self, state, depth):
        """Determine the minimax utility value of the given state.

        Args:
            state: a connect383.GameState object representing the current board
            depth: for this agent, the depth argument should be ignored!

        Returns: the exact minimax utility value of the state
        """
        if state.is_full():
            return state.score()
        else:
            successors = state.successors()
            state_values = []
            for successor in successors:
                state_values.append(self.minimax(successor[1], depth))
            if state.next_player() == -1:
                return min(state_values)
            else:
                return max(state_values)


class HeuristicAgent(MinimaxAgent):
    """Artificially intelligent agent that uses depth-limited minimax to select the best move."""

    def minimax(self, state, depth):
        return self.minimax_depth(state, depth)

    def minimax_depth(self, state, depth):
        """Determine the heuristically estimated minimax utility value of the given state.

        Args:
            state: a connect383.GameState object representing the current board
            depth: the maximum depth of the game tree that minimax should traverse before
                estimating the utility using the evaluation() function.  If depth is 0, no
                traversal is performed, and minimax returns the results of a call to evaluation().
                If depth is None, the entire game tree is traversed.

        Returns: the minimax utility value of the state
        """
        if state.is_full():
            return state.score()
        elif depth != None and depth == 0:
            return self.evaluation(state)
        else:
            successors = state.successors()
            state_values = []
            for successor in successors:
                if depth == None:
                    state_values.append(self.minimax_depth(successor[1], None))
                else:
                    state_values.append(self.minimax_depth(successor[1], depth - 1))
            if state.next_player() == -1:
                return min(state_values)
            else:
                return max(state_values)

    def evaluation(self, state):
        return state.score() + self.calculate_potential(-1, state) + self.calculate_potential(1, state)

    def calculate_potential(self, player, state):
        rows = state.get_all_rows()
        cols = state.get_all_cols()
        diags = state.get_all_diags()
        #streaks doesn't work for this purpose because I want to see if there is a 0 after the streak, otherwise there's no potential
        potential = 0
        len_row_streak = 0
        for row in rows:
            for space in row:
                if space != player and len_row_streak == 0:
                    continue
                if space == player:
                    len_row_streak += 1
                    continue
                if space == 0 and len_row_streak > 0:
                    if len_row_streak >= 2:
                        potential += player * len_row_streak * len_row_streak
                    len_col_streak = 0
        len_col_streak = 0
        for col in cols:
            for space in col:
                if space != player and len_col_streak == 0:
                    continue
                if space == player:
                    len_col_streak += 1
                    continue
                if space == 0 and len_col_streak > 0:
                    if len_col_streak >= 2:
                        potential += player * len_col_streak * len_col_streak
                    len_col_streak = 0
        len_diag_streak = 0
        for diag in diags:
            for space in diag:
                if space != player and len_diag_streak == 0:
                    continue
                if space == player:
                    len_diag_streak += 1
                    continue
                if space == 0 and len_diag_streak > 0:
                    if len_diag_streak >= 2:
                        potential += player * len_diag_streak * len_diag_streak
                    len_col_streak = 0
        return potential
                

        


class PruneAgent(HeuristicAgent):
    """Smarter computer agent that uses minimax with alpha-beta pruning to select the best move."""

    def minimax(self, state, depth):
        return self.minimax_prune(state, depth)
        
    def minimax_prune(self, state, depth):
        """Determine the minimax utility value the given state using alpha-beta pruning.

        The value should be equal to the one determined by ComputerAgent.minimax(), but the 
        algorithm should do less work.  You can check this by inspecting the class variables
        GameState.p1_state_count and GameState.p2_state_count, which keep track of how many
        GameState objects were created over time.

        N.B.: When exploring the game tree and expanding nodes, you must consider the child nodes
        in the order that they are returned by GameState.successors().  That is, you cannot prune
        the state reached by moving to column 4 before you've explored the state reached by a move
        to to column 1.

        Args: see ComputerDepthLimitAgent.minimax() above

        Returns: the minimax utility value of the state
        """
        if state.next_player() == 1:
            return self.minimax_prune_helper_max(state, depth, -math.inf, math.inf)
        else:
            return self.minimax_prune_helper_min(state, depth, -math.inf, math.inf)

    def minimax_prune_helper_max(self, state, depth, alpha, beta):
        if state.is_full():
            return state.score()
        elif depth != None and depth == 0:
            evaluator = HeuristicAgent()
            return evaluator.evaluation(state)
        else:
            successors = state.successors()
            best_next_move = -math.inf
            for successor in successors:
                if depth == None:
                    score = self.minimax_prune_helper_min(successor[1], None, alpha, beta)
                else:
                    score = self.minimax_prune_helper_min(successor[1], depth - 1, alpha, beta)
                best_next_move = max(score, best_next_move)
                if best_next_move >= beta:
                    return best_next_move
                alpha = max(best_next_move, alpha)
            return best_next_move
            

    def minimax_prune_helper_min(self, state, depth, alpha, beta):
        if state.is_full():
            return state.score()
        elif depth != None and depth == 0:
            evaluator = HeuristicAgent()
            return evaluator.evaluation(state)
        else:
            successors = state.successors()
            best_next_move = math.inf
            for successor in successors:
                if depth == None:
                    score = self.minimax_prune_helper_max(successor[1], None, alpha, beta)
                else:
                    score = self.minimax_prune_helper_max(successor[1], depth - 1, alpha, beta)
                best_next_move = min(score, best_next_move)
                if best_next_move >= beta:
                    return best_next_move
                beta = min(best_next_move, beta)
            return best_next_move
    

