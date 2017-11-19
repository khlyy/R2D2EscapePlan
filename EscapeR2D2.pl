
wall(cell(I, J)) :-
    I < 0; I > 2; J < 0; J > 2.

teleportal(cell(2, 2)).
obstacle(cell(10, 10)).
%obstacle(cell(0, 1)).
%obstacle(cell(0, 2)).

rock_location(cell(1, 0), s0).
rock_location(cell(0, 1), s0).

pressure_pad(cell(2, 0)).
pressure_pad(cell(0, 2)).

at_location(cell(0, 0), s0).
% teleportal(X), call_with_depth_limit(at_location(X, result(move(down), result(move(down), result(move(right), result(move(right), s0))))), 9, R).
oposite(up, down).
oposite(down, up).
oposite(left, right).
oposite(right, left).

adjacent(cell(I1, J1), cell(I2, J1), up) :- I2 is I1 + 1.
adjacent(cell(I1, J1), cell(I2, J1), down) :- I2 is I1 - 1.
adjacent(cell(I1, J1), cell(I1, J2), left) :- J2 is J1 + 1.
adjacent(cell(I1, J1), cell(I1, J2), right) :- J2 is J1 - 1.
not_goal(State) :-
  pressure_pad(Pad), \+ rock_location(Pad, State).
% teleportal(X), call_with_depth_limit(at_location(X, S), 10, R), pressure_pad(Z), call_with_depth_limit(rock_location(Z, S), 10, R).
% teleportal(X), call_with_depth_limit(at_location(X, Y), 10, R).
valid_cell(Cell, State) :- % TODO:: add teleportal if the rocks are not on the pads,
  \+ wall(Cell), \+ obstacle(Cell), (\+ teleportal(Cell); \+ not_goal(State)).


  at_location(Curr_cell, result(Action, State)) :-
    at_location(Curr_cell, State), \+ (adjacent(Curr_cell, Free_cell, Direction),
    oposite(Direction, Oposite_direction), Action = move(Oposite_direction),
    valid_cell(Free_cell, State), \+ rock_location(Free_cell, State)),
    \+ (adjacent(Curr_cell, Rock_cell, Direction), oposite(Direction, Oposite_direction),
    rock_location(Rock_cell, State), Action = push(Oposite_direction), adjacent(Rock_cell, Free_cell, Direction),
    valid_cell(Free_cell, State), \+ rock_location(Free_cell, State)).


at_location(Curr_cell, result(Action, State)) :-
  adjacent(Curr_cell, Agent_cell, Direction), Action = move(Direction), valid_cell(Curr_cell, State),
  valid_cell(Agent_cell, State), at_location(Agent_cell, State),
  \+ rock_location(Curr_cell, State).

at_location(Curr_cell, result(Action, State)) :-
  adjacent(Curr_cell, Agent_cell, Direction), at_location(Agent_cell, State),
  rock_location(Curr_cell, State), oposite(Direction, Oposite_direction), Action = push(Direction),
  valid_cell(Curr_cell, State),
  adjacent(Curr_cell, Free_cell, Oposite_direction), valid_cell(Free_cell, State),
  \+ rock_location(Free_cell, State).


% at_location(Curr_cell, result(Action, State)) :-
%   adjacent(Curr_cell, Agent_cell, Direction), Action = move(Direction), valid_cell(Curr_cell), valid_cell(Agent_cell), at_location(Agent_cell, State).

rock_location(Curr_cell, result(Action, State)) :-
  adjacent(Curr_cell, Rock_cell, Direction), adjacent(Rock_cell, Agent_cell, Direction), Action = push(Direction),
  rock_location(Rock_cell, State), \+ rock_location(Curr_cell, State), at_location(Agent_cell, State),
  valid_cell(Curr_cell, State).

rock_location(Curr_cell, result(Action, State)) :-
  rock_location(Curr_cell, State),
  \+ (adjacent(Curr_cell, Agent_cell, Direction), at_location(Agent_cell, State), Action = push(Direction),
  oposite(Direction, Oposite_direction),
  adjacent(Curr_cell, Free_cell, Oposite_direction), \+ rock_location(Free_cell, State), valid_cell(Free_cell, State)).
