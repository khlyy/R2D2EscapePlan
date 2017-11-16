
wall(cell(I, J)) :-
    I < 0; I > 2; J < 0; J > 2.

teleportal(cell(2, 2)).

obstacle(cell(0, 1)).
obstacle(cell(0, 2)).

rock_location(cell(1, 0), s0).
pressure_pad(cell(2, 0)).

up_cell(cell(I1, J1), cell(I2, J1)) :-  % First cell is over the second cell.
  I2 is I1 + 1.

down_cell(cell(I1, J1), cell(I2, J1)) :-
  I2 is I1 - 1.

right_cell(cell(I1, J1), cell(I1, J2)) :-
  J2 is J1 - 1.

left_cell(cell(I1, J1), cell(I1, J2)) :-
  J2 is J1 + 1.

at_location(cell(0, 0), s0).
at_location(cell(0, 0), s0).

valid_move(cell(I, J)) :- % TODO:: add teleportal if the rocks are not on the pads,
  \+ wall(cell(I, J)), \+ obstacle(cell(I, J)).

move_to_adjacent(Action, Curr_cell, New_cell) :-
  Action = up, up_cell(Curr_cell, New_cell).

move_to_adjacent(Action, Curr_cell, New_cell) :-
  Action = down, down_cell(Curr_cell, New_cell).

move_to_adjacent(Action, Curr_cell, New_cell) :-
  Action = right, right_cell(Curr_cell, New_cell).

move_to_adjacent(Action, Curr_cell, New_cell) :-
  Action = left, left_cell(Curr_cell, New_cell).

at_location(Curr_cell, result(Action, State)) :-
  move_to_adjacent(Action, Curr_cell, New_cell), valid_move(Curr_cell), valid_move(New_cell), at_location(New_cell, State).

rock_location(cell(I, J), result(Action, State)):-
  A = push_rock_up, up_cell(cell(I, J), New_cell), rock_location(New_cell, State), up_cell(New_cell, Agent_cell),
  at_location(Agent_cell, State), valid_move(cell(I,J)), \+ rock_location(cell(I,J), State).

rock_location(cell(I, J), result(Action, State)):-
  A = push_rock_down, down_cell(cell(I, J), New_cell), rock_location(New_cell, State), down_cell(New_cell, Agent_cell),
  at_location(Agent_cell, State), valid_move(cell(I,J)), \+ rock_location(cell(I,J), State).

rock_location(cell(I, J), result(Action, State)):-
  A = push_rock_left, left_cell(cell(I, J), New_cell), rock_location(New_cell, State), left_cell(New_cell, Agent_cell),
  at_location(Agent_cell, State), valid_move(cell(I,J)), \+ rock_location(cell(I,J), State).

rock_location(cell(I, J), result(Action, State)):-
  A = push_rock_right, up_cell(cell(I, J), New_cell), rock_location(New_cell, State), right_cell(New_cell, Agent_cell),
  at_location(Agent_cell, State), valid_move(cell(I,J)), \+ rock_location(cell(I,J), State).
